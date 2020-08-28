package com.rewin.swhysc.bean.vo;

import lombok.Data;


/**
 * 后端返回页面的首页列表展示数据
 */
@Data
public class AuditRecordVo {
    //主键id
    private Integer id;
    //信息类型id(0 折算率；1标的；2维持担保比例；3利率费率；113非现场人员）
    private Integer infoTypeid;
    //信息类型名称(0 折算率；1标的；2维持担保比例；3利率费率；113非现场人员）
    private String infoTypeidName;
    //操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
    private String operationIdName;
    //流程类型（1：代办流程， 2已办流程）
    private String flowTypName;
    //审核状态（0待审核；1：通过，2：驳回，）
    private String statusName;
    //提交人
    private String submitter;
    //审核意见
    private String auditOpinion;

    //提交时间
    private String submitTime;

    //审核时间
    private String auditTime;

}
