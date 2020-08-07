package com.ruoyi.swhysc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Advertise;
import com.ruoyi.swhysc.mapper.dao.AdvertiseMapper;
import com.ruoyi.swhysc.service.AdvertiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 广告表数据业务层
 */
@Service
public class AdvertiseServiceImpl implements AdvertiseService {


    @Resource
    private AdvertiseMapper advertiseMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public Advertise getAdvertiseById(Integer id) throws Exception {
        return advertiseMapper.getAdvertiseById(id);
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Advertise> getAdvertiseListByMap(Map
                                                         <String, Object> param) throws Exception {
        return advertiseMapper.getAdvertiseListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getAdvertiseCountByMap(Map
                                                  <String, Object> param) throws Exception {
        return advertiseMapper.getAdvertiseCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddAdvertise(Advertise advertise) throws Exception {
        return advertiseMapper.insertAdvertise(advertise);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyAdvertise(Advertise advertise) throws Exception {
        return advertiseMapper.updateAdvertise(advertise);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteAdvertiseById(Integer id) throws Exception {
        Map
                <String, Object> map = new HashMap
                <String, Object>();
        map.put("invid", id);
        return advertiseMapper.deleteAdvertiseByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<Advertise> queryAdvertisePageByMap(Map
                                                               <String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<Advertise> advertiseList = advertiseMapper.getAdvertiseListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Advertise> info = new PageInfo<Advertise>(advertiseList);
        return info;
    }

}
