package com.lq.accident.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 信息来源
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("info_source")
public class InfoSource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事故id
     */
    @TableField("info_id")
    private Integer infoId;

    /**
     * 信息来源地址
     */
    @TableField("url")
    private String url;

    /**
     * 来源描述
     */
    @TableField("url_title")
    private String urlTitle;

    @TableField("channel_id")
    private Integer channelId;

    /**
     * 来源信息时间
     */
    @TableField("source_date")
    private LocalDate sourceDate;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
