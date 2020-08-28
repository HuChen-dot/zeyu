package com.rewin.swhysc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.vo.WarrantRatioVo;
import com.rewin.swhysc.mapper.dao.WarrantRatioMapper;
import com.rewin.swhysc.service.WarrantRatioService;
import com.rewin.swhysc.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------维持担保比例
 */
@Service
public class WarrantRatioServiceImpl implements WarrantRatioService {
    @Resource
    private WarrantRatioMapper warrantRatioMapper;

    @Override
    public List<WarrantRatioVo> getWarrantRatioList() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        List<WarrantRatioVo> warrantRatioList = warrantRatioMapper.getWarrantRatioList(map);
        return warrantRatioList;
    }

    @Override
    public PageInfo<WarrantRatioVo> getWarrantRatioVoList(Integer pageNo, Integer pageSize, String state,String startDate,String endDate) throws Exception {
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
        List<WarrantRatioVo> warrantRatioList = warrantRatioMapper.getWarrantRatioVoList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<WarrantRatioVo> info = new PageInfo<WarrantRatioVo>(warrantRatioList);
        return info;
    }

    @Override
    public WarrantRatio getWarrantRatioInfo(String id) throws Exception {
        if(!StringUtils.isEmpty(id)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", id);
            return warrantRatioMapper.getWarrantRatioInfo(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertWarrantRatio(WarrantRatio interestRate) throws Exception {
        return warrantRatioMapper.insertWarrantRatio(interestRate);
    }

    @Override
    public Integer updateWarrantRatio(WarrantRatio interestRate) throws Exception {
        return warrantRatioMapper.updateWarrantRatio(interestRate);
    }
}
