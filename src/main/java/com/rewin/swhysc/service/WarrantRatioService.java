package com.rewin.swhysc.service;


import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.WarrantRatio;

/**
 * 融资融卷专栏------维持担保比例
 */
public interface WarrantRatioService  {
    PageInfo<WarrantRatio> getWarrantRatioList(Integer pageNo, Integer pageSize, String state,String startDate,String endDate) throws Exception;

    WarrantRatio getWarrantRatioInfo(String id) throws Exception;

    Integer insertWarrantRatio(WarrantRatio warrantRatio) throws Exception;

    Integer updateWarrantRatio(WarrantRatio warrantRatio) throws Exception;

}
