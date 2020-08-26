package com.rewin.swhysc.bean.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台向官网，
 * 广告列表
 */
@Getter
@Setter
public class ScAdvertiseVo {
    //广告id主键
    private Integer id;
    //广告标题(业务名称)
    private String title;
    //广告内容(业务描述）
    private String content;
    //显示顺序
    private Integer orderNo;
    //外链地址
    private String path;
    //图片路径
    private String[] imgPath;
    //图标路径
    private String iconPath;

}
