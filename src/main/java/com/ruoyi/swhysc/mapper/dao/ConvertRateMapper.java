package com.ruoyi.swhysc.mapper.dao;

import com.ruoyi.swhysc.bean.ConvertRate;

import java.util.List;
import java.util.Map;

public interface ConvertRateMapper {

    List<ConvertRate> getConverRateList(Map<String, Object> param) throws Exception;

    ConvertRate getConverRateInfo(Map<String, Object> param) throws Exception;

    Integer insertConverRate(ConvertRate convertRate) throws Exception;

    Integer updateConvertRate(ConvertRate convertRate) throws Exception;

    Integer deleteConverRateAll() throws Exception;

}