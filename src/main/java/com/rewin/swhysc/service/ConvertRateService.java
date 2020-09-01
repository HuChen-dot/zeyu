package com.rewin.swhysc.service;


import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.vo.ConvertRateVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;

/**
 * 广告位表数据业务层
 */
public interface ConvertRateService {

    PageInfo<ConvertRateVo> getConverRateList(Integer pageNo, Integer pageSize, String stockCode, String stockName, String trimDate) throws Exception;

    List<ConvertRateVo> getConverRateList(String stockCode, String stockName, String trimDate) throws Exception;

    List<ConvertRateVo> getConverRateState(String stockCode, String stockName, String trimDate) throws Exception;

    ConvertRate getConvertRateInfo(String id) throws Exception;

    Integer insertConvertRate(ConvertRate convertRate) throws Exception;

    Integer insertConvertRateList(List<ConvertRate> convertRateList) throws Exception;

    Integer updateConvertRate(ConvertRate convertRate) throws Exception;

    Integer subDelApproval(String ids) throws Exception;

    Integer delByIds(String ids) throws Exception;

    Integer setstateByIds(String ids,String state) throws Exception;
}
