package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.vo.AuditRecordVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface AuditRecordService {

    /**
     * 根据id查询；返回单个对象
     */
    AuditRecord getAuditRecordById(Integer id) throws Exception;

    /**
     * 首页查询， 根据条件查询信息列表
     */
    PageInfo<AuditRecordVo> getAuditRecordList(Map<String, Object> param, Integer pageNum, Integer pageSize) throws Exception;

    /**
     * 统一审核接口
     */
    Integer verifyInfo(AuditRecord auditRecord, Integer falg, String auditOpinion) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddAuditRecord(AuditRecord auditRecord) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyAuditRecord(AuditRecord auditRecord) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteAuditRecordById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<AuditRecord> queryAuditRecordPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
