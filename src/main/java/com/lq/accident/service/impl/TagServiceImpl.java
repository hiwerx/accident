package com.lq.accident.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.accident.mapper.TagMapper;
import com.lq.accident.model.Tag;
import com.lq.accident.service.ITagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
