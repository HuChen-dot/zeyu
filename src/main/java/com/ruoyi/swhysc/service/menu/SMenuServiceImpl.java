package com.ruoyi.swhysc.service.menu;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Menu;
import com.ruoyi.swhysc.bean.dto.laterdto.later_IndexDto;
import com.ruoyi.swhysc.bean.extension.IndexDtoExtension;
import com.ruoyi.swhysc.bean.vo.InitMenuVo;
import com.ruoyi.swhysc.exception.SqlException;
import com.ruoyi.swhysc.mapper.menu.SMenuMapper;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单路由逻辑实现层
 */
@Service
public class SMenuServiceImpl implements SMenuService {

    @Resource
    private SMenuMapper menuMapper;

    @Resource
    com.ruoyi.swhysc.util.redis.RedisCache RedisCache;

    /**
     * @param listInit     存储分类后的结果
     * @param IndexDtolist 父级标签集合
     * @param listIndexDto 总标签集合
     * @return
     */
    public static void classification(List<InitMenuVo> listInit, List<later_IndexDto> IndexDtolist, List<later_IndexDto> listIndexDto) {
        List<later_IndexDto> listzi = null;
        InitMenuVo initMenuVo = null;
        //遍历一级节点
        for (later_IndexDto menu1 : IndexDtolist) {
            initMenuVo = new InitMenuVo();
            IndexDtoExtension IndexDtoExtension = new IndexDtoExtension();
            BeanUtils.copyProperties(menu1, IndexDtoExtension);
            // IndexDtoExtension.setIndexDto(menu1);
            // 初始化临时子标签的集合容器
            listzi = new ArrayList();
            for (later_IndexDto dto : listIndexDto) {
                if (dto.getParent() == menu1.getId()) {
                    listzi.add(dto);
                }
            }
            // 父级标签下的所有子标签；如果为空则代表当前标签下没有子标签，直接反回
            if (listzi == null) {
                return;
            }
            // 避免一级标签没有子标签，一级标签直接存储
            if (menu1.getParent() == 1 && listzi.size() == 0) {
                IndexDtoExtension.setChildren(listzi);
                initMenuVo.setRoutes(IndexDtoExtension);
                listInit.add(initMenuVo);
            }
            // 避免标签重复存储，没有子标签的直接进行下一次循环
            if (listzi.size() == 0) {
                continue;
            }
            IndexDtoExtension.setChildren(listzi);
            initMenuVo.setRoutes(IndexDtoExtension);
            listInit.add(initMenuVo);
            // 递归循环开始
            classification(listInit, listzi, listIndexDto);
        }
    }

    /**
     * 初始化首页的菜单标签
     *
     * @return
     */
    @Override
    public List<InitMenuVo> InitMenu() throws SqlException {
        List<Menu> parentMenu = null;
        List<InitMenuVo> listInit = null;
        //首先查询redis缓存中有没有数据，
        listInit = RedisCache.getCacheList("initMenuVo");
        //如果有数据直接返回，
        if (listInit.size() > 0) {
            return listInit;
        }
        try {
            Map map = new HashMap();
            //设置参数
            map.put("status", 1);
            //查询出所有的标签
            parentMenu = menuMapper.getMenuListByMap(map);
            //初始化存储转换后对象的集合
            List<later_IndexDto> parentIndexDto = new ArrayList<>();
            //把Menu转换到IndexDto，去掉多余的字段
            for (Menu Menu : parentMenu) {
                later_IndexDto IndexDto = new later_IndexDto();
                BeanUtils.copyProperties(Menu, IndexDto);
                parentIndexDto.add(IndexDto);
            }
            //获得所有的父级标签集合
            List<later_IndexDto> list = new ArrayList();
            //循环顶级标签的列表，查询顶级标签下的所有子列表
            for (later_IndexDto indexto : parentIndexDto) {
                if (indexto.getParent() == 1) {
                    list.add(indexto);
                }
            }
            listInit = new ArrayList<>();
            //调用分类的方法
            this.classification(listInit, list, parentIndexDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlException(StateCode.SQL_FAIL.getCode());
        }
        //向redis中存储值
        if (listInit.size() > 0) {
            RedisCache.setCacheList("initMenuVo", listInit);
        }
        return listInit;
    }

    /**
     * 根据id查询；返回单个对象
     */
    @Override
    public Menu getMenuById(Long id) throws Exception {
        return menuMapper.getMenuById(id);
    }


    /**
     * 根据条件查询；返回多个对象
     */
    @Override
    public List<Menu> getMenuListByMap(Map
                                               <String, Object> param) throws Exception {
        return menuMapper.getMenuListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    @Override
    public Integer getMenuCountByMap(Map
                                             <String, Object> param) throws Exception {
        return menuMapper.getMenuCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    @Override
    public Integer AddMenu(Menu menu) throws Exception {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    @Override
    public Integer ModifyMenu(Menu menu) throws Exception {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    @Override
    public Integer DeleteMenuById(Long id) throws Exception {
        Map
                <String, Object> map = new HashMap
                <String, Object>();
        map.put("invid", id);
        return menuMapper.deleteMenuByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    @Override
    public PageInfo<Menu> queryMenuPageByMap(Map
                                                     <String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<Menu> menuList = menuMapper.getMenuListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Menu> info = new PageInfo<Menu>(menuList);
        return info;
    }
}
