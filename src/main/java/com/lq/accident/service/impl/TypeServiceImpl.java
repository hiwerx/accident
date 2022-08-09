package com.lq.accident.service.impl;

import com.lq.accident.model.Type;
import com.lq.accident.mapper.TypeMapper;
import com.lq.accident.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事故类型 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

}
