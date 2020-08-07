package com.ruoyi.swhysc.bean;
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
    private Long id;
    //新闻标题
    private String noticeTitle;
    //信息类型id
    private Long noticeTypeId;
    //公告状态（1未提交，2已发布，4审核中，8已下架，16已删除，32驳回）
    private String status;
    //审核意见
    private String opinion;
    //审核人
    private String auditor;
    //是否置顶
    private String isStick;
    //创建者
    private String creator;
    //创建时间
    private Date createTime;
    //更新者
    private String updater;
    //更新时间
    private Date updateTime;

}
