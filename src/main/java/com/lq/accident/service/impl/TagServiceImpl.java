package com.lq.accident.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.accident.mapper.TagMapper;
import com.lq.accident.model.AccidentTag;
import com.lq.accident.model.Tag;
import com.lq.accident.service.IAccidentTagService;
import com.lq.accident.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
