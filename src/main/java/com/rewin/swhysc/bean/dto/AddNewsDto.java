package com.rewin.swhysc.bean.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * 前台传递到后台，修改和添加广告信息字段封装类
 */
@Data
public class AddNewsDto {
    //信息类型id
    @NotEmpty(message = "信息类型id")
    private Integer noticeTypeId;
    //主键
    private Integer id;
    //新闻标题
    @NotEmpty(message = "新闻标题")
    private String noticeTitle;
    //来源
    private String source;

    //状态
    private String status;
    //作者
    private String author;
    //新闻内容
    @NotEmpty(message = "新闻内容")
    private String newsContent;
    //正文类型
    private String type;
    //是否置顶
    private String isStick;
    //附件链接地址
    private String accessoryPath;
    //附件名称
    private String accessoryName;

    //用来判断这次添加是存草稿，还是直接新增或修改
    private Integer isAdd;


}
