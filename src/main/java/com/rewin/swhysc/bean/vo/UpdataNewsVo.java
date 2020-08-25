package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

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
    //创建人
    private String creator;
    //创建时间
    private String createTime;
    //审核人
    private String verifier;
    //是否处理（1已处理，2未处理）
    private Integer flow;
    //是否置顶
    private Integer isStick;
    //新闻内容
    private String newsContent;
    //审核状态
    private String status;
    //正文类型
    private String type;
    //审核意见
    private String opinion;
    //来源
    private String source;

    //附件链接地址
    private String[] accessoryPath;
    //附件名称
    private String[] accessoryName;


}
