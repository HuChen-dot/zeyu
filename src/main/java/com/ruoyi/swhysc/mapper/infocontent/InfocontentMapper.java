package com.ruoyi.swhysc.mapper.infocontent;

import com.ruoyi.swhysc.bean.Infocontent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 信息表正文数据库访问层
 */
public interface InfocontentMapper {
    /**
     * 根据id查询；返回单个对象
     */
    Infocontent getInfocontentById(@Param(value = "id") Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<Infocontent> getInfocontentListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getInfocontentCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer insertInfocontent(Infocontent infocontent) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateInfocontent(Infocontent infocontent) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteInfocontentByMap(Map<String, Object> param) throws Exception;

}
