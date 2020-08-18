package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.AdSpace;
import com.rewin.swhysc.bean.vo.AdvertiseDetailVo;
import com.rewin.swhysc.bean.vo.AdvertiseVo;
import com.rewin.swhysc.bean.vo.ScAdvertiseVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 广告位表数据业务层
 */
public interface AdSpaceService {

    /**
     * 根据id查询；返回单个对象
     */
    AdSpace getAdSpaceById(Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<AdSpace> getAdSpaceListByMap(Map<String, Object> param) throws Exception;

    /**
     * 根据广告位id,分页查询广告信息列表
     */
    PageInfo<AdvertiseVo> getAdSpaceByPaid(Integer id, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 官网：根据广告位id联合查询广告信息列表
     */
    List<ScAdvertiseVo> scgetAdSpaceByPaid(Integer id) throws Exception;

    /**
     * 根据广告id查询广告详细信息
     */
    AdvertiseDetailVo getAdvertiseByid(Integer id) throws Exception;


    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddAdSpace(AdSpace adSpace) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyAdSpace(AdSpace adSpace) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteAdSpaceById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<AdSpace> queryAdSpacePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
