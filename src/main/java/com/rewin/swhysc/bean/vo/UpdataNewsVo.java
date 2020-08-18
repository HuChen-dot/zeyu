package com.rewin.swhysc.bean.vo;

import lombok.Data;

/**
 * 用户修改新闻内容前，通过新闻id，获取详细新闻信息返回前台
 */
@Data
public class UpdataNewsVo {
    //主键
    private Integer id;
    //新闻标题
    private String noticeTitle;
    //信息类型id
    private Integer noticeTypeId;
    //信息类型名称ame
    private String noticeTypeName;
    //作者
    private String author;
    //新闻内容
    private String newsContent;
    //正文类型
    private String type;
    //来源
    private String source;
    //状态
    private String status;
    //附件链接地址
    private String[] accessoryPath;
    //附件名称
    private String[] accessoryName;


}
