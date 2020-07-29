package com.ruoyi.swhysc.mapper.infobase;

import com.ruoyi.swhysc.bean.Infobase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 频道下信息表数据库访问层
 */
public interface InfobaseMapper {
    /**
     * 根据id查询；返回单个对象
     */
    Infobase getInfobaseById(@Param(value = "id") Long id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<Infobase> getInfobaseListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getInfobaseCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer insertInfobase(Infobase infobase) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateInfobase(Infobase infobase) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteInfobaseByMap(Map<String, Object> param) throws Exception;

}
