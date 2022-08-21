package com.lq.accident.controller;


import com.alibaba.fastjson.JSON;
import com.lq.accident.model.dto.InfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * tag与accident关联 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Slf4j
@RestController
@RequestMapping("/accident/tag")
public class AccidentTagController {

    @RequestMapping("test")
    public Object heh(@RequestBody InfoDTO infoDTO){
        log.info(JSON.toJSONString(infoDTO));
        return infoDTO;
    }

}
