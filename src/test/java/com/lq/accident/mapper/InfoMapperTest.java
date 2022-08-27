package com.lq.accident.mapper;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.lq.accident.model.vo.InfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class InfoMapperTest {

    @Autowired
    InfoMapper infoMapper;
    @Test
    public void selectAllInfoTest(){
        List<InfoVO> infoVOList = infoMapper.selectAllInfo();
        Console.log(JSON.toJSONString(infoVOList,true));
    }
}
