package com.rewin.swhysc.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BondInvestment implements Serializable {
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
    //创建者
    private String creator;
    //更新者
    private String updater;
    //当前信息状态(1:待审核；2;已发布；4已删除，32已发布不可操作，64驳回）
    private Integer status;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;
    //离职日期
    private Date dimissionTime;
    //更新时间(字符型）
    private String updateTimes;
    //创建时间(字符型）
    private String createTimes;
    //离职日期(字符型）
    private String dimissionTimes;


}
