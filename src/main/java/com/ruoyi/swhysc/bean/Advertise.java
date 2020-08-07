package com.ruoyi.swhysc.bean;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
/**
 * 广告表
 */
@Getter
@Setter
public class Advertise implements Serializable {
    //广告id主键
    private Integer id;
    //广告标题
    private String title;
    //广告位ID
    private Integer parentId;
    //广告内容
    private String content;
    //显示顺序
    private Integer orderNo;
    //外链地址
    private String path;
    //菜单状态（0显示 1隐藏）
    private Integer status;
    //图片路径
    private String imgPath;
    //图标路径
    private String iconPath;
    //创建者
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;

}
