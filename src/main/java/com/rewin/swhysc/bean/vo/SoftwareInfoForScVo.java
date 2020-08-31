package com.rewin.swhysc.bean.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: swhyscManageServer
 * @description:返回给前台的广告信息
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/25 14:21
 **/
@Data
public class SoftwareInfoForScVo {
    private Integer id;
    private String name;
    private String introduce;
    private String logo;
    private Integer platform;
    private String QRCode;
    private Integer sort;
    private AndroidInfo Android;
    private IOSInfo IOS;
    private PCInfo PC;
}
