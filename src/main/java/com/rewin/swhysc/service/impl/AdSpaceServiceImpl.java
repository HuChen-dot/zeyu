package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AdSpace;
import com.rewin.swhysc.bean.Advertise;
import com.rewin.swhysc.bean.vo.AdvertiseDetailVo;
import com.rewin.swhysc.bean.vo.AdvertiseVo;
import com.rewin.swhysc.bean.vo.ScAdvertiseVo;
import com.rewin.swhysc.config.RuoYiConfig;
import com.rewin.swhysc.mapper.dao.AdSpaceMapper;
import com.rewin.swhysc.mapper.dao.AdvertiseMapper;
import com.rewin.swhysc.service.AdSpaceService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.file.FileUploadUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 广告位表数据业务层
 */
@Service
public class AdSpaceServiceImpl implements AdSpaceService {


    @Resource
    private AdSpaceMapper adSpaceMapper;

    @Resource
    AdvertiseMapper AdvertiseMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public AdSpace getAdSpaceById(Integer id) throws Exception {
        return adSpaceMapper.getAdSpaceById(id);
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<AdSpace> getAdSpaceListByMap(Map<String, Object> param) throws Exception {
        return adSpaceMapper.getAdSpaceListByMap(param);
    }

    /**
     * 根据广告位id，分页查询广告信息列表
     */
    @Override
    public PageInfo<AdvertiseVo> getAdSpaceByPaid(Integer id, Integer pageNo, Integer pageSize) throws Exception {
        List<AdvertiseVo> AdvertiseVoList = new ArrayList<>();
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        Map<String, Object> map = new HashMap<>(1);
        map.put("parentId", id);
        map.put("status", 0);
        //获取广告列表信息
        List<Advertise> advertiseListByMap = AdvertiseMapper.getAdvertiseListByMap(map);
        //获取广告位信息
        AdSpace adSpace = adSpaceMapper.getAdSpaceById(id);
        //获取页面信息
        AdSpace page = adSpaceMapper.getAdSpaceById(adSpace.getParentId());
        for (Advertise advertise : advertiseListByMap) {
            AdvertiseVo AdvertiseVo = new AdvertiseVo();
            BeanUtils.copyProperties(advertise, AdvertiseVo);
            AdvertiseVo.setAdSpaceName(adSpace.getAdName());
            AdvertiseVo.setViewName(page.getAdName());
            AdvertiseVo.setUpdateTime(DateUtils.dateTime(advertise.getUpdateTime()));
            AdvertiseVoList.add(AdvertiseVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<AdvertiseVo> info = new PageInfo<AdvertiseVo>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(AdvertiseVoList);
        return info;
    }

    /**
     * 官网：根据广告位id联合查询广告信息列表
     */
    @Override
    public List<ScAdvertiseVo> scgetAdSpaceByPaid(Integer id) throws Exception {
        List<ScAdvertiseVo> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(1);
        map.put("parentId", id);
        map.put("status", 0);
        List<Advertise> advertiseList = AdvertiseMapper.getAdvertiseListByMap(map);
        //图片上传地址
        String profile = FileUploadUtils.getProfile();

        for (Advertise advertise : advertiseList) {
            ScAdvertiseVo ScAdvertiseVo = new ScAdvertiseVo();
            BeanUtils.copyProperties(advertise, ScAdvertiseVo);
            String imgPath = ScAdvertiseVo.getImgPath();
            String iconPath = ScAdvertiseVo.getIconPath();
            ScAdvertiseVo.setImgPath(profile + "/" + imgPath);
            if (iconPath != null && iconPath != "") {
                ScAdvertiseVo.setIconPath(profile + "/" + iconPath);
            }
            list.add(ScAdvertiseVo);
        }

        return list;
    }


    /**
     * 根据广告id查询广告详细信息
     */
    @Override
    public AdvertiseDetailVo getAdvertiseByid(Integer id) throws Exception {
        AdvertiseDetailVo AdvertiseDetailVo = new AdvertiseDetailVo();
        //查询广告信息
        Advertise advertise = AdvertiseMapper.getAdvertiseById(id);
        BeanUtils.copyProperties(advertise, AdvertiseDetailVo);
        //图片上传地址
        String profile = RuoYiConfig.getProfile();
        String imgPath = AdvertiseDetailVo.getImgPath();
        AdvertiseDetailVo.setImgPathName(imgPath);
        String iconPath = AdvertiseDetailVo.getIconPath();
        AdvertiseDetailVo.setImgPath(profile + "/" + imgPath);
        if (iconPath != null && iconPath != "") {
            AdvertiseDetailVo.setIconPath(profile + "/" + iconPath);
            AdvertiseDetailVo.setIconPathName(iconPath);
        }

        //查询广告位名称和页面名称
        AdSpace adSpace = adSpaceMapper.getAdSpaceById(advertise.getParentId());
        //获取到广告位名称
        AdvertiseDetailVo.setAdSpaceName(adSpace.getAdName());
        //获得当前广告位下，图片尺寸大小
        AdvertiseDetailVo.setImageSizes(adSpace.getImageSizes());
        AdSpace adSpace1 = adSpaceMapper.getAdSpaceById(adSpace.getParentId());
        //获取到页面名称
        AdvertiseDetailVo.setViewName(adSpace1.getAdName());
        //获取到页面id
        AdvertiseDetailVo.setViewId(adSpace1.getId());
        return AdvertiseDetailVo;
    }


    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddAdSpace(AdSpace adSpace) throws Exception {
        return adSpaceMapper.insertAdSpace(adSpace);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyAdSpace(AdSpace adSpace) throws Exception {
        return adSpaceMapper.updateAdSpace(adSpace);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteAdSpaceById(Long id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("invid", id);
        return adSpaceMapper.deleteAdSpaceByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<AdSpace> queryAdSpacePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<AdSpace> adSpaceList = adSpaceMapper.getAdSpaceListByMap(param);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<AdSpace> info = new PageInfo<AdSpace>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(adSpaceList);
        return info;
    }

}
