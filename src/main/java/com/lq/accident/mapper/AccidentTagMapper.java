package com.lq.accident.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lq.accident.model.AccidentTag;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * tag与accident关联 Mapper 接口
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Repository
public interface AccidentTagMapper extends BaseMapper<AccidentTag> {

}
