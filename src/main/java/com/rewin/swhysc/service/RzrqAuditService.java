package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.vo.RzrqAuditVo;
import com.rewin.swhysc.util.page.PageInfo;

/**
 * 融资融卷专栏------审核流程
 */
public interface RzrqAuditService {
    /**
     * 首页查询， 根据条件查询融资融券列表
     */
    PageInfo<RzrqAuditVo> getRzrqAuditList(Integer pageNum, Integer pageSize, String infoTypeid, String startDate, String endDate, String operationId, String flowType, String status) throws Exception;


}
