package com.rewin.swhysc.bean.vo;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 非现场开户人员和私募资产管理列表
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
    //信息类型
    private String staffType;
    //人员类型
    private String personnelType;
    //证书取得日期(字符串）
    private String certificatetimes;
    //证书类型
    private String certificatetype;


}
