package com.rewin.swhysc.bean.vo;


import lombok.Data;

/**
 * 债券投资人员管理
 * 修改信息时根据id查询信息回添
 */
@Data
public class UpdataBondInvestmentVo {
    //主键id
    private Integer id;
    //部门名称
    private String deptName;
    //员工姓名
    private String staffName;
    //职务
    private String duty;
    //岗位分类
    private String postType;
    //办公电话
    private String workPhone;
    //人员分类
    private String staffSort;
    //信息类型
    private Integer staffType;
}
