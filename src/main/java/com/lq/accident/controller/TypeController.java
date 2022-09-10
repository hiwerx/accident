package com.lq.accident.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lq.accident.model.Channel;
import com.lq.accident.model.Type;
import com.lq.accident.service.IChannelService;
import com.lq.accident.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 事故类型 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@RestController
@RequestMapping("/accident/type")
public class TypeController {

    @Autowired
    ITypeService typeService;

    @Autowired
    IChannelService channelService;

    @GetMapping("list")
    public R<List<Type>> getType(){
        return R.ok(typeService.list());
    }

    @GetMapping("channelList")
    public R<List<Channel>> getChannel(){
        return R.ok(channelService.list());
    }

}
