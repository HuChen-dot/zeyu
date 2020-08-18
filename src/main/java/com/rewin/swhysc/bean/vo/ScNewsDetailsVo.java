package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 官网：后台返回前端，新闻详细信息
 */
@Data
public class ScNewsDetailsVo {
    //新闻id
    private Integer newsId;
    //新闻内容
    private String newsContent;
    //正文类型
    private String type;
    //来源
    private String source;
    //新闻标题
    private String noticeTitle;
    //作者
    private String author;
    //附件链接地址
    private String accessoryPath;
    //附件名称
    private String accessoryName;
    //更新时间
    private Date updateTime;
}
