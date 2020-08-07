package com.ruoyi.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告位表
 */
@Getter
@Setter
public class AdSpace implements Serializable {
    //id主键
    private Integer id;
    //名称
    private String adName;
    //父id
    private Integer parentId;
    //状态
    private String status;
    //创建者
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;
    //广告位图片尺寸大小
    private String imageSizes;

}
