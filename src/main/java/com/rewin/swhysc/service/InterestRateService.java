package com.rewin.swhysc.service;


import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.vo.InterestRateVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------利率费率
 */
public interface InterestRateService{
    List<InterestRateVo> getInterestRateList() throws Exception;

    PageInfo<InterestRateVo> getInterestRateVoList(Integer pageNo, Integer pageSize, String state, String startDate, String endDate) throws Exception;

    InterestRate getInterestRateInfo(String id) throws Exception;

    List<InterestRate> getInterestRateList(String id) throws Exception;

    Integer insertInterestRate(InterestRate interestRate) throws Exception;

    Integer updateInterestRate(InterestRate interestRate) throws Exception;

    Integer setstateByIds(String ids,String state) throws Exception;
}
