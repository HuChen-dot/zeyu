package com.rewin.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 新闻附件表
 */
@Getter
@Setter
public class NewsAccessory implements Serializable {
    //主键
    private Integer id;
    //新闻id
    private Integer newsId;
    //附件链接地址
    private String accessoryPath;
    //附件名称
    private String accessoryName;

}
