package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 官网：后台返回前端的新闻公告列表
 */
@Data
public class ScNewsVo {
    //新闻id
    private Integer id;
    //新闻标题
    private String noticeTitle;
    //是否置顶
    private String isStick;
    //更新时间
    private String updateTime;
}
