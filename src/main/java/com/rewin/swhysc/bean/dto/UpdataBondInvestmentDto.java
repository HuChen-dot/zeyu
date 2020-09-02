package com.rewin.swhysc.bean.dto;


import lombok.Data;


/**
 * 修改添加，前台传入参数
 */
@Data
public class UpdataBondInvestmentDto {
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
    //离职日期(字符型）
    private String dimissionTimes;


}
