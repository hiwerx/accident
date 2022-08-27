package com.lq.accident.model.vo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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
public class InfoSourceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 信息来源地址
     */
    private String url;

    /**
     * 来源描述
     */
    private String urlTitle;

    private String channel;

    /**
     * 来源信息时间
     */
    private LocalDate sourceDate;



}
