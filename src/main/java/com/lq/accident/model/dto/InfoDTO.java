package com.lq.accident.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoDTO {
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
    private String provence;

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
    private String sourceUrl;


}
