package com.rewin.swhysc.bean.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

/**
 * 查询软件下载次数入参
 */
@Data
public class DnSwDto {
    //软件名称
    private String softwareName;

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
}
