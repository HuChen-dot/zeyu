package com.rewin.swhysc.service;



import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.vo.WarrantRatioVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;

/**
 * 融资融卷专栏------维持担保比例
 */
public interface WarrantRatioService  {
    List<WarrantRatioVo> getWarrantRatioList() throws Exception;

    PageInfo<WarrantRatioVo> getWarrantRatioVoList(Integer pageNo, Integer pageSize, String state, String startDate, String endDate) throws Exception;

    WarrantRatio getWarrantRatioInfo(String id) throws Exception;

    Integer insertWarrantRatio(WarrantRatio warrantRatio) throws Exception;

    Integer updateWarrantRatio(WarrantRatio warrantRatio) throws Exception;

    Integer setstateByIds(String ids,String state) throws Exception;
}
