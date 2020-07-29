package com.ruoyi.swhysc.service.infocontent;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Infocontent;
import com.ruoyi.swhysc.mapper.infocontent.InfocontentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfocontentServiceImpl implements InfocontentService {


    @Resource
    private InfocontentMapper infocontentMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public Infocontent getInfocontentById(Integer id) throws Exception {
        return infocontentMapper.getInfocontentById(id);
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Infocontent> getInfocontentListByMap(Map
                                                             <String, Object> param) throws Exception {
        return infocontentMapper.getInfocontentListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getInfocontentCountByMap(Map
                                                    <String, Object> param) throws Exception {
        return infocontentMapper.getInfocontentCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddInfocontent(Infocontent infocontent) throws Exception {
        return infocontentMapper.insertInfocontent(infocontent);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyInfocontent(Infocontent infocontent) throws Exception {
        return infocontentMapper.updateInfocontent(infocontent);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteInfocontentById(Long id) throws Exception {
        Map
                <String, Object> map = new HashMap
                <String, Object>();
        map.put("invid", id);
        return infocontentMapper.deleteInfocontentByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<Infocontent> queryInfocontentPageByMap(Map
                                                                   <String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<Infocontent> infocontentList = infocontentMapper.getInfocontentListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Infocontent> info = new PageInfo<Infocontent>(infocontentList);
        return info;
    }

}
