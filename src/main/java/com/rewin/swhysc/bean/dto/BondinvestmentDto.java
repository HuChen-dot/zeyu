package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: swhyscManageServer
 * @description:债券投资相关人员信息查询入参
 * @author: sinan@rewin.com.cn
 * @create: 2020/9/1 10:44
 **/
@Data
public class BondinvestmentDto {
    @NotBlank
    private String staffSort;
    @NotBlank
    private Integer pageSize;
    @NotBlank
    private Integer PageNum;
}
