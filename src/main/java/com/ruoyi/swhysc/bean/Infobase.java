package com.ruoyi.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 频道下的信息表实体类
 */
@Getter
@Setter
public class Infobase implements Serializable {
    //表的主键ID
    private Integer id;
    //信息的标题
    private String title;
    //信息的简介
    private String introduce;
    //记录当前信息所带附件的个数
    private Integer attachmentcount;
    //关键字。当有多个值时通过 ”;” 隔开。
    private String keywords;
    //相关信息ID，多值通过英文都会逗号分隔（记录最近的10条）
    //这个字段的值是在提交信息时，系统根据关键字去找到的其他信息的id
    private String relateinfo;
    //入库时间
    private Date createtime;
    //最后访问时间
    private Date lastaccesstime;
    //信息的创建时间
    private Date originaltime;
    //最后修改时间
    private Date modifytime;
    //过期时间
    private Date expiretime;
    //信息提交者的呢称
    private String poster;
    //信息的作者
    private String author;
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
    //信息的访问次数
    private Integer accesstimes;
    //打分次数
    private Integer ratetimes;
    //信息所得总分
    private Integer sumrate;
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


}
