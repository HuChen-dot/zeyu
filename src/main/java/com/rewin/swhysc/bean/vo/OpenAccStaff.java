package com.rewin.swhysc.bean.vo;

import lombok.Data;

/**
 * @program: swhyscManageServer
 * @description:开户人员信息
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/31 16:10
 **/
@Data
public class OpenAccStaff {
    private String name;
    private Integer number;
    private String branch;
    private String certificate;
    private String staffType;
}
