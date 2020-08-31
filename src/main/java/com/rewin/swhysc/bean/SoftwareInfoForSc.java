package com.rewin.swhysc.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SoftwareInfoForSc implements Serializable {

    private static final long serialVersionUID = 248140556003700671L;

    private Integer id;

    private Integer softwareType;

    private String softwareName;

    private String softwareImg;

    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private String describe;

    private String fileUrl;

    private Integer sort;

    private String skipUrl;

    private String qrCode;

    private Integer isShow;

    private Integer platformType;

    private String softwareSize;

    private String updateExplain;

    private String softwareUpdateTime;

}