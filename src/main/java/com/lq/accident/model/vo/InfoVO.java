package com.lq.accident.model.vo;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lq.accident.model.Tag;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InfoVO {
    private Integer id;
    private String title;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
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

    private String[] introduces;

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
    private String type;

    /**
     * 事故原因
     */
    private String reason;

    /**
     * 消息来源
     */
    private List<InfoSourceVO> sourceUrls;

    /**
     * 信息关键词
     */
    private List<Tag> tags;

    public static void main(String[] args) {


        Tag t1 = Tag.builder().id(1).tag("t1").build();
        Tag t2 = Tag.builder().id(2).tag("t2").build();
        Tag t3 = Tag.builder().id(3).tag("t3").build();
        Tag t4 = Tag.builder().id(4).tag("t4").build();

        InfoSourceVO s1 = InfoSourceVO.builder().channel("央视新闻")
                .url("http://sd.com")
                .urlTitle("地址1").sourceDate(LocalDate.now()).build();

        InfoSourceVO s2 = InfoSourceVO.builder().channel("新浪新闻")
                .url("http://sd.com")
                .urlTitle("地址1").sourceDate(LocalDate.now()).build();

        InfoSourceVO s3 = InfoSourceVO.builder().channel("网易新闻")
                .url("http://sd.com")
                .urlTitle("地址1").sourceDate(LocalDate.now()).build();

        InfoVO dto = InfoVO.builder()
                .province("湖北")
                .city("随州")
                .area("随县")
                .date(LocalDate.now())
                .reason("森林火灾")
                .introduce("sdglkjsd")
                .injuryNum(2)
                .sourceUrls(Arrays.asList(s1,s2,s3))
                .tags(Arrays.asList(t1,t2,t3,t4))
                .build();

        Console.log(JSON.toJSONString(dto,true));


    }
}
