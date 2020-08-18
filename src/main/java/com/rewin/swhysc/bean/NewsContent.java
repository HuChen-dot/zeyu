package com.rewin.swhysc.bean;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 新闻内容表
 */
@Setter
@Getter
public class NewsContent implements Serializable {
    //主键
    private Integer id;
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

}
