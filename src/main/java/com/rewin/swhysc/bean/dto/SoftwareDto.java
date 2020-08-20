package com.rewin.swhysc.bean.dto;


import lombok.Data;

import java.util.Date;


/**
 * 增加或修改，前台传递到后台的参数信息
 */
@Data
public class SoftwareDto {
    //主键
    private Integer id;
    //软件类型id
    private Integer softwareType;
    //软件名称
    private String softwareName;
    //软件图标路径
    private String softwareImg;
    //安装文件路径或软件安装的二维码
    private String fileUrl;
    //更新说明
    private String updateExplain;
    //当前发布状态(1为存草稿，2为正常发布3为已删除）
    private Integer status;
    //软件描述
    private String describe;
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
