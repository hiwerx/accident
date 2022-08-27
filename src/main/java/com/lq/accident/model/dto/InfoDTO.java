package com.lq.accident.model.dto;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.lq.accident.model.Info;
import com.lq.accident.model.InfoSource;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InfoDTO {

    private String title;
    /**
     * 发生日期
     */
    private LocalDate date;

    /**
     * 发生地
     */
    private String place;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 事故介绍
     */
    private String introduce;

    /**
     * 受伤人数
     */
    private Integer injuryNum;

    /**
     * 死亡人数
     */
    private Integer deathNum;

    /**
     * 失联人数
     */
    private Integer missingNum;

    /**
     * 事故类型
     */
    private Integer type;

    /**
     * 事故原因
     */
    private String reason;

    /**
     * 消息来源
     */
    private List<InfoSource> sourceUrls;

    /**
     * 信息关键词
     */
    private List<String> tags;

    public static void main(String[] args) {
        InfoDTO dto = InfoDTO.builder()
                .province("湖北")
                .city("随州")
                .area("随县")
                .date(LocalDate.now())
                .reason("森林火灾")
                .introduce("sdglkjsd")
                .injuryNum(2)
//                .sourceUrls(Arrays.asList("http1","http2"))
                .tags(Arrays.asList("t1","t2","t3"))
                .build();

        Console.log(JSON.toJSONString(dto,true));
        Info info = new Info();
        BeanUtils.copyProperties(dto,info);
        Console.log(JSON.toJSONString(info,true));

        Map mi = new HashMap();
        mi.put("23","wer");
        mi.put("io","iok");
        Map m2 = new HashMap();
        m2.put("m223","wer");
        m2.put("m2io","iok");
        List<Map> ll = Arrays.asList(mi,m2);
        for (Map map : ll) {
            map.put("rt",34);
        }
        System.out.println(mi);
        System.out.println(m2);
    }

}
