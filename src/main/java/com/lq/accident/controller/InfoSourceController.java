package com.lq.accident.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lq.accident.model.InfoSource;
import com.lq.accident.service.IInfoSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 信息来源 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@RestController
@RequestMapping("/accident/infoSource")
public class InfoSourceController {

    @Autowired
    IInfoSourceService infoSourceService;
    @GetMapping("list/{infoId}")
    public R<List> list(@PathVariable String infoId){
        List<InfoSource> infoSourceList = infoSourceService.lambdaQuery()
                .eq(InfoSource::getInfoId, infoId)
                .list();
        return R.ok(infoSourceList);
    }

    @PostMapping("add")
    public R<InfoSource> add(@RequestBody InfoSource source){
        source.setCreateTime(LocalDateTime.now());
        boolean res = infoSourceService.save(source);
        if (res) return R.ok(source);
        return R.failed("保存来源失败");
    }

    @PostMapping("update")
    public R<InfoSource> edit(@RequestBody InfoSource source){
        boolean res = infoSourceService.updateById(source);
        if (res) return R.ok(source);
        return R.failed("更新来源失败");
    }

    @PostMapping("del/{id}")
    public R del(@PathVariable Integer id){
        boolean res = infoSourceService.removeById(id);
        if (res) return R.ok("删除成功");
        return R.failed("删除失败");
    }
}
