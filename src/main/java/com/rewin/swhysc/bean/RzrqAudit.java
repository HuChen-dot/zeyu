package com.rewin.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------审核流程
 */
@Getter
@Setter
public class RzrqAudit implements Serializable {
    private int id;
    private String infoType;//数据类型（0折算率；1标的；2维持担保比例；3利率费率；）
    private Date commitTime;//提交时间
    private String commitUser;//提交人
    private String handleType;//操作类型（0新增；1修改；3批量上传；）
    private String auditStatus;//审核状态（0待审核；1通过；2驳回；）
    private String auditUser;//审核人
    private Date auditTime;//审核时间
    private String auditIdea;//审核意见
    private String handleNum;//操作数据编号（多条数据以,分割）
    private String state;//流程状态（0待办；1已办；）
}
