package com.ruoyi.swhysc.mapper.menu;


import com.ruoyi.swhysc.bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 菜单路由链接数据库层
 */
@Mapper
public interface SMenuMapper {

    /**
     * 根据id查询；返回单个对象
     */
    Menu getMenuById(@Param(value = "id") Long id) throws Exception;

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
    Integer insertMenu(Menu menu) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateMenu(Menu menu) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteMenuByMap(Map<String, Object> param) throws Exception;

}
