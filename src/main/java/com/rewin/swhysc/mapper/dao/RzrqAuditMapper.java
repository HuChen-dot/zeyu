package com.rewin.swhysc.mapper.dao;


import com.rewin.swhysc.bean.RzrqAudit;

import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------审核流程
 */
public interface RzrqAuditMapper{
    List<RzrqAudit> getRzrqAuditList(Map<String, Object> param) throws Exception;

    RzrqAudit getRzrqAuditInfo(Map<String, Object> param) throws Exception;

    Integer insertRzrqAudit(RzrqAudit rzrqAudit) throws Exception;

    Integer updateRzrqAudit(RzrqAudit rzrqAudit) throws Exception;
}
