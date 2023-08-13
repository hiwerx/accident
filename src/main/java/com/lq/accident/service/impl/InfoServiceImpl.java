package com.lq.accident.service.impl;

import com.lq.accident.mapper.InfoSourceMapper;
import com.lq.accident.mapper.TagMapper;
import com.lq.accident.model.AccidentTag;
import com.lq.accident.model.Info;
import com.lq.accident.mapper.InfoMapper;
import com.lq.accident.model.InfoSource;
import com.lq.accident.model.Tag;
import com.lq.accident.model.dto.InfoDTO;
import com.lq.accident.model.dto.SearchDTO;
import com.lq.accident.model.page.MyPage;
import com.lq.accident.model.vo.InfoVO;
import com.lq.accident.service.IInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 事故信息 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {

    @Autowired
    InfoMapper infoMapper;
    @Autowired
    TagServiceImpl tagService;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    AccidentTagServiceImpl accidentTagService;
    @Autowired
    InfoSourceServiceImpl infoSourceService;
    @Autowired
    InfoSourceMapper infoSourceMapper;
    /**
     * 保存事件信息
     * @param infoDTO
     */
    @Transactional
    public void saveInfo(InfoDTO infoDTO){
        Info info = new Info();
        BeanUtils.copyProperties(infoDTO,info);
        this.save(info);

        tagService.saveTag(infoDTO.getTags(), info.getId());
//        if (infoDTO.getTags()!=null&&infoDTO.getTags().size()>0){
//            List<AccidentTag> tagList = infoDTO.getTags().stream()
//                    .map(t->{
//                        Tag tag = tagService.lambdaQuery().eq(Tag::getTag, t).one();
//                        if (tag==null){
//                            tag = Tag.builder().tag(t).build();
//                            tagService.save(tag);
//                        }else {
//                            tag.setCount(tag.getCount()+1);
//                            tagService.updateById(tag);
//                        }
//                        return AccidentTag.builder().tagId(tag.getId()).infoId(info.getId()).build();
//                    }).collect(Collectors.toList());
//            // tag与info关联
//            accidentTagService.saveBatch(tagList);
//        }
        if (infoDTO.getSourceUrls()!=null && infoDTO.getSourceUrls().size()>0){
            for (InfoSource sourceUrl : infoDTO.getSourceUrls()) {
                sourceUrl.setInfoId(info.getId());
                sourceUrl.setCreateTime(LocalDateTime.now());
            }
            infoSourceService.saveBatch(infoDTO.getSourceUrls());
        }
    }

    @Override
    public MyPage<InfoVO> mixPageSearch(SearchDTO dto) {
        MyPage<InfoVO> res = infoMapper.selectAllInfo1(new MyPage(dto.getPageNum(),5),dto);

        if (res.getRecords().size()==0){
            if(res.getPages()==0){
                return res;
            } else {
                // 如果有页数取最大页数
                res = infoMapper.selectAllInfo1(new MyPage(res.getPages(),5),dto);
            }
        }
        res.getRecords().forEach(r->{
            r.setIntroduces(r.getIntroduce().split("\n"));
            r.setTags(tagMapper.selectTagByInfoId(r.getId()));
            r.setSourceUrls(infoSourceMapper.selectInfoSourceVOByInfoId(r.getId()));
            r.setId(null);
        });
        return res;
    }
}
