package com.rewin.swhysc.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------利率费率
 */
@Data
public class InterestRateDto {
    private int id;
    @NotEmpty(message = "融资利率不能为空")
    private String rzRate;//融资利率
    @NotEmpty(message = "融券利率不能为空")
    private String rqRate;//融券利率
    @NotEmpty(message = "融资逾期罚息率不能为空")
    private String rzLataCharge;//融资逾期罚息率
    @NotEmpty(message = "融券逾期罚息率不能为空")
    private String rqLataCharge;//融券逾期罚息率
    @NotEmpty(message = "融资占用额度费率不能为空")
    private String rzQuotaRate;//融资占用额度费率
    @NotEmpty(message = "融券占用额度费率不能为空")
    private String rqQuotaRate;//融券占用额度费率
    @NotEmpty(message = "调整日期不能为空")
    private Date trimDate;//调整日期
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String state;//状态（1:新增待审核(不可操作)；2:已发布(可操作)；3:驳回(可操作)；4:修改待审核(不可操作)；
                        // 5:已发布(不可操作)；6:已发布，删除待审核(不可操作)；7:已废弃(不展示不可操作)）
    private String auditId; //审核记录ID
}
