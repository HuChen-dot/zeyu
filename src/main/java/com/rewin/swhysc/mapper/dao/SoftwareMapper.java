package com.rewin.swhysc.mapper.dao;

import com.rewin.swhysc.bean.Software;
import com.rewin.swhysc.bean.SoftwareInfoForSc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SoftwareMapper {
    /**
     * 根据id查询；返回单个对象
     */
    Software getSoftwareById(@Param(value = "id") Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<Software> getSoftwareListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getSoftwareCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer insertSoftware(Software software) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateSoftware(Software software) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteSoftwareByMap(Map<String, Object> param) throws Exception;

    /**
     * @Description:前台查询软件信息列表
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/25 9:21
     */
    List<SoftwareInfoForSc> getSoftwareInfoForSc(@Param(value = "type") Integer type) throws Exception;

}
