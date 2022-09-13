package com.lq.accident.controller;


import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lq.accident.mapper.InfoMapper;
import com.lq.accident.model.AccidentTag;
import com.lq.accident.model.Info;
import com.lq.accident.model.InfoSource;
import com.lq.accident.model.Tag;
import com.lq.accident.model.dto.InfoDTO;
import com.lq.accident.model.dto.SearchDTO;
import com.lq.accident.model.page.MyPage;
import com.lq.accident.model.vo.InfoVO;
import com.lq.accident.service.IAccidentTagService;
import com.lq.accident.service.IInfoSourceService;
import com.lq.accident.service.ITagService;
import com.lq.accident.service.impl.InfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p>
 * 事故信息 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Slf4j
@RestController
@RequestMapping("/accident/info")
public class InfoController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    InfoServiceImpl infoService;
    @Autowired
    InfoMapper infoMapper;
    @Autowired
    ITagService tagService;
    @Autowired
    IAccidentTagService accidentTagService;
    @Autowired
    IInfoSourceService sourceService;
    @RequestMapping("all")
    public R<List<InfoVO>> getAll(){
        return R.ok(infoMapper.selectAllInfo(new SearchDTO()));
    }

    @RequestMapping("get/{tagId}")
    public R<List<InfoVO>> getAllByTagId(@PathVariable Integer tagId){
        SearchDTO dto = new SearchDTO();
        dto.setTagId(tagId);
        return R.ok(infoMapper.selectAllInfo(dto));
    }

    @GetMapping("listBase/{infoId}")
    public R getOneInfoById(@PathVariable Integer infoId){
        Info info = infoService.lambdaQuery().eq(Info::getId,infoId).one();
        if (info==null) return R.failed("未查询到相关信息");
        JSONObject jb = JSON.parseObject(JSON.toJSONString(info));
        List<Tag> tagList = tagService.getTagByInfoId(infoId);
        if (tagList!=null){
            String ts = tagList.stream().map(Tag::getTag).collect(Collectors.joining(","));
            jb.put("ts",ts);
            jb.put("tags",tagList);
        }else {
            // 给个空数组避免前端无法获取tags值报错
            jb.put("tags",new ArrayList<Tag>());
        }
        return R.ok(jb);
    }

    /**
     * 多重搜索
     * @param dto
     * @return
     */
    @RequestMapping("mix")
    public R mixSearch(SearchDTO dto){
        // 参数校验
        initSearchDTO(dto);
        if(dto.getContent()!=null)dto.setContent("%"+dto.getContent().trim()+"%");
        return R.ok(infoMapper.selectAllInfo(dto));
    }

    /**
     * 优化搜索请求参数
     * @param dto
     */
    public void initSearchDTO(SearchDTO dto){
        // 参数校验
        if (dto.getPageNum()==null)dto.setPageNum(1);
        /**
         * <option value="1">全部</option>
         * <option value="2">当月</option>
         * <option value="3">上月</option>
         * <option value="4">近三月</option>
         * <option value="5">近半年</option>
         * <option value="6">今年</option>
         * <option value="7">自定义</option>
         */
        String dateFlag = dto.getCheckedDate();
        if (StrUtil.isBlank(dateFlag)||dateFlag.equals("1")){
            dto.setStartDate(null);
            dto.setEndDate(null);
        }else if (dateFlag.equals("7")){
            LocalDate startDate = dto.getStartDate();
            LocalDate endDate = dto.getEndDate();
            if (startDate.compareTo(endDate)>0) {
                dto.setEndDate(startDate);
                dto.setStartDate(endDate);
            }
        }else {
            dto.setEndDate(LocalDate.now());
            LocalDate nowStartMonth = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1);
            if (dateFlag.equals("2"))dto.setStartDate(nowStartMonth);
            else if (dateFlag.equals("3"))dto.setStartDate(nowStartMonth.minusMonths(1));
            else if (dateFlag.equals("4"))dto.setStartDate(nowStartMonth.minusMonths(2));
            else if (dateFlag.equals("5"))dto.setStartDate(nowStartMonth.minusMonths(5));
            else if (dateFlag.equals("6"))dto.setStartDate(LocalDate.of(LocalDate.now().getYear(),1,1));
        }
        if (StrUtil.isBlank(dto.getContent())){
            dto.setContent(null);
        }else {
            if (!dto.getContent().matches("[\\w\\u4e00-\\u9fa5]{0,16}")){
                throw new RuntimeException("搜索文本仅支持汉字数字和英文字母");
            }else{
                dto.setContent(dto.getContent().trim());
            }
        }
    }

    @GetMapping("adminSearch")
    public R adminSearch(SearchDTO searchDTO){
        initSearchDTO(searchDTO);
        MyPage<Info> myPage = new MyPage<>(searchDTO.getPageNum(),3);
        // select * from ddd where date between * and * and (introduce like * or title like*);
        infoService.page(myPage,new LambdaQueryWrapper<Info>().select(Info::getId,Info::getTitle)
                .between(searchDTO.getStartDate()!=null&&searchDTO.getEndDate()!=null,
                        Info::getDate,
                        searchDTO.getStartDate(),searchDTO.getEndDate())
                .and(searchDTO.getContent()!=null,(qw)->
                    qw.like(Info::getIntroduce,searchDTO.getContent())
                            .or()
                            .like(Info::getTitle,searchDTO.getContent())
                            .or()
                            .like(Info::getPlace,searchDTO.getContent())
                )
                .orderByDesc(Info::getDate)
        );
//        List<Info> infoList = infoService.lambdaQuery()
//                .select(Info::getId,Info::getTitle)
//                .between(searchDTO.getStartDate()!=null&&searchDTO.getEndDate()!=null,
//                        Info::getDate,
//                        searchDTO.getStartDate(),searchDTO.getEndDate())
//                .and(searchDTO.getContent()!=null,
//                        (qr)-> qr.like(Info::getIntroduce,searchDTO.getContent())
//                                .or()
//                                .like(Info::getTitle,searchDTO.getContent())
//                                .or()
//                                .like(Info::getPlace,searchDTO.getContent())
//                )
//                .orderByDesc(Info::getDate)
//                .list();
        myPage.setSelf();
        Console.log(myPage);
        return R.ok(myPage);
    }



    /**
     * 混合保存（基本信息，标签，来源）
     * @param infoDTO
     * @return
     */
    @RequestMapping("/postInfo")
    public R ui(@RequestBody InfoDTO infoDTO){
//        Console.log(JSON.toJSONString(infoDTO,true));
        infoService.saveInfo(infoDTO);
//        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
//        String uuid = UUID.randomUUID().toString();
     //   redisTemplate.opsForValue().set("info:"+date+":"+uuid, JSON.toJSONString(infoDTO),24, TimeUnit.HOURS);

        return R.ok("保存成功");
    }

    /**
     * 更新基本信息
     * @param info
     * @return
     */
    @PostMapping("update")
    public R updateInfo(@RequestBody Info info){
        if (info.getId()==null){
            return R.failed("信息不全，更新失败");
        }
        info.setUpdateTime(LocalDateTime.now());
        infoService.updateById(info);
        return R.ok("更新成功");
    }

    @PostMapping("del/{infoId}")
    public R delInfo(@PathVariable Integer infoId){
        log.info(infoId+"");
        // 删除info
        infoService.removeById(infoId);
        // 删除tag关联
        accidentTagService.remove(new LambdaQueryWrapper<AccidentTag>().eq(AccidentTag::getInfoId,infoId));
        // 删除来源
        sourceService.remove(new LambdaQueryWrapper<InfoSource>().eq(InfoSource::getInfoId,infoId));
        return R.ok("删除成功");
    }
    // 获取未读的投递消息
    public void getInfo(){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        Set<String> keys =  redisTemplate.keys("info:"+date+":");
        if (keys!=null) redisTemplate.delete(keys);
    }

    // 删除投递消息
    public void delInfo(String id){
        if (id.matches("\\d+")){
            infoService.lambdaUpdate()
                    .set(Info::getStatus,"2")
                    .eq(Info::getId,id)
                    .update();
        }else {
            String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
            redisTemplate.delete("info:"+date+":"+id);
        }
    }

    // 忽略全部
    public void ignoreAllInfo(){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        Set<String> keys =  redisTemplate.keys("info:"+date+":");
        if (keys!=null) redisTemplate.delete(keys);
        infoService.lambdaUpdate()
                .set(Info::getStatus,"2")
                .eq(Info::getStatus,"0")
                .update();
    }

}
