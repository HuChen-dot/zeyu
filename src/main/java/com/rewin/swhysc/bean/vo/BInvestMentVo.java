package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * 点击审核按钮
 * 返回给页面，债券投资人员信息
 */
@Data
public class BInvestMentVo {
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
    //操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
    private String operationId;
    //提交人
    private String submitter;
    //提交时间
    private String submitTime;


    //部门名称
    private String deptName;
    //员工姓名
    private String staffName;
    //人员分类
    private String staffSort;
    //职务
    private String duty;
    //岗位分类
    private String postType;
    //办公电话
    private String workPhone;
    //    批量删除人员名单
    List<BatchesRemVo> list;
    //文件地址
    private String fileUrl;
    //文件名称
    private String fileName;

}
