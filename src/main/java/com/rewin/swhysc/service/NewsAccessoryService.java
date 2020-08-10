package com.rewin.swhysc.service;

import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.NewsAccessory;

import java.util.List;
import java.util.Map;

/**
 * 新闻附件表数据业务层
 */
public interface NewsAccessoryService {

    /**
     * 根据id查询；返回单个对象
     */
    NewsAccessory getNewsAccessoryById(Long id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<NewsAccessory> getNewsAccessoryListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getNewsAccessoryCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddNewsAccessory(NewsAccessory newsAccessory) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyNewsAccessory(NewsAccessory newsAccessory) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteNewsAccessoryById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<NewsAccessory> queryNewsAccessoryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
