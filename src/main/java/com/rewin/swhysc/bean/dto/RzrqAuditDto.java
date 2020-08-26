package com.rewin.swhysc.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------审核流程
 */
@Data
public class RzrqAuditDto{
    private int id;
    @NotEmpty(message = "数据类型不能为空")
    private String infoType;//数据类型（0折算率；1标的；2维持担保比例；3利率费率；）
    @NotEmpty(message = "操作类型不能为空")
    private String handleType;//操作类型（0新增；1修改；2删除；3批量上传；4批量删除；5全部删除）
    @NotEmpty(message = "审核状态不能为空")
    private String auditStatus;//审核状态（0待审核；1通过；2驳回；）
    @NotEmpty(message = "审核意见不能为空")
    private String audotIdea;//审核意见
    @NotEmpty(message = "操作数据编号不能为空")
    private String handleNum;//操作数据编号（多条数据以,分割）
    private String state;//流程状态（0待办；1已办；）
}
