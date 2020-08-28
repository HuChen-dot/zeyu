package com.rewin.swhysc.bean;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 非现场开户人员实体类
 */
@Data
public class NotOpenStaff implements Serializable {
    //主键id
    private Integer id;
    //部门id
    @Excel(name = "部门id(数字)", type = Excel.Type.ALL)
    private Integer deptId;
    //员工姓名
    @Excel(name = "员工姓名(字符)", type = Excel.Type.ALL)
    private String staffName;
    //员工编号
    @Excel(name = "员工编号(数字)", type = Excel.Type.ALL)
    private Integer staffNo;
    //证书编号
    @Excel(name = "证书编号(字符)", type = Excel.Type.ALL)
    private String certificateNo;
    //信息类型
    private Integer staffType;
    //人员类型
    @Excel(name = "非现场开户人员类别(数字编号)", type = Excel.Type.ALL)
    private Integer personnelType;
    //创建者
    @Excel(name = "创建人(字符)", type = Excel.Type.ALL)
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;
    //当前信息状态(1:待审核；2;已发布；4已删除，32已发布不可操作，64驳回）
    private Integer status;


}
