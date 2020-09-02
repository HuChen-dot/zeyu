package com.rewin.swhysc.bean.vo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class RzrqAuditVo<T> implements Serializable {
    //主键id
    private Integer id;
    //信息类型id(0 折算率；1标的；2维持担保比例；3利率费率；113非现场人员）
    private Integer infoTypeid;
    //操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
    private Integer operationId;
    //流程类型（1：代办流程， 2已办流程）
    private Integer flowType;
    //审核状态（0待审核；1：通过，2：驳回，）
    private Integer status;
    //提交人
    private String submitter;
    //审核意见
    private String auditOpinion;
    //审核人
    private String auditor;
    //提交时间(字符类型）
    private String submitTimes;
    //审核时间（字符类型）
    private String auditTimes;
    //之前的id
    private String formerId;
    //信息主键id
    private String staffId;
    //文件地址
    private String fileUrl;
    //文件名称
    private String fileName;

    private List<T> infolist;

    private List<T> formList;
}
