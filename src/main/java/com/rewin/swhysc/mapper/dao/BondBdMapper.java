package com.rewin.swhysc.mapper.dao;


import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.bean.vo.BondBdVo;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------标的保证金
 */
public interface BondBdMapper  {
    List<BondBdVo> getBondBdList(Map<String, Object> param) throws Exception;

    List<BondBdVo> getBondBdState(Map<String, Object> param) throws Exception;

    BondBd getBondBdInfo(Map<String, Object> param) throws Exception;

    Integer insertBondBd(BondBd bondBd) throws Exception;

    Integer updateBondBd(BondBd bondBd) throws Exception;

    Integer subDelApproval(Map<String, Object> param) throws Exception;

    Integer delByIds(Map<String, Object> param) throws Exception;

    Integer setstateByIds(Map<String, Object> param) throws Exception;
}
