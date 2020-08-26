package com.rewin.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------标的保证金
 */
@Getter
@Setter
public class BondBd implements Serializable {
    private int id;
    private String stockCode;//证券代码
    private String stockName;//证券名称
    private String rzRatio;//融资比例
    private String rqRatio;//融券比例
    private String isRz;//是否融资
    private String isRq;//是否融券
    private String bourse;//交易所
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private String state;//状态（0：草稿 1：待审核 2：通过 3：驳回 4：审核修改中  6：已删除）

}
