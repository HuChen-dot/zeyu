package com.rewin.swhysc.bean.dto;

import lombok.Data;

/**
 * 添加TAB管理软件时传递数
 */
@Data
public class TabDto {
    //主键
    private Integer id;
    //软件是否显示及所属显示分类(0 不显示，1显示，2其他)
    private Integer channel;
    //排序字段
    private Integer sort;
}
