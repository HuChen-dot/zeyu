package com.rewin.swhysc.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rewin.swhysc.config.CustomJsonDateDeserializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 前台传递到后台
 * 根据条件查询新闻列表
 */

public class newsDto {
    //新闻标题
    private String noticeTitle;
    //信息类型id
    @NotEmpty(message = "信息类型id,不能为空")
    private Integer noticeTypeId;
    //公告状态（1未提交，2已发布，4审核中，8已下架，16已删除，32驳回）
    private String status;

    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String beginTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endTime;
    //当前页码
    private Integer pageNum;
    //    页面容量
    private Integer pageSize;


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Integer getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setNoticeTypeId(Integer noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "newsDto{" +
                "noticeTitle='" + noticeTitle + '\'' +
                ", noticeTypeId=" + noticeTypeId +
                ", status='" + status + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
