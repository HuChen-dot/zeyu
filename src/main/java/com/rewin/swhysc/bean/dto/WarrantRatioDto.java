package com.rewin.swhysc.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------维持担保比例
 */
@Data
public class WarrantRatioDto {
    private int id;
    @NotEmpty(message = "平仓线不能为空")
    private String closeLine;//平仓线
    @NotEmpty(message = "警戒线不能为空")
    private String cordon;//警戒线
    @NotEmpty(message = "担保物提取线不能为空")
    private String extractLine;//担保物提取线
    @NotEmpty(message = "最低线不能为空")
    private String minLine;//最低线
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String state;//状态（0：草稿 1：待审核 2：已发布 3：驳回 4：审核修改中  6：已删除 7已下架）
    private String auditId; //审核记录ID
}
