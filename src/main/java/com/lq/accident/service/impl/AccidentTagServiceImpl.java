package com.lq.accident.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.accident.mapper.AccidentTagMapper;
import com.lq.accident.model.AccidentTag;
import com.lq.accident.service.IAccidentTagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * tag与accident关联 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-20
 */
@Service
public class AccidentTagServiceImpl extends ServiceImpl<AccidentTagMapper, AccidentTag> implements IAccidentTagService {

}
