package com.ruoyi.swhysc.bean.dto.laterdto;

import lombok.Getter;
import lombok.Setter;


/**
 * 后端频道路由转换实体层
 */
@Getter
@Setter
public class later_ChannelDto {
    //表的主键ID
    private Integer id;
    //类别的名字。
    private String name;
    //频道的类型：；1 － 视图；2 － URL
    private Integer type;
    //此处为URL。；如果type为0,这个属性有效
    private String url;
    //频道的状态：；0 － 正常；1 － 停用
    private Integer status;
    //父频道ID
    private Integer parentid;
    //频道的描述。
    private String description;
    //对频道的显示，进行排序
    private Integer classorder;
    //频道需要展示的图片的URL
    private String imgurl;
    //预览本频道下的信息列表的URL
    private String viewurl;
}
