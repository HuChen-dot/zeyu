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
    //信息类型
    private Integer staffType;
    //人员类型
    @Excel(name = "非现场开户人员类别", type = Excel.Type.ALL)
    private String personnelType;
    //创建者
    @Excel(name = "创建人", type = Excel.Type.ALL)
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;
    //当前信息状态(1:待审核；2;已发布；4已删除，32已发布不可操作，64驳回）
    private Integer status;
    //证书取得日期
    @Excel(name = "证书取得日期（非现场开户人员类别，请忽略此字段）", type = Excel.Type.ALL)
    private Date certificatetime;
    //证书取得日期(字符串）
    private String certificatetimes;
    //证书类型
    @Excel(name = "证书类型（非现场开户人员类别，请忽略此字段）", type = Excel.Type.ALL)
    private String certificatetype;
    //营业部
    private String sales;


}
