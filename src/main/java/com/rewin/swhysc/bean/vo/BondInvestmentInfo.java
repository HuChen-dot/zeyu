package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/9/1 10:57
 **/
@Data
public class BondInvestmentInfo {
    private String postType;
    private String name;
    private String deptName;
    private String duty;
    private String workPhone;
    private Date dimissionTime;
}
