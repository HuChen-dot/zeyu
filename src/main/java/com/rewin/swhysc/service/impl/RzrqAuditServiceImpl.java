package com.rewin.swhysc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.vo.RzrqAuditVo;
import com.rewin.swhysc.mapper.dao.AuditRecordMapper;
import com.rewin.swhysc.service.RzrqAuditService;
import com.rewin.swhysc.util.StringUtils;
import com.rewin.swhysc.util.page.PageInfo;
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
    private AuditRecordMapper auditRecordMapper;

    public PageInfo<RzrqAuditVo> getRzrqAuditList(Integer pageNum, Integer pageSize, String infoTypeid, String startDate,
                                                  String endDate, String operationId, String flowType, String status) throws Exception {
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(infoTypeid)){
            map.put("infoTypeid", infoTypeid);
        }
        if(!StringUtils.isEmpty(operationId)){
            map.put("operationId", operationId);
        }
        if(!StringUtils.isEmpty(flowType)){
            map.put("flowType", flowType);
        }
        if(!StringUtils.isEmpty(status)){
            map.put("status", status);
        }
        if(!StringUtils.isEmpty(startDate)){
            map.put("startDate", startDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            map.put("endDate", endDate+" 23:59:59");
        }

        List<RzrqAuditVo> rzrqAuditList = auditRecordMapper.getRzrqAuditList(map);

        PageInfo<RzrqAuditVo> info = new PageInfo<RzrqAuditVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(rzrqAuditList);
        return info;
    }
}
