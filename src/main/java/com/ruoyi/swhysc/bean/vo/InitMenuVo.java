package com.ruoyi.swhysc.bean.vo;

import com.ruoyi.swhysc.bean.extension.IndexDtoExtension;

import java.io.Serializable;


public class InitMenuVo implements Serializable {
    private IndexDtoExtension routes;

    public IndexDtoExtension getRoutes() {
        return routes;
    }

    public void setRoutes(IndexDtoExtension routes) {
        this.routes = routes;
    }

}
