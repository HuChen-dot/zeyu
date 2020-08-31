package com.rewin.swhysc.bean.vo;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;


/**
 * 非现场开户人员模板
 */
@Data
public class NOSTemplate {

    //部门名称
    @Excel(name = "部门名称", type = Excel.Type.ALL)
    private String deptName;
    //员工姓名
    @Excel(name = "员工姓名", type = Excel.Type.ALL)
    private String staffName;
    //员工编号
    @Excel(name = "员工编号", type = Excel.Type.ALL)
    private Integer staffNo;
    //证书编号
    @Excel(name = "证书编号", type = Excel.Type.ALL)
    private String certificateNo;
    //人员类型
    @Excel(name = "非现场开户人员类别", type = Excel.Type.ALL)
    private String personnelType;
    //创建者
    @Excel(name = "创建人", type = Excel.Type.ALL)
    private String creator;

}
