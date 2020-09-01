package com.rewin.swhysc.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;


/**
 * 前台传递到后台，添加和修改广告信息的实体类
 */

@Data
public class RzrqAuditDto {
    private int id;
    @NotEmpty(message = "审核意见不能为空")
    private String auditOpinion;

}
