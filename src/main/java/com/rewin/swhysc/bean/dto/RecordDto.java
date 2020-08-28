package com.rewin.swhysc.bean.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 公示信息审核，查询列表，传入的参数封装
 */
@Data
public class RecordDto {

    //信息类型id(0 折算率；1标的；2维持担保比例；3利率费率；113非现场人员）
    private Integer infoTypeid;

    //操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
    private Integer operationId;

    //流程类型（1：代办流程， 2已办流程）
    private Integer flowType;

    //审核状态（0待审核；1：通过，2：驳回，）
    private Integer status;

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
