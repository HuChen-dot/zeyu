package com.rewin.swhysc.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 后端返回前端的广告详细信息实体类
 * 用来修改广告前的初始化工作
 */
@Getter
@Setter
public class AdvertiseDetailVo implements Serializable {
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
    private String[] imgPath;
    //图片名称
    private String[] imgPathName;
    //图标路径
    private String iconPath;
    //图标名称
    private String iconPathName;
    //页面名称
    private String viewName;
    //页面id
    private Integer viewId;
    //广告位名称
    private String AdSpaceName;

}
