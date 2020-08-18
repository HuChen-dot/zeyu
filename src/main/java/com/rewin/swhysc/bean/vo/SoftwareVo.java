package com.rewin.swhysc.bean.vo;


import lombok.Data;


/**
 * 后台返回前端，查询的软件列表
 */
@Data
public class SoftwareVo {

    //主键
    private Integer id;
    //软件类型名称
    private String softwareTypeName;
    //软件名称
    private String softwareName;
    //更新时间
    private String updateTime;
    //创建时间
    private String createTime;
}
