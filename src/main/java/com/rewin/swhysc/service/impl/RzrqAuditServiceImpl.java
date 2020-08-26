package com.rewin.swhysc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.RzrqAudit;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.mapper.dao.RzrqAuditMapper;
import com.rewin.swhysc.service.RzrqAuditService;
import com.rewin.swhysc.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------审核流程
 */
@Service
public class RzrqAuditServiceImpl implements RzrqAuditService {
    @Resource
    private RzrqAuditMapper rzrqAuditMapper;

    @Override
    public PageInfo<RzrqAudit> getRzrqAuditList(Integer pageNo, Integer pageSize, String infoType, String commitTime, String auditStatus, String handleType, String state) throws Exception {
        //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(state)){
            map.put("state", state);
        }
        if(!StringUtils.isEmpty(infoType)){
            map.put("infoType", infoType);
        }
        if(!StringUtils.isEmpty(auditStatus)){
            map.put("auditStatus", auditStatus);
        }
        if(!StringUtils.isEmpty(handleType)){
            map.put("handleType", handleType);
        }
        if(!StringUtils.isEmpty(commitTime)){
            map.put("commitTime1", commitTime+" 00:00:00");
            map.put("commitTime2", commitTime+" 23:59:59");
        }
        List<RzrqAudit> rzrqAuditList = rzrqAuditMapper.getRzrqAuditList(map);

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<RzrqAudit> info = new PageInfo<RzrqAudit>(rzrqAuditList);
        return info;
    }

    @Override
    public RzrqAudit getRzrqAuditInfo(String id) throws Exception {
        if(!StringUtils.isEmpty(id)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", id);
            return rzrqAuditMapper.getRzrqAuditInfo(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertRzrqAudit(RzrqAudit rzrqAudit) throws Exception {
        return rzrqAuditMapper.insertRzrqAudit(rzrqAudit);
    }

    @Override
    public Integer updateRzrqAudit(RzrqAudit rzrqAudit) throws Exception {
        return rzrqAuditMapper.updateRzrqAudit(rzrqAudit);
    }
}
