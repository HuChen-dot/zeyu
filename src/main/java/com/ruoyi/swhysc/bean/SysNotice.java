package com.ruoyi.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SysNotice implements Serializable {
    //公告主键seq_sys_notice.nextval
    private Long noticeId;
    //公告标题
    private String noticeTitle;
    //公告类型（1通知 2公告）
    private String noticeType;
    //公告内容
    private String noticeContent;
    //公告状态（0正常 1关闭）
    private String status;
    //创建者
    private String createBy;
    //创建时间
    private Date createTime;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;
    //公告图片
    private String picture;
    //菜单频道id
    private Integer menuid;
}
