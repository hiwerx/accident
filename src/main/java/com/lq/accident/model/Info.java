package com.lq.accident.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 事故信息
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("accident_info")
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 发生日期
     */
    @TableField("date")
    private LocalDate date;

    /**
     * 发生地
     */
    @TableField("place")
    private String place;

    /**
     * 省
     */
    @TableField("province")
    private String province;

    /**
     * 市
     */
    @TableField("city")
    private String city;

    /**
     * 区
     */
    @TableField("area")
    private String area;

    /**
     * 事故介绍
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 受伤人数
     */
    @TableField("injury_num")
    private Integer injuryNum;

    /**
     * 死亡人数
     */
    @TableField("death_num")
    private Integer deathNum;

    /**
     * 失联人数
     */
    @TableField("missing_num")
    private Integer missingNum;

    /**
     * 事故类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 事故原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 投递消息状态，0已投递，1已阅读，2已忽略
     */
    @TableField("status")
    private String status;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
