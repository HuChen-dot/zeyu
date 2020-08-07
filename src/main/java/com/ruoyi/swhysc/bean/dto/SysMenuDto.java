package com.ruoyi.swhysc.bean.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 添加菜单，前台传递到后台的字段属性
 */
@Data
public class SysMenuDto implements Serializable {


    /**
     * 菜单名称
     */
    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String status;

    /**
     * 菜单图标
     */
    private String icon;

}
