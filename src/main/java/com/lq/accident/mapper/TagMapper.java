package com.lq.accident.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lq.accident.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 标签 Mapper 接口
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag>  selectTagByInfoId(Integer infoId);
}
