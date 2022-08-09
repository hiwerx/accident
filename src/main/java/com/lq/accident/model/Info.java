package com.lq.accident.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 事故信息
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
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
    @TableField("provence")
    private String provence;

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
     * 事故类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
