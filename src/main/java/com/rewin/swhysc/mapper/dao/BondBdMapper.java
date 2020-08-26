package com.rewin.swhysc.mapper.dao;


import com.rewin.swhysc.bean.BondBd;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------标的保证金
 */
public interface BondBdMapper  {
    List<BondBd> getBondBdList(Map<String, Object> param) throws Exception;

    BondBd getBondBdInfo(Map<String, Object> param) throws Exception;

    Integer insertBondBd(BondBd bondBd) throws Exception;

    Integer updateBondBd(BondBd bondBd) throws Exception;

    Integer deleteBondBdAll() throws Exception;

    Integer updateBondBdAll() throws Exception;
}
