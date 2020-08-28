package com.rewin.swhysc.service;


import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.bean.vo.BondBdVo;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------标的保证金
 */
public interface BondBdService  {
    PageInfo<BondBdVo> getBondBdList(Integer pageNo, Integer pageSize, String stockCode, String stockName, String trimDate) throws Exception;

    List<BondBdVo> getBondBdState(String stockCode, String stockName, String trimDate) throws Exception;

    BondBd getBondBdInfo(String id) throws Exception;

    Integer insertBondBd(BondBd bondBd) throws Exception;

    Integer updateBondBd(BondBd bondBd) throws Exception;

    Integer subDelApproval(String ids) throws Exception;

    Integer delByIds(String ids) throws Exception;
}
