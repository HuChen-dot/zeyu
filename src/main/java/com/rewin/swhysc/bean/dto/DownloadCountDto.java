package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: swhyscManageServer
 * @description:下载次数入参
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/26 14:32
 **/
@Data
public class DownloadCountDto {
    @NotBlank
    private Integer softwareID;
    @NotBlank
    private Integer softwareType;
    @NotBlank
    private String softName;
    @NotBlank
    private String ip;
}
