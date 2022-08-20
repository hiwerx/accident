package com.lq.accident.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * tag与accident关联
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("accident_tag")
public class AccidentTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事故id
     */
    @TableField("info_id")
    private Integer infoId;

    /**
     * 标签id
     */
    @TableField("tag_id")
    private Integer tagId;


}
