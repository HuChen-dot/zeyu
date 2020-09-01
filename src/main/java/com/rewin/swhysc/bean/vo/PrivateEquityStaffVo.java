package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/9/1 17:37
 **/
@Data
public class PrivateEquityStaffVo {
    List<PrivateEquityStaff> privateEquityStaffs;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}
