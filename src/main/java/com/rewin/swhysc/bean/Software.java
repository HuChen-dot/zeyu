package com.rewin.swhysc.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Software implements Serializable {
    //主键
    private Integer id;
    //软件类型id
    private Integer softwareType;
    //软件名称
    private String softwareName;
    //软件图标路径
    private String softwareImg;
    //当前发布状态(1为存草稿，2为正常发布3为已删除）
    private Integer status;
    //安装文件路径或软件下载二维码
    private String fileUrl;
    //软件描述
    private String describe;
    //排序字段
    private Integer sort;
    //ios跳转地址
    private String skipUrl;
    //手机二维码地址
    private String qrCode;
    //软件是否显示及所属显示分类(0 不显示，1电脑端，2其他-电脑端 4其他-手机端）
    private Integer channel;
    //创建者
    private String creator;
    //创建时间
    private String createTime;
    //更新者
    private String updater;
    //更新时间
    private String updateTime;

}
