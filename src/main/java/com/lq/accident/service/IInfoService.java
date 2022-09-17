package com.lq.accident.service;

import com.lq.accident.model.Info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lq.accident.model.dto.SearchDTO;
import com.lq.accident.model.page.MyPage;
import com.lq.accident.model.vo.InfoVO;

/**
 * <p>
 * 事故信息 服务类
 * </p>
 *
 * @author lq.com
 * @since 2022-08-10
 */
public interface IInfoService extends IService<Info> {

    MyPage<InfoVO> mixPageSearch(SearchDTO dto);
}
