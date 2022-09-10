package com.lq.accident.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lq.accident.model.Tag;

import java.util.List;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
public interface ITagService extends IService<Tag> {

    List<Tag> getTagByInfoId(Integer infoId);

}
