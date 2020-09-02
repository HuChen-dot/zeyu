package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: swhyscManageServer
 * @description:私募资产管理业务从业人员信息查询入参
 * @author: sinan@rewin.com.cn
 * @create: 2020/9/1 17:36
 **/
@Data
public class PrivateEquityStaffDto {
    private String searchKey;
    @NotBlank
    private Integer pageNum;
    @NotBlank
    private Integer pageSize;
}
