package com.rewin.swhysc.bean.vo;

import lombok.Data;


/**
 * 返回给前端首页列表数据
 */
@Data
public class BondInvestmentVo {
    //主键id
    private Integer id;
    //部门名称
    private String deptName;
    //员工姓名
    private String staffName;
    //信息类型
    private Integer staffType;
    //职务
    private String duty;
    //岗位分类
    private String postType;
    //办公电话
    private String workPhone;
    //人员分类
    private String staffSort;
    //当前信息状态(1:待审核；2;已发布；4已删除，32已发布不可操作，64驳回）
    private Integer status;
    //离职日期(字符型）
    private String dimissionTimes;
}
