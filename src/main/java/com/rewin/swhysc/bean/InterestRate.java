package com.rewin.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------利率费率
 */
@Getter
@Setter
public class InterestRate implements Serializable {
    private int id;
    private String rzRate;//融资利率
    private String rqRate;//融券利率
    private String rzLataCharge;//融资逾期罚息率
    private String rqLataCharge;//融券逾期罚息率
    private String rzQuotaRate;//融资占用额度费率
    private String rqQuotaRate;//融券占用额度费率
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String state;//状态（0：草稿 1：待审核 2：已发布 3：驳回 4：审核修改中 6：已删除 7已下架）
    private String auditId; //审核记录ID
}
