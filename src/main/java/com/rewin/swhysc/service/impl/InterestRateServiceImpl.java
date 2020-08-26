package com.rewin.swhysc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.vo.InterestRateVo;
import com.rewin.swhysc.mapper.dao.InterestRateMapper;
import com.rewin.swhysc.service.InterestRateService;
import com.rewin.swhysc.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------利率费率
 */
@Service
public class InterestRateServiceImpl implements InterestRateService {
    @Resource
    private InterestRateMapper interestRateMapper;

    @Override
    public PageInfo<InterestRate> getInterestRateList(Integer pageNo, Integer pageSize, String state,String startDate,String endDate) throws Exception {
       //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(state)){
            map.put("state", state);
        }
        if(!StringUtils.isEmpty(startDate)){
            map.put("createDate1", startDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            map.put("createDate2", endDate+" 23:59:59");
        }
        List<InterestRate> interestRateListByMap = interestRateMapper.getInterestRateList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<InterestRate> info = new PageInfo<InterestRate>(interestRateListByMap);
        return info;
    }

    @Override
    public PageInfo<InterestRateVo> getInterestRateVoList(Integer pageNo, Integer pageSize, String state, String startDate, String endDate) throws Exception {
        //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(state)){
            map.put("state", state);
        }
        if(!StringUtils.isEmpty(startDate)){
            map.put("createDate1", startDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            map.put("createDate2", endDate+" 23:59:59");
        }
        List<InterestRateVo> interestRateListByMap = interestRateMapper.getInterestRateVoList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<InterestRateVo> info = new PageInfo<InterestRateVo>(interestRateListByMap);
        return info;
    }

    @Override
    public InterestRate getInterestRateInfo(String id) throws Exception {
        if(!StringUtils.isEmpty(id)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", id);
            return interestRateMapper.getInterestRateInfo(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertInterestRate(InterestRate interestRate) throws Exception {
        return interestRateMapper.insertInterestRate(interestRate);
    }

    @Override
    public Integer updateInterestRate(InterestRate interestRate) throws Exception {
        return interestRateMapper.updateInterestRate(interestRate);
    }
}
