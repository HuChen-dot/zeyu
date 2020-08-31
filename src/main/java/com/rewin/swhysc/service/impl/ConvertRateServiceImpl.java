package com.rewin.swhysc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.util.page.PageInfo;
import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.vo.ConvertRateVo;
import com.rewin.swhysc.mapper.dao.ConvertRateMapper;
import com.rewin.swhysc.service.ConvertRateService;
import com.rewin.swhysc.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告位表数据业务层
 */
@Service
public class ConvertRateServiceImpl implements ConvertRateService {

    @Resource
    private ConvertRateMapper convertRateMapper;

    @Override
    public PageInfo<ConvertRateVo> getConverRateList(Integer pageNo, Integer pageSize, String stockCode, String stockName, String trimDate) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);

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

        List<ConvertRateVo> converRateList = convertRateMapper.getConverRateList(map);

        PageInfo<ConvertRateVo> info = new PageInfo<ConvertRateVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(converRateList);
        return info;
    }

    @Override
    public List<ConvertRateVo> getConverRateState(String stockCode,String stockName,String trimDate) throws Exception {
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

        List<ConvertRateVo> converRateList = convertRateMapper.getConverRateState(map);

        return converRateList;
    }

    @Override
    public ConvertRate getConvertRateInfo(String id) throws Exception {
        if(!StringUtils.isEmpty(id)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", id);
            return convertRateMapper.getConverRateInfo(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertConvertRate(ConvertRate convertRate) throws Exception {
        return convertRateMapper.insertConverRate(convertRate);
    }

    @Override
    public Integer updateConvertRate(ConvertRate convertRate) throws Exception {
        return convertRateMapper.updateConvertRate(convertRate);
    }

    @Override
    public Integer subDelApproval(String ids) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            return convertRateMapper.subDelApproval(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer delByIds(String ids) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            return convertRateMapper.delByIds(param);
        }else{
            return null;
        }

    }
}
