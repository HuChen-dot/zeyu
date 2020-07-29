package com.ruoyi.project.interestrate.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.project.interestrate.domain.Interestrate;
import com.ruoyi.project.interestrate.mapper.InterestrateMapper;
import com.ruoyi.project.interestrate.service.InterestrateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterestrateServiceImpl implements InterestrateService {


    @Resource
    private InterestrateMapper interestrateMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public Interestrate getInterestrateById(Long id) throws Exception {
        return interestrateMapper.getInterestrateById(id);
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Interestrate> getInterestrateListByMap(Map<String, Object> param) throws Exception {
        return interestrateMapper.getInterestrateListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getInterestrateCountByMap(Map<String, Object> param) throws Exception {
        return interestrateMapper.getInterestrateCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddInterestrate(Interestrate interestrate) throws Exception {
        //封装修改时间参数
        interestrate.setModificationTime(new Date());
        Integer in = interestrateMapper.insertInterestrate(interestrate);
        System.err.println("主键为：" + interestrate.getSysId());
        return in;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyInterestrate(Interestrate interestrate) throws Exception {
        //封装修改时间参数
        interestrate.setModificationTime(new Date());
        return interestrateMapper.updateInterestrate(interestrate);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteInterestrateById(Long id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysId", id);
        return interestrateMapper.deleteInterestrateByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<Interestrate> queryInterestratePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<Interestrate> interestrateList = interestrateMapper.getInterestrateListByMap(param);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Interestrate> info = new PageInfo<Interestrate>(interestrateList);
        return info;
    }

}
