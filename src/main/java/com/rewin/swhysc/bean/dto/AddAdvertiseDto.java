package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


/**
 * 前台传递到后台，添加和修改广告信息的实体类
 */

@Data
public class AddAdvertiseDto {

    //广告ID
    private Integer id;
    //广告位ID
    @NotEmpty(message = "广告位不能为空")
    private Integer parentId;
    //广告标题
    @NotEmpty(message = "广告标题不能为空")
    private String title;
    //广告内容
    private String content;
    //外链地址
    @NotEmpty(message = "外链地址不能为空")
    private String path;
    //菜单状态（0显示 1隐藏）
    @NotEmpty(message = "菜单状态不能为空")
    private Integer status;
    //图片路径
    private String imgPath;

    //图标路径
    private String iconPath;
    //显示顺序
    @NotEmpty(message = "显示顺序不能为空")
    private Integer orderNo;

}
