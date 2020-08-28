package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.Iosaonroid;
import com.rewin.swhysc.bean.Software;
import com.rewin.swhysc.bean.SoftwareInfoForSc;
import com.rewin.swhysc.bean.SysDictType;
import com.rewin.swhysc.bean.dto.DownloadCountDto;
import com.rewin.swhysc.bean.dto.SoftwareDto;
import com.rewin.swhysc.bean.pojo.DownloadCount;
import com.rewin.swhysc.bean.vo.*;
import com.rewin.swhysc.common.constant.BusinessConstants;
import com.rewin.swhysc.common.constant.ExceptionCode;
import com.rewin.swhysc.common.exception.CustomException;
import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.mapper.dao.DownloadCountMapper;
import com.rewin.swhysc.mapper.dao.IosaonroidMapper;
import com.rewin.swhysc.mapper.dao.SoftwareMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private DownloadCountMapper downloadCountMapper;

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
     * 根据软件d查询软件详细信息
     * 用来TAB管理修改软件前的初始化工作
     */
    public TabSoftwareVo getSoftwaretabById(Integer id) throws Exception {
        //查询软件主表
        Software software = softwareMapper.getSoftwareById(id);
        TabSoftwareVo TabSoftwareVo = new TabSoftwareVo();
        TabSoftwareVo.setId(software.getId());
        TabSoftwareVo.setSoftwareName(software.getSoftwareName());
        TabSoftwareVo.setIsShow(software.getIsShow());
        return TabSoftwareVo;
    }


    /**
     * 根据条件查询；返回多个对象
     */
    public List<Software> getSoftwareListByMap(Map<String, Object> param) throws Exception {
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
     * 根据TAB类型id分页获取软件列表
     */
    public PageInfo<TabSoftwareVo> getSoftwareListByTabId(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        List<Software> softwareList = softwareMapper.getSoftwareListByMap(param);
        //转换参数
        List<TabSoftwareVo> listVo = new ArrayList<TabSoftwareVo>();
        for (Software software : softwareList) {
            TabSoftwareVo TabSoftwareVo = new TabSoftwareVo();
            TabSoftwareVo.setId(software.getId());
            //封装转换TAB类型名称
            if (software.getIsShow() == 1) {
                TabSoftwareVo.setIsShowName("电脑版");
            } else if (software.getIsShow() == 2) {
                TabSoftwareVo.setIsShowName("其他-电脑端");
            } else if (software.getIsShow() == 4) {
                TabSoftwareVo.setIsShowName("其他-手机端");
            }
            //封装转换软件类型名称
            if (software.getSoftwareType() == 111) {
                TabSoftwareVo.setSoftwareTypeName("电脑端");
            } else if (software.getSoftwareType() == 112) {
                TabSoftwareVo.setSoftwareTypeName("手机端");
            }
            TabSoftwareVo.setSoftwareName(software.getSoftwareName());
            TabSoftwareVo.setSort(software.getSort());
            TabSoftwareVo.setUpdateTime(DateUtils.dateTime(software.getUpdateTime()));
            listVo.add(TabSoftwareVo);
        }

        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<TabSoftwareVo> info = new PageInfo<TabSoftwareVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(listVo);
        return info;
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
    public Integer updateSoftwareById(Software software) throws Exception {


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

    @Override
    public List<SoftwareInfoForScVo> getSoftwareInfoForSc(Integer type) throws Exception{
        List<SoftwareInfoForScVo> list = new ArrayList<>();
        //参数转化
        if (type == 0) {
            //查询PC
            type = BusinessConstants.SOFTWARE_TYPE_PC;
        } else if (type == 1) {
            //查询手机
            type = BusinessConstants.SOFTWARE_TYPE_MOBILE;
        } else if (type == 2) {
            //查询全部
            type = null;
        }
        //从数据库查询软件信息
        List<SoftwareInfoForSc> softInfoList = softwareMapper.getSoftwareInfoForSc(type);
        if (!CollectionUtils.isEmpty(softInfoList)) {
            //遍历数据
            for (SoftwareInfoForSc softwareInfoForSc : softInfoList) {
                SoftwareInfoForScVo softwareInfoForScVo = new SoftwareInfoForScVo();
                softwareInfoForScVo.setName(softwareInfoForSc.getSoftwareName());//软件名称
                softwareInfoForScVo.setLogo(softwareInfoForSc.getSoftwareImg());//软件图标
                softwareInfoForScVo.setIntroduce(softwareInfoForSc.getDescribe());//软件简介
                Integer softwareType = softwareInfoForSc.getSoftwareType();//软件平台(PC/手机)
                softwareInfoForScVo.setPlatform(softwareType);
                softwareInfoForScVo.setQRCode(softwareInfoForSc.getQrCode());//软件二维码
                softwareInfoForScVo.setSort(softwareInfoForSc.getSort());//软件排序优先级
                //PC端软件详细信息
                if (softwareType == BusinessConstants.SOFTWARE_TYPE_PC) {
                    PCInfo pcInfo = new PCInfo();
                    pcInfo.setFileUrl(softwareInfoForSc.getFileUrl());//软件下载地址
                    pcInfo.setSize(softwareInfoForSc.getSoftwareSize());//软件大小
                    pcInfo.setUpdateTime(softwareInfoForSc.getSoftwareUpdateTime());//软件更新时间
                    pcInfo.setUpdateInfo(softwareInfoForSc.getUpdateExplain());//软件更新描述
                    softwareInfoForScVo.setPcInfo(pcInfo);
                }
                //手机端软件详细信息
                if (softwareType == BusinessConstants.SOFTWARE_TYPE_MOBILE) {
                    //软件ID
                    Integer id = softwareInfoForSc.getId();
                    //手机平台(安卓/IOS)
                    Integer platformType = softwareInfoForSc.getPlatformType();
                    Boolean isNewInfo = true;
                    if (list.size() > 0) {
                        for (SoftwareInfoForScVo oldInfo : list) {
                            Integer infoId = oldInfo.getId();
                            //是否已存在手机平台信息
                            if (id == infoId) {
                                isNewInfo = false;
                                //存入IOS端详细数据
                                if (platformType == BusinessConstants.PLATFORM_IOS) {
                                    IOSInfo iosInfo = new IOSInfo();
                                    iosInfo.setSize(softwareInfoForSc.getSoftwareSize());
                                    iosInfo.setUpdateInfo(softwareInfoForSc.getUpdateExplain());
                                    iosInfo.setUpdateTime(softwareInfoForSc.getSoftwareUpdateTime());
                                    oldInfo.setIosInfo(iosInfo);
                                }
                                //存入安卓端详细数据
                                if (platformType == BusinessConstants.PLATFORM_ANDROID) {
                                    AndroidInfo androidInfo = new AndroidInfo();
                                    androidInfo.setFileUrl(softwareInfoForSc.getFileUrl());
                                    androidInfo.setSize(softwareInfoForSc.getSoftwareSize());
                                    androidInfo.setUpdateInfo(softwareInfoForSc.getUpdateExplain());
                                    androidInfo.setUpdateTime(softwareInfoForSc.getSoftwareUpdateTime());
                                    oldInfo.setAndroidInfo(androidInfo);
                                }
                            }
                        }
                    }
                    //如果是新数据
                    if (isNewInfo) {
                        if (platformType == BusinessConstants.PLATFORM_IOS) {
                            IOSInfo iosInfo = new IOSInfo();
                            iosInfo.setSize(softwareInfoForSc.getSoftwareSize());
                            iosInfo.setUpdateInfo(softwareInfoForSc.getUpdateExplain());
                            iosInfo.setUpdateTime(softwareInfoForSc.getSoftwareUpdateTime());
                            softwareInfoForScVo.setIosInfo(iosInfo);
                        }
                        //存入安卓端详细数据
                        if (platformType == BusinessConstants.PLATFORM_ANDROID) {
                            AndroidInfo androidInfo = new AndroidInfo();
                            androidInfo.setFileUrl(softwareInfoForSc.getFileUrl());
                            androidInfo.setSize(softwareInfoForSc.getSoftwareSize());
                            androidInfo.setUpdateInfo(softwareInfoForSc.getUpdateExplain());
                            androidInfo.setUpdateTime(softwareInfoForSc.getSoftwareUpdateTime());
                            softwareInfoForScVo.setAndroidInfo(androidInfo);
                        }
                    } else {
                        continue;
                    }
                }
                softwareInfoForScVo.setId(softwareInfoForSc.getId()); //软件ID
                list.add(softwareInfoForScVo);
            }
        }
        return list;
    }

    @Override
    public Integer insertSoftwareDownloadCount(DownloadCountDto downloadCountDto) throws Exception{
        DownloadCount downloadCount = new DownloadCount();
        downloadCount.setSoftwareid(downloadCountDto.getSoftwareID());
        downloadCount.setIp(downloadCountDto.getIP());
        downloadCount.setSoftwaretype(downloadCountDto.getSoftwareType());
        return downloadCountMapper.insertSelective(downloadCount);
    }

}
