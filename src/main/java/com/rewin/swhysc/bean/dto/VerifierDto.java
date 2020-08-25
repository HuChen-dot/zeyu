package com.rewin.swhysc.bean.dto;

import lombok.Data;


@Data
public class VerifierDto {
    //主键
    private Integer id;

    //审核状态
    private String status;

    //审核意见
    private String opinion;


}
