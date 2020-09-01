package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.bean.dto.UpdataBondInvestmentDto;
import com.rewin.swhysc.bean.vo.BondInvestmentVo;
import com.rewin.swhysc.bean.vo.UpdataBondInvestmentVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface BondInvestmentService {

    /**
     * 根据id查询；返回单个对象
     */
    BondInvestment getBondInvestmentById(Integer id) throws Exception;

    /**
     * 根据id查询；修改信息前查询数据回添
     */
    UpdataBondInvestmentVo getBondInvestment(Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<BondInvestment> getBondInvestmentListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getBondInvestmentCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddBondInvestment(BondInvestment bondInvestment) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyBondInvestment(UpdataBondInvestmentDto bondInvestment) throws Exception;

    /**
     * 逻辑删除：全量删除或批量删除
     */
    Integer DeleteBondInvestment(Map<String, Object> param, String id, int i, Integer type) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<BondInvestmentVo> queryBondInvestmentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
