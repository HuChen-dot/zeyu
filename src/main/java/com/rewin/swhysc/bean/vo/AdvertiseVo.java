package com.rewin.swhysc.bean.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 后台传递到前端，封装广告信息的实体类
 */
@Getter
@Setter
public class AdvertiseVo {
    //广告id主键
    private Integer id;
    //页面名称
    private String viewName;
    //广告位名称
    private String AdSpaceName;
    //广告标题
    private String title;
    //广告位ID
    private Integer parentId;
    //显示顺序
    private Integer orderNo;
    //更新时间
    private Date updateTime;


}
