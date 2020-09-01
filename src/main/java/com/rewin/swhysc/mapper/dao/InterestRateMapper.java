package com.rewin.swhysc.mapper.dao;


import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.vo.InterestRateVo;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------利率费率
 */
public interface InterestRateMapper{
    List<InterestRateVo> getInterestRateList(Map<String, Object> param) throws Exception;

    List<InterestRateVo> getInterestRateVoList(Map<String, Object> param) throws Exception;

    InterestRate getInterestRateInfo(Map<String, Object> param) throws Exception;

    InterestRateVo showInterestRateInfo(Map<String, Object> param) throws Exception;

    Integer insertInterestRate(InterestRate interestRate) throws Exception;

    Integer updateInterestRate(InterestRate interestRate) throws Exception;

    Integer setstateByIds(Map<String, Object> param) throws Exception;
}
