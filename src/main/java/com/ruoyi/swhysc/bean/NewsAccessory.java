package com.ruoyi.swhysc.bean;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻附件表
 */
@Getter
@Setter
public class NewsAccessory implements Serializable {
    //主键
    private Long id;
    //新闻内容id
    private Long newsId;
    //附件链接地址
    private String accessoryPath;
    //附件名称
    private String accessoryName;

}
