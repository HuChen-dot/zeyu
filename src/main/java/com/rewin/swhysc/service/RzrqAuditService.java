package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.AuditRecord;
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

    /**
     * 根据id查询；返回单个对象
     */
    RzrqAuditVo getRzrqAuditById(Integer id) throws Exception;

    /**
     *
     */
    Integer examineRzrqAudit(AuditRecord auditRecord) throws Exception;
}
