package com.rewin.swhysc.bean.vo;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
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
    private String deptName;
    //员工姓名
    private String staffName;
    //员工编号
    private Integer staffNo;
    //证书编号
    private String certificateNo;
    //信息类型
    private Integer staffType;
    //人员类型
    private String personnelType;
    //证书取得日期(字符串）
    private String certificatetimes;
    //证书类型
    private String certificatetype;

}
