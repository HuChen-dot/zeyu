package com.rewin.swhysc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.mapper.dao.BondBdMapper;
import com.rewin.swhysc.service.BondBdService;
import com.rewin.swhysc.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------标的保证金
 */
@Service
public class BondBdServiceImpl implements BondBdService {
    @Resource
    private BondBdMapper bondBdMapper;

    @Override
    public PageInfo<BondBd> getBondBdList(Integer pageNo, Integer pageSize, String stockCode, String stockName,String startDate,String endDate) throws Exception {
       //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(startDate)){
            map.put("updateDate1", startDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            map.put("updateDate2", endDate+" 23:59:59");
        }
        List<BondBd> bondBdList = bondBdMapper.getBondBdList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<BondBd> info = new PageInfo<BondBd>(bondBdList);
        return info;
    }

    @Override
    public BondBd getBondBdInfo(String id) throws Exception {
        if(!StringUtils.isEmpty(id)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", id);
            return bondBdMapper.getBondBdInfo(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertBondBd(BondBd bondBd) throws Exception {
        return bondBdMapper.insertBondBd(bondBd);
    }

    @Override
    public Integer updateBondBd(BondBd bondBd) throws Exception {
        return bondBdMapper.updateBondBd(bondBd);
    }

    @Override
    public Integer deleteBondBdAll() throws Exception {
        return bondBdMapper.deleteBondBdAll();
    }

    @Override
    public Integer updateBondBdAll() throws Exception {
        return bondBdMapper.updateBondBdAll();
    }
}
