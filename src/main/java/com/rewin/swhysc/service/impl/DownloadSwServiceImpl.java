package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.DownloadSw;
import com.rewin.swhysc.bean.vo.DownloadSwVo;
import com.rewin.swhysc.mapper.dao.DownloadSwMapper;
import com.rewin.swhysc.service.DownloadSwService;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DownloadSwServiceImpl implements DownloadSwService {


    @Resource
    private DownloadSwMapper downloadSwMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public List<DownloadSwVo> getDownloadSwById(Integer id) throws Exception {
        List<DownloadSw> downloadSw = downloadSwMapper.getDownloadSwById(id);
        List<DownloadSwVo> list = new ArrayList<>();
        for (DownloadSw sw : downloadSw) {
            DownloadSwVo DownloadSwVo = new DownloadSwVo();
            BeanUtils.copyProperties(sw, DownloadSwVo);
            if (sw.getSoftwareType() == 111) {
                DownloadSwVo.setSoftwareTypeName("电脑端");
            }
            if (sw.getSoftwareType() == 112) {
                DownloadSwVo.setSoftwareTypeName("手机端");
            }
            list.add(DownloadSwVo);
        }

        return list;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<DownloadSw> getDownloadSwListByMap(Map
                                                           <String, Object> param) throws Exception {
        return downloadSwMapper.getDownloadSwListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getDownloadSwCountByMap(Map<String, Object> param) throws Exception {
        return downloadSwMapper.getDownloadSwCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddDownloadSw(DownloadSw downloadSw) throws Exception {
        return downloadSwMapper.insertDownloadSw(downloadSw);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyDownloadSw(DownloadSw downloadSw) throws Exception {
        return downloadSwMapper.updateDownloadSw(downloadSw);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteDownloadSwById(Long id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("invid", id);
        return downloadSwMapper.deleteDownloadSwByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<DownloadSwVo> queryDownloadSwPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<DownloadSw> downloadSwList = downloadSwMapper.getDownloadSwListByMap(param);
        List<DownloadSwVo> list = new ArrayList<>();
        for (DownloadSw downloadSw : downloadSwList) {
            DownloadSwVo DownloadSwVo = new DownloadSwVo();
            BeanUtils.copyProperties(downloadSw, DownloadSwVo);
            param.put("softwareId", downloadSw.getSoftwareId());
            //查询数据库，获得当前软件的下载总数
            Integer downloadSwCount = getDownloadSwCountByMap(param);
            //设置下载总数参数
            DownloadSwVo.setDownloadCount(downloadSwCount);
            if (downloadSw.getSoftwareType() == 111) {
                DownloadSwVo.setSoftwareTypeName("电脑端");
            }
            if (downloadSw.getSoftwareType() == 112) {
                DownloadSwVo.setSoftwareTypeName("手机端");
            }
            list.add(DownloadSwVo);
        }
        //设置排序
        Collections.sort(list);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<DownloadSwVo> info = new PageInfo<DownloadSwVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(list);
        return info;
    }

}
