package com.rewin.swhysc.bean.dto;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * 添加或修改非现场开户人员
 */
@Data
public class AddOpenStaffDto {
    //主键id
    private Integer id;
    //部门名称
    private String deptName;
    //信息类型
    private Integer staffType;
    //员工姓名
    private String staffName;
    //员工编号
    private Integer staffNo;
    //证书编号
    private String certificateNo;
    //人员类型
    private String personnelType;
    //证书取得日期(字符串）
    private String certificatetimes;
    //证书类型
    private String certificatetype;
    //营业部
    private String sales;

}
