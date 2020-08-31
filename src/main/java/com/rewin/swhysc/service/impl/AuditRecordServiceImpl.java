package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.vo.AuditRecordVo;
import com.rewin.swhysc.mapper.dao.AuditRecordMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuditRecordServiceImpl implements AuditRecordService {


    @Resource
    private AuditRecordMapper auditRecordMapper;

    @Resource
    TokenService tokenService;

    /**
     * 根据id查询；返回单个对象
     */
    public AuditRecord getAuditRecordById(Integer id) throws Exception {
        return auditRecordMapper.getAuditRecordById(id);
    }

    /**
     * 首页查询， 根据条件查询信息列表
     */
    public PageInfo<AuditRecordVo> getAuditRecordList(Map<String, Object> param, Integer pageNum, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);
        List<AuditRecord> auditRecord = auditRecordMapper.getAuditRecordList(param);
        List<AuditRecordVo> auditRecordVo = new ArrayList<AuditRecordVo>();
        for (AuditRecord record : auditRecord) {
            AuditRecordVo AuditRecordVo = new AuditRecordVo();
            BeanUtils.copyProperties(record, AuditRecordVo);
            AuditRecordVo.setSubmitTime(record.getSubmitTimes());
            //判断封装信息类型
            if (record.getInfoTypeid() == 113) {
                AuditRecordVo.setInfoTypeidName("非现场开户人员信息");
            } else if (record.getInfoTypeid() == 114) {
                AuditRecordVo.setInfoTypeidName("债券投资相关人员信息");
            } else if (record.getInfoTypeid() == 115) {
                AuditRecordVo.setInfoTypeidName("私募资产管理业务从业人员信息");
            }
            //判断封装操作类型
            if (record.getOperationId() == 1) {
                AuditRecordVo.setOperationIdName("新增");
            } else if (record.getOperationId() == 2) {
                AuditRecordVo.setOperationIdName("批量上传");
            } else if (record.getOperationId() == 4) {
                AuditRecordVo.setOperationIdName("批量删除");
            } else if (record.getOperationId() == 8) {
                AuditRecordVo.setOperationIdName("全量删除");
            } else if (record.getOperationId() == 16) {
                AuditRecordVo.setOperationIdName("修改");
            }
            //判断封装流程类型
            if (record.getFlowType() == 1) {
                AuditRecordVo.setFlowTypName("代办流程");
            } else if (record.getFlowType() == 2) {
                AuditRecordVo.setFlowTypName("已办流程");
            }
            //判断状态类型
            if (record.getStatus() == 1) {
                AuditRecordVo.setStatusName("通过");
            } else if (record.getFlowType() == 2) {
                AuditRecordVo.setStatusName("驳回");
            }
            auditRecordVo.add(AuditRecordVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<AuditRecordVo> info = new PageInfo<AuditRecordVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(auditRecordVo);
        return info;
    }


    /**
     * 统一审核接口
     */
    @Override
    public Integer verifyInfo(AuditRecord auditRecord, Integer falg, String auditOpinion) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        AuditRecord audit = new AuditRecord();
        //表名
        String tableNames = auditRecord.getTableNames();
        System.err.println(tableNames);
        Map<String, Object> table = new ConcurrentHashMap<>();
        //等于1，表示当前是通过操作
        if (falg == 1) {
            Integer operationId = auditRecord.getOperationId();
            String formerId = auditRecord.getFormerId();
            //1表示新增操作，2表示批量上传操作
            if (operationId == 1 || operationId == 2) {
                //获取新增的数据id,并转换成数组
                String[] split = auditRecord.getStaffId().split(",");
                //依次把新增的数据状态修改为已发布
                for (String id : split) {
                    table.put("tabName", tableNames);
                    table.put("status", 2);
                    table.put("id", Integer.parseInt(id));
                    auditRecordMapper.dynamicUpdateTable(table);
                }
            }
            //4表示批量删除操作，2表示全量删除操作
            if (operationId == 4 || operationId == 8) {
                //获取需要删除的数据id,并转换成数组
                String[] split = auditRecord.getFormerId().split(",");
                //依次把删除的数据状态修改为已删除
                for (String id : split) {
                    table.put("tabName", tableNames);
                    table.put("status", 4);
                    table.put("id", Integer.parseInt(id));
                    auditRecordMapper.dynamicUpdateTable(table);
                }
            }
            //16表示修改操作
            if (operationId == 16) {
                //把新增的数据的状态修改为已发布
                table.put("tabName", tableNames);
                table.put("status", 2);
                table.put("id", Integer.parseInt(auditRecord.getStaffId()));
                auditRecordMapper.dynamicUpdateTable(table);
                //把原数据的数据的状态修改为已删除
                table.put("tabName", tableNames);
                table.put("status", 4);
                table.put("id", Integer.parseInt(auditRecord.getFormerId()));
                auditRecordMapper.dynamicUpdateTable(table);
            }
            audit.setStatus(1);
        } else {
            //下面是驳回逻辑
            Integer operationId = auditRecord.getOperationId();
            String formerId = auditRecord.getFormerId();
            //1表示新增操作，2表示批量上传操作
            if (operationId == 1 || operationId == 2) {
                //获取新增的数据id,并转换成数组
                String[] split = auditRecord.getStaffId().split(",");
                //依次把新增的数据状态修改为已删除
                for (String id : split) {
                    table.put("tabName", tableNames);
                    table.put("status", 4);
                    table.put("id", Integer.parseInt(id));
                    auditRecordMapper.dynamicUpdateTable(table);
                }
            }
            //4表示批量删除操作，2表示全量删除操作
            if (operationId == 4 || operationId == 8) {
                //获取需要删除的数据id,并转换成数组
                String[] split = auditRecord.getFormerId().split(",");
                //依次把删除的数据状态修改为已发布
                for (String id : split) {
                    table.put("tabName", tableNames);
                    table.put("status", 2);
                    table.put("id", Integer.parseInt(id));
                    auditRecordMapper.dynamicUpdateTable(table);
                }
            }
            //16表示修改操作
            if (operationId == 16) {
                //把新增的数据的状态修改为已删除
                table.put("tabName", tableNames);
                table.put("status", 4);
                table.put("id", Integer.parseInt(auditRecord.getStaffId()));
                auditRecordMapper.dynamicUpdateTable(table);
                //把原数据的数据的状态修改为已发布
                table.put("tabName", tableNames);
                table.put("status", 2);
                table.put("id", Integer.parseInt(auditRecord.getFormerId()));
                auditRecordMapper.dynamicUpdateTable(table);
            }
            audit.setStatus(2);
        }
        //修改审核表当前数据
        audit.setId(auditRecord.getId());
        audit.setFlowType(2);
        audit.setAuditor(loginUser.getUsername());
        audit.setAuditTime(new Date());
        System.err.println("审核意见：=====》" + auditOpinion);
        audit.setAuditOpinion("null".equals(auditOpinion) ? " " : auditOpinion);
        auditRecordMapper.updateAuditRecord(audit);
        return null;
    }


    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddAuditRecord(AuditRecord auditRecord) throws Exception {
        return auditRecordMapper.insertAuditRecord(auditRecord);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyAuditRecord(AuditRecord auditRecord) throws Exception {
        return auditRecordMapper.updateAuditRecord(auditRecord);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteAuditRecordById(Long id) throws Exception {
        Map
                <String, Object> map = new HashMap
                <String, Object>();
        map.put("invid", id);
        return auditRecordMapper.deleteAuditRecordByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<AuditRecord> queryAuditRecordPageByMap(Map
                                                                   <String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<AuditRecord> auditRecordList = auditRecordMapper.getAuditRecordListByMap(param);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<AuditRecord> info = new PageInfo<AuditRecord>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(auditRecordList);
        return info;
    }

}
