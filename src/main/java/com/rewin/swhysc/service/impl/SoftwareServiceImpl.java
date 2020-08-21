package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.Iosaonroid;
import com.rewin.swhysc.bean.Software;
import com.rewin.swhysc.bean.SysDictType;
import com.rewin.swhysc.bean.dto.SoftwareDto;
import com.rewin.swhysc.bean.vo.SoftwareByidVo;
import com.rewin.swhysc.bean.vo.SoftwareVo;
import com.rewin.swhysc.mapper.dao.IosaonroidMapper;
import com.rewin.swhysc.mapper.dao.SoftwareMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SoftwareServiceImpl implements SoftwareService {


    @Resource
    private SoftwareMapper softwareMapper;

    @Resource
    com.rewin.swhysc.mapper.dao.SysDictTypeMapper SysDictTypeMapper;

    @Resource
    private IosaonroidMapper iosaonroidMapper;
    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;

    /**
     * 根据软件id查询软件详细信息
     * 用来修改软件前的初始化工作
     */
    public SoftwareByidVo getSoftwareById(Integer id) throws Exception {
        //查询软件主表
        Software software = softwareMapper.getSoftwareById(id);
        //查询软件关联表
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put("softwareId", software.getId());
        List<Iosaonroid> iosaonroidList = iosaonroidMapper.getIosaonroidListByMap(map);
        //声明变量
        SoftwareByidVo SoftwareByidVo = new SoftwareByidVo();
        BeanUtils.copyProperties(software, SoftwareByidVo);
        //遍历取值
        for (Iosaonroid iosaonroid : iosaonroidList) {
            if (iosaonroid.getPlatformType() == 1 || iosaonroid.getPlatformType() == 4) {
                SoftwareByidVo.setSoftwareSize(iosaonroid.getSoftwareSize());
                SoftwareByidVo.setUpdateExplain(iosaonroid.getUpdateExplain());
                SoftwareByidVo.setUpdateTime(DateUtils.dateTime(iosaonroid.getUpdateTime()));
                SoftwareByidVo.setVersion(iosaonroid.getVersion());
            }
            if (iosaonroid.getPlatformType() == 2) {
                SoftwareByidVo.setCellSoftwareSize(iosaonroid.getSoftwareSize());
                SoftwareByidVo.setCellUpdateExplain(iosaonroid.getUpdateExplain());
                SoftwareByidVo.setCellUpdateTime(DateUtils.dateTime(iosaonroid.getUpdateTime()));
                SoftwareByidVo.setCellVersion(iosaonroid.getVersion());
            }
        }
        return SoftwareByidVo;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Software> getSoftwareListByMap(Map
                                                       <String, Object> param) throws Exception {
        return softwareMapper.getSoftwareListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getSoftwareCountByMap(Map
                                                 <String, Object> param) throws Exception {
        return softwareMapper.getSoftwareCountByMap(param);
    }

    /**
     * 添加：添加软件主表和软件从表；返回影响的行数
     */
    @Transactional
    public Integer AddSoftware(SoftwareDto softwareDto) throws Exception {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        //添加主表
        Software software = new Software();
        BeanUtils.copyProperties(softwareDto, software);
        software.setSkipUrl(softwareDto.getSkipUrl() == null ? " " : softwareDto.getSkipUrl());
        software.setCreator(loginUser.getUsername());
        software.setUpdater(loginUser.getUsername());
        software.setCreateTime(new Date());
        software.setUpdateTime(new Date());
        software.setSort(1);
        software.setIsShow(0);
        softwareMapper.insertSoftware(software);


        //添加从表
        Integer inout = null;
        if (softwareDto.getSoftwareType() == 111) {
            Iosaonroid iosaonroid = new Iosaonroid();
            iosaonroid.setSoftwareId(software.getId());
            iosaonroid.setPlatformType(1);
            iosaonroid.setSoftwareSize(softwareDto.getSoftwareSize());
            iosaonroid.setUpdateExplain(softwareDto.getUpdateExplain());

            iosaonroid.setUpdateTime(softwareDto.getUpdateTime());
            iosaonroid.setVersion(softwareDto.getVersion());
            iosaonroidMapper.insertIosaonroid(iosaonroid);
        } else {
            Iosaonroid iosaonroid = new Iosaonroid();
            iosaonroid.setSoftwareId(software.getId());
            iosaonroid.setPlatformType(1);
            iosaonroid.setSoftwareSize(softwareDto.getSoftwareSize());
            iosaonroid.setUpdateExplain(softwareDto.getUpdateExplain());

            iosaonroid.setUpdateTime(softwareDto.getUpdateTime());
            iosaonroid.setVersion(softwareDto.getVersion());
            iosaonroidMapper.insertIosaonroid(iosaonroid);
            iosaonroid.setSoftwareId(software.getId());
            iosaonroid.setPlatformType(2);
            iosaonroid.setSoftwareSize(softwareDto.getCellSoftwareSize());
            iosaonroid.setUpdateExplain(softwareDto.getCellUpdateExplain());
            iosaonroid.setUpdateTime(softwareDto.getCellUpdateTime());
            iosaonroid.setVersion(softwareDto.getCellVersion());
            inout = iosaonroidMapper.insertIosaonroid(iosaonroid);
        }

        return inout;
    }

    /**
     * 根据id修改：修改软件主表和软件从表；返回影响的行数
     */
    @Transactional
    public Integer ModifySoftware(SoftwareDto softwareDto) throws Exception {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        Software software = new Software();
        BeanUtils.copyProperties(softwareDto, software);
        software.setUpdater(loginUser.getUsername());
        software.setUpdateTime(new Date());
        softwareMapper.updateSoftware(software);

        Iosaonroid iosaonroid = new Iosaonroid();
        iosaonroid.setSoftwareId(software.getId());
        iosaonroid.setPlatformType(1);
        iosaonroid.setSoftwareSize(softwareDto.getSoftwareSize());
        iosaonroid.setUpdateExplain(softwareDto.getUpdateExplain());
        iosaonroid.setUpdateTime(DateUtils.parseDate(softwareDto.getUpdateTime()));
        iosaonroid.setVersion(softwareDto.getVersion());
        iosaonroidMapper.updateIosaonroid(iosaonroid);

        Iosaonroid iosaonroid1 = new Iosaonroid();
        iosaonroid1.setSoftwareId(software.getId());
        iosaonroid1.setPlatformType(2);
        iosaonroid1.setSoftwareSize(softwareDto.getCellSoftwareSize());
        iosaonroid1.setUpdateExplain(softwareDto.getCellUpdateExplain());
        iosaonroid1.setUpdateTime(DateUtils.parseDate(softwareDto.getCellUpdateTime()));
        iosaonroid1.setVersion(softwareDto.getCellVersion());
        Integer count = iosaonroidMapper.updateIosaonroid(iosaonroid1);
        return count;
    }

    /**
     * 删除：逻辑删除软件信息
     */
    public Integer DeleteSoftwareById(Integer id) throws Exception {
        Software software = new Software();
        software.setId(id);
        software.setStatus(3);

        return softwareMapper.updateSoftware(software);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<SoftwareVo> querySoftwarePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //查询数据库
        List<Software> softwareList = softwareMapper.getSoftwareListByMap(param);
        List<SoftwareVo> listVo = new ArrayList<SoftwareVo>();
        for (Software software : softwareList) {
            SysDictType sysDictType = SysDictTypeMapper.getSysDictTypeById(software.getSoftwareType());
            SoftwareVo SoftwareVo = new SoftwareVo();
            SoftwareVo.setSoftwareTypeName(sysDictType.getDictName());
            BeanUtils.copyProperties(software, SoftwareVo);
            SoftwareVo.setUpdateTime(DateUtils.dateTime(software.getUpdateTime()));
            SoftwareVo.setCreateTime(DateUtils.dateTime(software.getCreateTime()));
            listVo.add(SoftwareVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<SoftwareVo> info = new PageInfo<SoftwareVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(listVo);
        return info;
    }

}
