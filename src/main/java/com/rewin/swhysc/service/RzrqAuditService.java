package com.rewin.swhysc.service;


import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.RzrqAudit;

/**
 * 融资融卷专栏------审核流程
 */
public interface RzrqAuditService {
    PageInfo<RzrqAudit> getRzrqAuditList(Integer pageNo, Integer pageSize, String infoType, String commitTime, String auditStatus, String handleType, String state) throws Exception;

    RzrqAudit getRzrqAuditInfo(String id) throws Exception;

    Integer insertRzrqAudit(RzrqAudit rzrqAudit) throws Exception;

    Integer updateRzrqAudit(RzrqAudit rzrqAudit) throws Exception;

}
