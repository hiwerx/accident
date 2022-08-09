package com.lq.accident.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 事故类型
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("accident_type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名称
     */
    @TableField("name")
    private String name;


}
