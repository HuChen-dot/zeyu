package com.rewin.swhysc.bean.vo;

import lombok.Data;


/**
 * 非现场开户人员点击审核后，返回的数据
 */
@Data
public class StaffAuditVo {
    //审核表主键id
    private Integer id;
    //部门名称
    private String deptName;
    //员工编号
    private Integer staffNo;
    //员工姓名
    private String staffName;
    //证书编号
    private String certificateNo;

    //信息类型
    private String staffType;
    //流程类型（1：代办流程， 2已办流程）
    private Integer flowType;
    //审核状态(0待审核；1：通过，2：驳回，）
    private String status;

    //审核意见
    private String auditOpinion;
    //人员类型
    private String personnelType;
    //操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
    private String operationId;
    //提交人
    private String submitter;
    //提交时间
    private String submitTime;
    //证书取得日期(字符串）
    private String certificatetimes;
    //证书类型
    private String certificatetype;
    //营业部
    private String sales;

}
