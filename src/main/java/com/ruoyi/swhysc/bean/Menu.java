package com.ruoyi.swhysc.bean;


import java.io.Serializable;
import java.util.Date;

/**
 * 菜单路由实体层
 */
public class Menu implements Serializable {
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
    //创建时间
    private Date createtime;
    //创建人
    private String lastaccessname;
    //修改时间
    private Date originaltime;
    //修改人
    private String modifyname;
    //审核人
    private String auditname;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getLastaccessname() {
        return lastaccessname;
    }

    public void setLastaccessname(String lastaccessname) {
        this.lastaccessname = lastaccessname;
    }

    public Date getOriginaltime() {
        return originaltime;
    }

    public void setOriginaltime(Date originaltime) {
        this.originaltime = originaltime;
    }

    public String getModifyname() {
        return modifyname;
    }

    public void setModifyname(String modifyname) {
        this.modifyname = modifyname;
    }

    public String getAuditname() {
        return auditname;
    }

    public void setAuditname(String auditname) {
        this.auditname = auditname;
    }
}
