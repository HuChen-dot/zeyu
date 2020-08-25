package com.rewin.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻公告表
 */
@Getter
@Setter
public class NewsNotice implements Serializable {
    //主键
    private Integer id;
    //新闻标题
    private String noticeTitle;
    //信息类型id
    private Integer noticeTypeId;
    //公告状态（1未提交，2已发布，4审核中，8已下架，16已删除，32驳回）
    private String status;
    //是否处理（1已处理，2未处理）
    private Integer flow;
    //审核意见
    private String opinion;
    //作者
    private String auditor;
    //审核人
    private String verifier;
    //是否置顶
    private Integer isStick;
    //创建者
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;

}
