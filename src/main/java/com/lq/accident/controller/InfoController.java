package com.lq.accident.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 事故信息 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@RestController
@RequestMapping("/accident/info")
public class InfoController {

    @RequestMapping("df")
    public String ui(){
        return "sdfsdg";
    }

}
