package com.lq.accident.service;

import com.lq.accident.model.Info;
import com.lq.accident.model.InfoSource;
import com.lq.accident.model.dto.InfoDTO;
import com.lq.accident.service.impl.InfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest
public class InfoServiceTest {

    @Autowired
    InfoServiceImpl infoService;

    @Test
    public void saveTest(){
        InfoSource infoSource = InfoSource.builder()
                .channelId(7)
                .url("https://www.sohu.com/a/578847202_391439")
                .urlTitle("浙江龙游6名落水游客已找到，均不幸遇难 ")
                .sourceDate(LocalDate.of(2022,8,22))
                .build();
        InfoSource infoSource2 = InfoSource.builder()
                .channelId(2)
                .url("https://www.toutiao.com/article/7133071895531880964/?wid=1661098998134")
                .urlTitle("彭州龙槽沟山洪致7人死亡，从这三个定律看悲剧背后的真相")
                .sourceDate(LocalDate.of(2022,8,18))
                .build();
        InfoDTO dto = InfoDTO.builder()
                .title("浙江龙游衢江河道意外溺水事件")
                .place("浙江龙游")
                .province("浙江省")
                .city("衢州市")
                .area("龙游县")
                .date(LocalDate.of(2022,8,21))
                .reason("意外溺水")
                .introduce("据央视新闻8月22日消息，2022年8月21日10时52分，浙江省龙游县衢江河道后厅段发生一起人员意外溺水事件。经公安、消防、应急、辖区街道和民间搜救队全力搜救，截至8月22日5时40分，6名落水人员都已找到，均不幸遇难。")
                .injuryNum(0)
                .deathNum(6)
                .missingNum(0)
                .type(8)
                .sourceUrls(Arrays.asList(infoSource))
                .tags(Arrays.asList("浙江龙游","溺水"))
                .build();

        infoService.saveInfo(dto);

    }
}
