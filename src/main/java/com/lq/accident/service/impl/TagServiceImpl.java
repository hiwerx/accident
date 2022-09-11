package com.lq.accident.service.impl;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.accident.mapper.TagMapper;
import com.lq.accident.model.AccidentTag;
import com.lq.accident.model.Tag;
import com.lq.accident.service.IAccidentTagService;
import com.lq.accident.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    IAccidentTagService accidentTagService;
    @Override
    public List<Tag> getTagByInfoId(Integer infoId) {
        // 查找关联的tagId
        List<AccidentTag> accidentTagList = accidentTagService.lambdaQuery().eq(AccidentTag::getInfoId, infoId).list();
        if (accidentTagList==null||accidentTagList.size()==0) return null;
        // 查找tag
        List<Integer> tagIdList= accidentTagList.stream().map(AccidentTag::getTagId).collect(Collectors.toList());
        return this.lambdaQuery().in(Tag::getId,tagIdList).list();
    }

    /**
     * 保存tags, 若保存失败返回null
     * @param tagList
     * @param infoId
     * @return
     */
    @Override
    public List<Tag> saveTag(List<String> tagList, Integer infoId) {
        if (tagList!=null&&tagList.size()>0&&infoId!=null){
            List<Tag> tags = tagList.stream()
                    .map(t->{
                        Tag tag = this.lambdaQuery().eq(Tag::getTag, t).one();
                        if (tag==null){
                            tag = Tag.builder().tag(t).build();
                            this.save(tag);
                        }else {
                            tag.setCount(tag.getCount()+1);
                            this.updateById(tag);
                        }
                        // tag与info关联
                        AccidentTag at = accidentTagService.lambdaQuery().eq(AccidentTag::getInfoId, infoId)
                                .eq(AccidentTag::getTagId,tag.getId()).one();
                        if (at==null){
                            accidentTagService.save(AccidentTag.builder().tagId(tag.getId()).infoId(infoId).build());
                        }
                        return tag;
                    }).collect(Collectors.toList());
            return tags;
        }
        return null;
    }

}
