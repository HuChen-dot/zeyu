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
    private String staffType;
    //职务
    private String duty;
    //岗位分类
    private Integer postType;
    //办公电话
    private String workPhone;
    //离职日期
    private Date dimissionTime;
    //人员分类
    private Integer staffSort;
    //创建者
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;
    //当前信息状态(1:待审核；2;已发布；4已删除，32已发布不可操作，64驳回）
    private Integer status;


}
