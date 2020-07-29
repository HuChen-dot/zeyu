package com.ruoyi.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 频道实体类
 */
@Getter
@Setter
public class Channel implements Serializable {
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
    //本记录最后修改人的id，记录刚创建时此处为创建人
    private Integer userid;
    //本记录最后修改人的姓名，记录刚创建时此处为创建人
    private String username;
    //最后修改时间，记录刚创建时此处为创建时间
    private Date modifytime;

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", parentid=" + parentid +
                ", description='" + description + '\'' +
                ", classorder=" + classorder +
                ", imgurl='" + imgurl + '\'' +
                ", viewurl='" + viewurl + '\'' +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", modifytime=" + modifytime +
                '}';
    }
}
