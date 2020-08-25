package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 开户人员修改前信息回添
 */
@Data
public class UpdaNotOpenStaffVo {
    //主键id
    private Integer id;
    //部门id
    private Integer deptId;
    //员工姓名
    private String staffName;
    //员工编号
    private Integer staffNo;
    //证书编号
    private String certificateNo;
    //人员类型
    private Integer staffType;

}
