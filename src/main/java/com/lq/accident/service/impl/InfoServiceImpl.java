package com.lq.accident.service.impl;

import com.lq.accident.model.Info;
import com.lq.accident.mapper.InfoMapper;
import com.lq.accident.service.IInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事故信息 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {

}
