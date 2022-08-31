package com.lq.accident.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.accident.mapper.InfoMapper;
import com.lq.accident.model.Info;
import com.lq.accident.model.dto.InfoDTO;
import com.lq.accident.model.dto.SearchDTO;
import com.lq.accident.model.vo.InfoVO;
import com.lq.accident.service.impl.InfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @RequestMapping("mix")
    public R mixSearch(SearchDTO dto){

        // 参数校验
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
            if (startDate.compareTo(endDate)>1) {
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
                return R.failed("搜索文本仅支持汉字数字和英文字母");
            }else{
                dto.setContent("%"+dto.getContent().trim()+"%");
            }
        }
        log.info(JSON.toJSONString(dto,true));
        return R.ok(infoMapper.selectAllInfo(dto));
    }

    // 防止恶意投递
    @RequestMapping("/postInfo")
    public String ui(InfoDTO infoDTO){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("info:"+date+":"+uuid, JSON.toJSONString(infoDTO),24, TimeUnit.HOURS);

        return "sdfsdg";
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
