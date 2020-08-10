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
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
