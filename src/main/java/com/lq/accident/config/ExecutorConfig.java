package com.lq.accident.config;

import com.alibaba.fastjson.JSON;
import com.lq.accident.model.Info;
import com.lq.accident.model.InfoSource;
import com.lq.accident.model.dto.InfoDTO;
import com.lq.accident.service.impl.InfoServiceImpl;
import com.lq.accident.service.impl.InfoSourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Configuration
public class ExecutorConfig {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    InfoServiceImpl infoService;
    @Autowired
    InfoSourceServiceImpl infoSourceService;
    // 指定时间保存
    public void saveInf(){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        String uuid = UUID.randomUUID().toString();
        Set<String> keys = redisTemplate.keys("info:"+date+":");
        if (keys==null) return;
        for (String key : keys) {
            String val = redisTemplate.opsForValue().get(key);
            InfoDTO infoDTO = JSON.parseObject(val,InfoDTO.class);
            Info info = new Info();
            info.setStatus("0");
            infoService.save(info);
        }
    }
}
