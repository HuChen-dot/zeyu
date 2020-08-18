package com.rewin.swhysc.bean.vo;


import lombok.Getter;
import lombok.Setter;

/**
 * 后台传递到前台，页面名称或者广告位信息的实体类
 */
@Getter
@Setter
public class viewOrAdVo {
    //id主键
    private Integer id;
    //名称
    private String adName;
    //广告位图片尺寸大小
    private String imageSizes;

}
