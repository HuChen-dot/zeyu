package com.rewin.swhysc.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 融资融卷专栏------利率费率
 */
@Getter
@Setter
public class WarrantRatioVo implements Serializable {
    private int id;
    private String closeLine;//平仓线
    private String cordon;//警戒线
    private String extractLine;//担保物提取线
    private String minLine;//最低线
    private String createUser;
    private String createDate;
    private String updateUser;
    private String updateDate;
    private String auditId; //审核记录ID
    private String trimDate;//调整日期
    private String state;//状态（1:新增待审核(不可操作)；2:已发布(可操作)；3:驳回(可操作)；4:修改待审核(不可操作)；
                        // 5:已发布(不可操作)；6:已发布，删除待审核(不可操作)；7:已废弃(不展示不可操作)）
    private String auditor;
    private String auditOpinion;
}
