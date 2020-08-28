package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 非现场开户人员列表
 */
@Data
public class NotOpenStaffVo {
    //主键id
    private Integer id;
    //部门名称
    private String deptName;
    //员工姓名
    private String staffName;
    //员工编号
    private Integer staffNo;
    //证书编号
    private String certificateNo;
    //人员类型
    private String staffType;


}
