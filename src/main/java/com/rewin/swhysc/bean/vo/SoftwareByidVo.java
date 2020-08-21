package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 根据软件id，修改软件列表时，查询软件信息返回前端
 */
@Data
public class SoftwareByidVo {
    //主键
    private Integer id;
    //软件类型id
    private Integer softwareType;
    //软件名称
    private String softwareName;
    //软件图标路径
    private String softwareImg;
    //安装文件路径或二维码
    private String fileUrl;
    //软件描述
    private String describe;
    //ios跳转地址
    private String skipUrl;
    //手机二维码地址
    private String qrCode;

    //更新说明
    private String updateExplain;
    //软件大小
    private String softwareSize;
    //版本号
    private String version;
    //更新时间
    private String updateTime;

    //手机冗余更新说明
    private String cellUpdateExplain;
    //手机冗余软件大小
    private String cellSoftwareSize;
    //手机冗余版本号
    private String cellVersion;
    //手机冗余更新时间
    private String cellUpdateTime;
}

