package com.rewin.swhysc.bean.pojo;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 私募资产管理业务从业人员信息
 */
@Data
public class NOSZTemplate {
    //营业部
    @Excel(name = "营业部", type = Excel.Type.ALL)
    private String sales;
    //部门名称
    @Excel(name = "部门名称", type = Excel.Type.ALL)
    private String deptName;
    //员工姓名
    @Excel(name = "员工姓名", type = Excel.Type.ALL)
    private String staffName;
    //员工编号
    @Excel(name = "员工编号(数字编号）", type = Excel.Type.ALL)
    private Integer staffNo;
    //证书编号
    @Excel(name = "证书编号", type = Excel.Type.ALL)
    private String certificateNo;
    //证书取得日期
    @Excel(name = "证书取得日期", type = Excel.Type.ALL)
    private Date certificatetime;

    //证书类型
    @Excel(name = "证书类型", type = Excel.Type.ALL)
    private String certificatetype;


}
