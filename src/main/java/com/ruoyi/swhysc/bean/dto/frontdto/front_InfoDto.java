package com.ruoyi.swhysc.bean.dto.frontdto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 前端返回给后端的封装数据
 * 新增特定频道下的信息是，传递过来的数据
 */
@Getter
@Setter
public class front_InfoDto implements Serializable {
    //信息的标题
    private String title;
    //信息的简介
    private String introduce;
    //关键字。当有多个值时通过 ”;” 隔开。
    private String keywords;
    //相关信息ID，多值通过英文都会逗号分隔（记录最近的10条）
    //这个字段的值是在提交信息时，系统根据关键字去找到的其他信息的id
    private String relateinfo;
    //提交者的用户ID
    private Integer posterid;
    //信息的来源
    private String source;
    //信息级别。
    private Integer infolevel;
    //重要程度。
    private Integer importance;
    //紧急程度。
    private Integer urgency;
    //自定义用途
    private Integer infovalue;
    //信息的当前状态:;0 － 发布;1 － 待审;-1 － 打回
    private Integer status;
    //相关股票，行业
    private String category;
    //标志-最新信息，0:无此标志；1:有此标志
    private Integer flagNew;
    //标志-置顶信息，0:无此标志；1:有此标志
    private Integer flagTop;
    //标志-最热信息，0:无此标志；1:有此标志
    private Integer flagHot;
    //源表主键，自动转入信息时写入
    private Integer sourceid;
    //频道表id
    private Integer channelid;

    //正文表字段
    //信息的正文
    private String content;

}
