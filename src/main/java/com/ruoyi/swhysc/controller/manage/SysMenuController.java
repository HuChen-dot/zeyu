package com.ruoyi.swhysc.controller.manage;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.swhysc.bean.SysMenu;
import com.ruoyi.swhysc.bean.dto.SysMenuDto;
import com.ruoyi.swhysc.security.LoginUser;
import com.ruoyi.swhysc.security.service.TokenService;
import com.ruoyi.swhysc.service.ISysMenuService;
import com.ruoyi.swhysc.util.*;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("system/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId) {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenu menu) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {

        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
//        if (menuService.checkMenuExistRole(menuId)) {
//            return AjaxResult.error("菜单已分配,不允许删除");
//        }
        return toAjax(menuService.deleteMenuById(menuId));
    }

    // *********************************************************************************

    /**
     * 根据父标签id查询标签，如果没有子标签，
     * 则查询相应标签下面的新闻公告；
     */
    @GetMapping(value = "/getMenusByid/{parentId}")
    public AjaxResult getMenusByid(@PathVariable("parentId") Long parentId) {
        System.err.println("15");
        //根据Token,从缓存中获取用户信息,
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //判断
        if (StringUtils.isNull(loginUser)) {
            return AjaxResult.error(StateCode.FORBIDDEN.getCode(), "用户信息失效，请重新登录");
        }
        List<SysMenu> menus = menuService.selectMenuByparentId(parentId);
        //如果查出来的集合菜单是空的，我就认为是最底层标签，就查询出当前菜单下的新闻
        String s = null;
        if (StringUtils.isEmpty(menus)) {
            s = com.ruoyi.swhysc.util.UrlUtils.loadURL("http://localhost:8083/system/notice/list", "id=" + parentId + "&pageNo=1&pageSize=3");
            System.out.println(s);
            return AjaxResult.success("查询成功", s);
        }
        return AjaxResult.success("查询成功", menus);
    }

    /**
     * 新增菜单
     */
    @PostMapping("addMenu")
    public AjaxResult add(@RequestBody SysMenuDto menuVo) {
        System.err.println("16");
        if (!menuService.checkMenuNameBymanuName(menuVo.getMenuName())) {
            return AjaxResult.error(StateCode.NOT_MODIFIED.getCode(), "新增菜单'" + menuVo.getMenuName() + "'失败，菜单名称已存在");
        }
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(menuVo, menu);
        menu.setCreateBy(SecurityUtils.getUsername());
        int i = menuService.insertMenu(menu);
        return toAjax(i);
    }

    /**
     * 修改菜单
     */
    @PutMapping("upedit")
    public AjaxResult edit(@RequestBody SysMenuDto menuVo) {
        System.err.println("17");
        //根据Token,从缓存中获取用户信息,
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //判断
        if (StringUtils.isNull(loginUser)) {
            return AjaxResult.error(StateCode.FORBIDDEN.getCode(), "用户信息失效，请重新登录");
        }
        if (!menuService.checkMenuNameBymanuName(menuVo.getMenuName())) {
            return AjaxResult.error(StateCode.NOT_MODIFIED.getCode(), "修改菜单'" + menuVo.getMenuName() + "'失败，菜单名称已存在");
        }
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(menuVo, menu);
        menu.setUpdateBy(SecurityUtils.getUsername());
        int i = menuService.updateMenu(menu);
        return toAjax(i);
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @DeleteMapping("removeMenu/{menuId}")
    public AjaxResult removeMenu(@PathVariable("menuId") Long menuId) {
        System.err.println("18");
        //根据Token,从缓存中获取用户信息,
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //判断
        if (StringUtils.isNull(loginUser)) {
            return AjaxResult.error(StateCode.FORBIDDEN.getCode(), "用户信息失效，请重新登录");
        }
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error(StateCode.FORBIDDEN.getCode(), "存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {

            return AjaxResult.error(StateCode.PARAMETER.getCode(), "菜单已分配,不允许删除");
        }
        int i = menuService.deleteMenuById(menuId);
        return toAjax(i);
    }

}