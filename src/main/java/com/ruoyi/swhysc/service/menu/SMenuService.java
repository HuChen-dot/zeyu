package com.ruoyi.swhysc.service.menu;

import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Menu;
import com.ruoyi.swhysc.bean.vo.InitMenuVo;
import com.ruoyi.swhysc.exception.SqlException;

import java.util.List;
import java.util.Map;

/**
 * 菜单路由逻辑接口层
 */
public interface SMenuService {

    /**
     * 初始化首页的菜单标签
     *
     * @return
     */
    List<InitMenuVo> InitMenu() throws SqlException;

    /**
     * 根据id查询；返回单个对象
     */
    Menu getMenuById(Long id) throws Exception;


    /**
     * 根据条件查询；返回多个对象
     */
    List<Menu> getMenuListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getMenuCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddMenu(Menu menu) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyMenu(Menu menu) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteMenuById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<Menu> queryMenuPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}