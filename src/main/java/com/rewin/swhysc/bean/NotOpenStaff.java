package com.rewin.swhysc.bean;

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
    private Integer deptId;
    //员工姓名
    private String staffName;
    //员工编号
    private Integer staffNo;
    //证书编号
    private String certificateNo;
    //人员类型
    private Integer staffType;
    //创建者
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;
    //当前信息状态(1:待审核；2;已发布；4已删除,8驳回）
    private Integer status;


}
