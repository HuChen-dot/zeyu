package com.rewin.swhysc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.ConvertRate;
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
    public PageInfo<ConvertRate> getConverRateList(Integer pageNo, Integer pageSize,String stockCode,String stockName,String updateDate) throws Exception {
        List<ConvertRate> AdvertiseVoList = new ArrayList<>();
        //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(updateDate)){
            map.put("updateDate1", updateDate+" 00:00:00");
            map.put("updateDate2", updateDate+" 23:59:59");
        }
        List<ConvertRate> advertiseListByMap = convertRateMapper.getConverRateList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<ConvertRate> info = new PageInfo<ConvertRate>(advertiseListByMap);
        return info;
    }

}
