package com.lq.accident.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.accident.model.AccidentTag;
import com.lq.accident.model.Tag;
import com.lq.accident.service.IAccidentTagService;
import com.lq.accident.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/accident/tag")
public class TagController {

    @Autowired
    IAccidentTagService accidentTagService;
    @Autowired
    ITagService tagService;

    @PostMapping("delInfoTag/{infoId}/{tagId}")
    public R delInfoTag(@PathVariable Integer infoId, @PathVariable Integer tagId){

//        QueryWrapper wrapper = new QueryWrapper<AccidentTag>();
//        wrapper.eq("info_id",infoId);
//        wrapper.eq("tag_id", tagId);
        boolean r = accidentTagService
                .remove(new LambdaQueryWrapper<AccidentTag>()
                        .eq(AccidentTag::getInfoId, infoId)
                        .eq(AccidentTag::getTagId, tagId));
        if (r) return R.ok("删除标签成功");
        return R.failed("删除标签失败");
    }

    @PostMapping("save/{infoId}")
    public R<List<Tag>> saveTag(@RequestBody List<String> tagList, @PathVariable Integer infoId){
        return R.ok(tagService.saveTag(tagList, infoId));
    }
}
