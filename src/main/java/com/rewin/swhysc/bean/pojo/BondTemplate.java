package com.rewin.swhysc.bean.pojo;


import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;


/**
 * 债券投资相关人员下载模板
 */
@Data
public class BondTemplate {
    //人员分类
    @Excel(name = "人员分类", type = Excel.Type.ALL)
    private String staffSort;
    //部门名称
    @Excel(name = "部门名称", type = Excel.Type.ALL)
    private String deptName;
    //员工姓名
    @Excel(name = "员工姓名", type = Excel.Type.ALL)
    private String staffName;
    //职务
    @Excel(name = "职务", type = Excel.Type.ALL)
    private String duty;
    //岗位分类
    @Excel(name = "岗位分类", type = Excel.Type.ALL)
    private String postType;
    //办公电话
    @Excel(name = "办公电话", type = Excel.Type.ALL)
    private String workPhone;


}
