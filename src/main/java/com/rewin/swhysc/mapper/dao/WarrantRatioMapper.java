package com.rewin.swhysc.mapper.dao;


import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.vo.WarrantRatioVo;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------维持担保比例
 */
public interface WarrantRatioMapper  {
    List<WarrantRatioVo> getWarrantRatioVoList(Map<String, Object> param) throws Exception;

    List<WarrantRatioVo> getWarrantRatioList(Map<String, Object> param) throws Exception;

    WarrantRatio getWarrantRatioInfo(Map<String, Object> param) throws Exception;

    WarrantRatioVo showWarrantRatioInfo(Map<String, Object> param) throws Exception;

    Integer insertWarrantRatio(WarrantRatio warrantRatio) throws Exception;

    Integer updateWarrantRatio(WarrantRatio warrantRatio) throws Exception;

    Integer setstateByIds(Map<String, Object> param) throws Exception;
}
