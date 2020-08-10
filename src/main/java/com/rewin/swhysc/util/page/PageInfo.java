package com.rewin.swhysc.util.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageInfo<T> implements Serializable {
    //总页码
    private Integer pages;
    //当前页码
    private Integer pageNum;
    //页面容量
    private Integer pageSize;
    //总记录数
    private long total;
    //分页数据
    private List<T> data;

}
