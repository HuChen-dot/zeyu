package com.ruoyi.swhysc.bean.dto.frontdto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 增加频道时前台传递到后台的参数
 */
@Getter
@Setter
public class front_ChannelDto implements Serializable {
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
    //频道需要展示的图片的URL
    private String imgurl;
    //本记录最后修改人的id，记录刚创建时此处为创建人
    private Integer userid;
}
