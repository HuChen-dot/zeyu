package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: swhyscManageServer
 * @description:营销人员查询入参
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 16:55
 **/
@Data
public class MarketerDto {
    @NotBlank
    private String companyType;
    @NotBlank
    private String staffType;
    private String searchKey;
    private Integer pageNum;
    private Integer pageSize;
}
