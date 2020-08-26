package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;


/**
 * 前台传递到后台，添加和修改广告信息的实体类
 */

@Data
public class AddConvertRateDto {
    private int id;
    @NotEmpty(message = "证券代码不能为空")
    private String stockCode;
    @NotEmpty(message = "证券名称不能为空")
    private String stockName;
    @NotEmpty(message = "折算率不能为空")
    private String rate;
    @NotEmpty(message = "交易所不能为空")
    private String bourse;
    private String state;
}
