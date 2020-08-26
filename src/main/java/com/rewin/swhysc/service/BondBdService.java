package com.rewin.swhysc.service;


import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.BondBd;

import java.util.Map;

/**
 * 融资融卷专栏------标的保证金
 */
public interface BondBdService  {
    PageInfo<BondBd> getBondBdList(Integer pageNo, Integer pageSize, String stockCode, String stockName,String startDate,String endDate) throws Exception;

    BondBd getBondBdInfo(String id) throws Exception;

    Integer insertBondBd(BondBd bondBd) throws Exception;

    Integer updateBondBd(BondBd bondBd) throws Exception;

    Integer deleteBondBdAll() throws Exception;

    Integer updateBondBdAll() throws Exception;
}
