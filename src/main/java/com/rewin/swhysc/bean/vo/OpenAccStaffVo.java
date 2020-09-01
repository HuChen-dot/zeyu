package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: swhyscManageServer
 * @description:开户人员信息出参
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 14:58
 **/
@Data
public class OpenAccStaffVo {
    List<OpenAccStaff> openAccStaffs;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}
