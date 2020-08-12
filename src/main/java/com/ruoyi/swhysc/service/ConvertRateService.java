package com.ruoyi.swhysc.service;


import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.ConvertRate;

import java.sql.Date;

/**
 * 广告位表数据业务层
 */
public interface ConvertRateService {

    PageInfo<ConvertRate> getConverRateList(Integer pageNo, Integer pageSize,String stockCode,String stockName,String updateDate) throws Exception;
}
