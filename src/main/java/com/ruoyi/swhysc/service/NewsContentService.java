package com.ruoyi.swhysc.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.NewsContent;

import java.util.List;
import java.util.Map;

/**
 * 新闻内容表业务层
 */
public interface NewsContentService {

    /**
     * 根据id查询；返回单个对象
     */
    NewsContent getNewsContentById(Long id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<NewsContent> getNewsContentListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getNewsContentCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddNewsContent(NewsContent newsContent) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyNewsContent(NewsContent newsContent) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteNewsContentById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<NewsContent> queryNewsContentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
