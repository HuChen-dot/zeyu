package com.rewin.swhysc.mapper.dao;

import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.vo.ConvertRateVo;

import java.util.List;
import java.util.Map;

public interface ConvertRateMapper {

    List<ConvertRateVo> getConverRateList(Map<String, Object> param) throws Exception;

    List<ConvertRateVo> getConverRateState(Map<String, Object> param) throws Exception;

    ConvertRate getConverRateInfo(Map<String, Object> param) throws Exception;

    Integer insertConverRate(ConvertRate convertRate) throws Exception;

    Integer updateConvertRate(ConvertRate convertRate) throws Exception;

    Integer subDelApproval(Map<String, Object> param) throws Exception;

    Integer delByIds(Map<String, Object> param) throws Exception;
}
