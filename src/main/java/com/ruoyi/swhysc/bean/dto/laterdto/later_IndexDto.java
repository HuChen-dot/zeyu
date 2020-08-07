package com.ruoyi.swhysc.bean.dto.laterdto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 菜单路由实体层
 */
@Getter
@Setter
public class later_IndexDto implements Serializable {
    //主键
    private Integer id;
    //菜单称
    private String title;
    //菜单的路由
    private String path;
    //父级标签(顶级标签的父级是1）
    private Integer parent;
    //类别的状态：生效1；待审核2；审核通过4；审核不通过8；
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
