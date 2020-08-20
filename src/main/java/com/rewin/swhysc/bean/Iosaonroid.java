package com.rewin.swhysc.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Iosaonroid implements Serializable {
    //主键
    private Integer id;
    //软件id
    private Integer softwareId;
    //当前软件类型（ios 1; Aonroid 2；4 PC)
    private Integer platformType;
    //更新说明
    private String updateExplain;

    //软件大小
    private String softwareSize;
    //版本号
    private String version;
    //更新时间
    private Date updateTime;


}
