package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/9/1 17:37
 **/
@Data
public class PrivateEquityStaff {
    private String name;
    private String deptName;
    private String certificateNo;
    private Date certificateTime;
    private String certificateType;
}
