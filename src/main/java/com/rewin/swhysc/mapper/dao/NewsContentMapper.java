package com.rewin.swhysc.mapper.dao;

import com.rewin.swhysc.bean.NewsContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 新闻内容表数据访问层
 */
public interface NewsContentMapper {
    /**
     * 根据id查询；返回单个对象
     */
    NewsContent getNewsContentById(@Param(value = "id") Long id) throws Exception;

    /**
     * 根据新闻id查询；返回单个对象
     */
    NewsContent getNewsContentBynewsId(@Param(value = "id") Integer id) throws Exception;

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
    Integer insertNewsContent(NewsContent newsContent) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateNewsContent(NewsContent newsContent) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteNewsContentByMap(Map<String, Object> param) throws Exception;

}
