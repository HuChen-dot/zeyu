package com.rewin.swhysc.bean.vo;

import com.rewin.swhysc.bean.NotOpenStaff;
import lombok.Data;

import java.util.List;

/**
 * 审核非现场公开人员
 * 批量删除，全量删除，批量上传返回数据
 */
@Data
public class DeleStaffAuditVo {
    //审核表主键id
    private Integer id;
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
    //文件地址
    private String fileUrl;
    //文件名称
    private String fileName;

    //    批量删除人员名单
    List<BatchesRemVo> list;


}
