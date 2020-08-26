package com.rewin.swhysc.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------标的保证金
 */
@Data
public class BondBdDto {
    private int id;
    @NotEmpty(message = "证券代码不能为空")
    private String stockCode;//证券代码
    @NotEmpty(message = "证券名称不能为空")
    private String stockName;//证券名称
    @NotEmpty(message = "融资比例不能为空")
    private String rzRatio;//融资比例
    @NotEmpty(message = "融券比例不能为空")
    private String rqRatio;//融券比例
    @NotEmpty(message = "是否融资不能为空")
    private String isRz;//是否融资
    @NotEmpty(message = "是否融券不能为空")
    private String isRq;//是否融券
    @NotEmpty(message = "交易所不能为空")
    private String bourse;//交易所
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String state;//状态（0：草稿 1：待审核 2：通过 3：驳回 4：删除）

}
