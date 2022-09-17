package com.lq.accident.mapper;

import com.lq.accident.model.InfoSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lq.accident.model.vo.InfoSourceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 信息来源 Mapper 接口
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Repository
public interface InfoSourceMapper extends BaseMapper<InfoSource> {
    List<InfoSourceVO> selectInfoSourceVOByInfoId(Integer infoId);
}
