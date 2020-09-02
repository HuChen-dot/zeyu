package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: swhyscManageServer
 * @description:非现场开户人员信息查询入参
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 16:55
 **/
@Data
public class OpenAccStaffDto {
    private String searchKey;
    @NotBlank
    private Integer pageNum;
    @NotBlank
    private Integer pageSize;
}
