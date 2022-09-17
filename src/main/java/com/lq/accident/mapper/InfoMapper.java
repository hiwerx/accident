package com.lq.accident.mapper;

import com.lq.accident.model.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lq.accident.model.dto.SearchDTO;
import com.lq.accident.model.page.MyPage;
import com.lq.accident.model.vo.InfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 事故信息 Mapper 接口
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
@Repository
public interface InfoMapper extends BaseMapper<Info> {

    List<InfoVO> selectAllInfo(SearchDTO dto);

    MyPage<InfoVO> selectAllInfo1(MyPage page, SearchDTO dto);

}
