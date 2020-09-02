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
@Data
public class newsDto {
    //新闻标题
    private String noticeTitle;
    //信息类型id
    private Integer noticeTypeId;
    //公告状态（1未提交，2已发布，4审核中，8已下架，16已删除，32驳回）
    private String status;

    //开始时间
    private String beginTime;
    //结束时间
    private String endTime;
    //是否处理（1已处理，2未处理）
    private Integer flow;
    //当前页码
    private Integer pageNum;
    //    页面容量
    private Integer pageSize;


}
