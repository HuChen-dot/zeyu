package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.mapper.dao.BondInvestmentMapper;
import com.rewin.swhysc.service.BondInvestmentService;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BondInvestmentServiceImpl implements BondInvestmentService {


    @Resource
    private BondInvestmentMapper bondInvestmentMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public BondInvestment getBondInvestmentById(Long id) throws Exception {
        return bondInvestmentMapper.getBondInvestmentById(id);
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<BondInvestment> getBondInvestmentListByMap(Map
                                                                   <String, Object> param) throws Exception {
        return bondInvestmentMapper.getBondInvestmentListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getBondInvestmentCountByMap(Map
                                                       <String, Object> param) throws Exception {
        return bondInvestmentMapper.getBondInvestmentCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddBondInvestment(BondInvestment bondInvestment) throws Exception {
        return bondInvestmentMapper.insertBondInvestment(bondInvestment);
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyBondInvestment(BondInvestment bondInvestment) throws Exception {
        return bondInvestmentMapper.updateBondInvestment(bondInvestment);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer DeleteBondInvestmentById(Long id) throws Exception {
        Map
                <String, Object> map = new HashMap
                <String, Object>();
        map.put("invid", id);
        return bondInvestmentMapper.deleteBondInvestmentByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<BondInvestment> queryBondInvestmentPageByMap(Map
                                                                         <String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<BondInvestment> bondInvestmentList = bondInvestmentMapper.getBondInvestmentListByMap(param);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<BondInvestment> info = new PageInfo<BondInvestment>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(bondInvestmentList);
        return info;
    }

}
