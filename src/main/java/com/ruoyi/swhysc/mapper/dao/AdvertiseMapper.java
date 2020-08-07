package com.ruoyi.swhysc.mapper.dao;

import com.ruoyi.swhysc.bean.Advertise;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 广告表数据访问层
 */
public interface AdvertiseMapper {
    /**
     * 根据id查询；返回单个对象
     */
    Advertise getAdvertiseById(@Param(value = "id") Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<Advertise> getAdvertiseListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getAdvertiseCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer insertAdvertise(Advertise advertise) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateAdvertise(Advertise advertise) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteAdvertiseByMap(Map<String, Object> param) throws Exception;

}
