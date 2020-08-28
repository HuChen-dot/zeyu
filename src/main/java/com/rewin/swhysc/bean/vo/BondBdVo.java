package com.rewin.swhysc.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------标的保证金
 */
@Getter
@Setter
public class BondBdVo implements Serializable {
    private int id;
    private String stockCode;//证券代码
    private String stockName;//证券名称
    private String rzRatio;//融资比例
    private String rqRatio;//融券比例
    private String isRz;//是否融资
    private String isRq;//是否融券
    private String bourse;//交易所
    private String createUser;
    private String createDate;
    private String updateUser;
    private String updateDate;
    private String state;//状态（1:新增待审核(不可操作)；2:已发布(可操作)；3:驳回(可操作)；4:修改待审核(不可操作)；
    // 5:已发布(不可操作)；6:已发布，删除待审核(不可操作)；7:已废弃(不展示不可操作)）
    private String trimDate;//调整日期
}
