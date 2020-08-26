package com.rewin.swhysc.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 融资融卷专栏------可充抵保证金名单及折算率
 */
@Getter
@Setter
public class ConvertRateVo implements Serializable {
    private int id;
    private String stockCode;
    private String stockName;
    private String rate;
    private String state;
    private String bourse;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;

}
