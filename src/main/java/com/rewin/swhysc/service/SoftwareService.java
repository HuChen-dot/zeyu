package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.Software;
import com.rewin.swhysc.bean.SoftwareInfoForSc;
import com.rewin.swhysc.bean.dto.SoftwareDto;
import com.rewin.swhysc.bean.vo.SoftwareByidVo;
import com.rewin.swhysc.bean.vo.SoftwareVo;
import com.rewin.swhysc.bean.vo.TabSoftwareVo;
import com.rewin.swhysc.util.page.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface SoftwareService {

    /**
     * 根据软件id查询软件详细信息
     * 用来修改软件前的初始化工作
     */
    SoftwareByidVo getSoftwareById(Integer id) throws Exception;

    /**
     * 根据软件id查询软件详细信息
     * 用来TAB管理修改软件前的初始化工作
     */
    TabSoftwareVo getSoftwaretabById(Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<Software> getSoftwareListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getSoftwareCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 根据TAB类型id获取软件列表
     */
    PageInfo<TabSoftwareVo> getSoftwareListByTabId(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 添加：添加软件主表和软件从表；返回影响的行数
     */
    Integer AddSoftware(SoftwareDto software) throws Exception;

    /**
     * 根据id修改：修改软件主表和软件从表；返回影响的行数
     */
    Integer ModifySoftware(SoftwareDto softwareDto) throws Exception;

    /**
     * 删除：逻辑删除软件信息
     */
    Integer updateSoftwareById(Software software) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<SoftwareVo> querySoftwarePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * @Description:前台查询软件信息列表
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/25 9:21
     */
    List<SoftwareInfoForSc> getSoftwareInfoForSc(@Param(value = "type") Integer type) throws Exception;
}
