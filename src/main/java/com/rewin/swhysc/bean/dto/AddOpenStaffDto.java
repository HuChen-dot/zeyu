package com.rewin.swhysc.bean.dto;

import lombok.Data;

/**
 * 添加或修改非现场开户人员
 */
@Data
public class AddOpenStaffDto {
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
    private Integer personnelType;

}
