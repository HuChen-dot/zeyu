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
    private String state;//状态（1:新增待审核(不可操作)；2:已发布(可操作)；3:驳回(可操作)；4:修改待审核(不可操作)；
    // 5:已发布(不可操作)；6:已发布，删除待审核(不可操作)；7:已废弃(不展示不可操作)）
    @NotEmpty(message = "调整日期不能为空")
    private Date trimDate;//调整日期
}
