package com.rewin.swhysc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.vo.BondBdVo;
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
    public PageInfo<BondBdVo> getBondBdList(Integer pageNo, Integer pageSize, String stockCode, String stockName,String trimDate) throws Exception {
       //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(trimDate)){
            map.put("trimDate", trimDate+" 00:00:00");
        }
        List<BondBdVo> bondBdList = bondBdMapper.getBondBdList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<BondBdVo> info = new PageInfo<BondBdVo>(bondBdList);
        return info;
    }

    @Override
    public List<BondBdVo> getBondBdState(String stockCode, String stockName, String trimDate) throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(trimDate)){
            map.put("trimDate", trimDate+" 00:00:00");
        }

        List<BondBdVo> bondBdList = bondBdMapper.getBondBdState(map);

        return bondBdList;
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
    public Integer subDelApproval(String ids) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            return bondBdMapper.subDelApproval(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer delByIds(String ids) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            return bondBdMapper.delByIds(param);
        }else{
            return null;
        }
    }
}
