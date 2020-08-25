package com.rewin.swhysc.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件下载实体
 */
@Data
public class DownloadSw implements Serializable {
    //主键id
    private Integer id;
    //软件id
    private Integer softwareId;
    //软件类型
    private Integer softwareType;
    //软件名称
    private String softwareName;
    //下载次数
    private Integer downloadCount;
    //下载时间
    private Date downloadTime;
    //下载机器ip
    private String ip;


}
