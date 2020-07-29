package com.ruoyi.project.interestrate.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.project.interestrate.domain.Interestrate;

import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface InterestrateService {

    /**
     * 根据id查询；返回单个对象
     */
    public Interestrate getInterestrateById(Long id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Interestrate> getInterestrateListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getInterestrateCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddInterestrate(Interestrate interestrate) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyInterestrate(Interestrate interestrate) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    public Integer DeleteInterestrateById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<Interestrate> queryInterestratePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
