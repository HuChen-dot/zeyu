package com.rewin.swhysc.service;


import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.ConvertRate;

/**
 * 广告位表数据业务层
 */
public interface ConvertRateService {

    PageInfo<ConvertRate> getConverRateList(Integer pageNo, Integer pageSize,String stockCode,String stockName,String startDate,String endDate) throws Exception;

    ConvertRate getConvertRateInfo(String id) throws Exception;

    Integer insertConvertRate(ConvertRate convertRate) throws Exception;

    Integer updateConvertRate(ConvertRate convertRate) throws Exception;

    Integer deleteConvertRateAll() throws Exception;

    Integer updateConvertRateAll() throws Exception;
}
