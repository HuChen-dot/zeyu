package com.rewin.swhysc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.vo.InterestRateVo;
import com.rewin.swhysc.mapper.dao.InterestRateMapper;
import com.rewin.swhysc.service.InterestRateService;
import com.rewin.swhysc.util.StringUtils;
import com.rewin.swhysc.util.page.PageInfo;
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
    public List<InterestRateVo> getInterestRateList() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        List<InterestRateVo> interestRateListByMap = interestRateMapper.getInterestRateList(map);
        return interestRateListByMap;
    }

    @Override
    public PageInfo<InterestRateVo> getInterestRateVoList(Integer pageNo, Integer pageSize, String state, String startDate, String endDate) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(state)){
            map.put("state", state);
        }
        if(!StringUtils.isEmpty(startDate)){
            map.put("startDate", startDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            map.put("endDate", endDate+" 23:59:59");
        }
        List<InterestRateVo> interestRateListByMap = interestRateMapper.getInterestRateVoList(map);

        PageInfo<InterestRateVo> info = new PageInfo<InterestRateVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(interestRateListByMap);
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

    @Override
    public Integer setstateByIds(String ids,String state) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            param.put("state", state);
            return interestRateMapper.setstateByIds(param);
        }else{
            return null;
        }
    }
}
