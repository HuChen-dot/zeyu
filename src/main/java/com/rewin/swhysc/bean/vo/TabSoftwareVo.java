package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 传递到后台管理页面，TAB列表的数据
 */
@Data
public class TabSoftwareVo {
    //主键
    private Integer id;
    //软件是否显示及所属显示分类(0 不显示，1电脑版，2其他-电脑端，3其他-手机端)
    private Integer isShow;
    //软件类型名称
    private String softwareTypeName;
    //软件名称
    private String softwareName;
    //排序字段
    private Integer sort;
    //软件是否显示及所属显示分类(0 不显示，1电脑版，2其他-电脑端，3其他-手机端)
    private String isShowName;
    //更新时间
    private String updateTime;

}
