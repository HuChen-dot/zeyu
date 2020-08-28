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
    private String state;//状态（1:新增待审核(不可操作)；2:已发布(可操作)；3:驳回(可操作)；4:修改待审核(不可操作)；
                         // 5:已发布(不可操作)；6:已发布，删除待审核(不可操作)；7:已废弃(不展示不可操作)；8:下架）
    private String auditId; //审核记录ID
    private Date trimDate;//调整日期
}
