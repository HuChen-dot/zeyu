package com.rewin.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------维持担保比例
 */
@Getter
@Setter
public class WarrantRatio implements Serializable {
    private int id;
    private String closeLine;//平仓线
    private String cordon;//警戒线
    private String extractLine;//担保物提取线
    private String minLine;//最低线
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String state;//状态（0：草稿 1：待审核 2：已发布 3：驳回 4：审核修改中  6：已删除 7已下架）
    private String auditId; //审核记录ID
    private Date trimDate;//调整日期
}
