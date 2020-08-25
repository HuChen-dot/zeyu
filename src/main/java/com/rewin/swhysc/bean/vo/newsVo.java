package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 后台传递到前台，
 * 新闻列表信息
 */
@Data
public class newsVo {
    //主键
    private Integer id;
    //新闻标题
    private String noticeTitle;
    //信息类型id
    private Integer noticeTypeId;
    //信息类型名称
    private String noticeTypeName;
    //公告状态（1未提交，2已发布，4审核中，8已下架，16已删除，32驳回）
    private String status;
    //审核意见
    private String opinion;
    //是否处理（1已处理，2未处理）
    private String flow;
    //创建人
    private String creator;
    //审核人
    private String verifier;
    //是否置顶
    private String isStick;
    //更新时间
    private String updateTime;
}
