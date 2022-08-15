package com.lq.accident.controller;


import com.alibaba.fastjson.JSON;
import com.lq.accident.model.Info;
import com.lq.accident.model.dto.InfoDTO;
import com.lq.accident.service.impl.InfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@RestController
@RequestMapping("/accident/info")
public class InfoController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    InfoServiceImpl infoService;

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
