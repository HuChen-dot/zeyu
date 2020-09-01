package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.UpdataBondInvestmentDto;
import com.rewin.swhysc.bean.vo.BondInvestmentVo;
import com.rewin.swhysc.bean.vo.UpdataBondInvestmentVo;
import com.rewin.swhysc.mapper.dao.BondInvestmentMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.BondInvestmentService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BondInvestmentServiceImpl implements BondInvestmentService {


    @Resource
    private BondInvestmentMapper bondInvestmentMapper;
    @Resource
    AuditRecordService AuditRecordService;

    @Resource
    TokenService tokenService;

    /**
     * 根据id查询；返回单个对象
     */
    public BondInvestment getBondInvestmentById(Integer id) throws Exception {
        return bondInvestmentMapper.getBondInvestmentById(id);
    }

    /**
     * 根据id查询；修改信息前查询数据回添
     */
    @Override
    public UpdataBondInvestmentVo getBondInvestment(Integer id) throws Exception {
        BondInvestment bondInvestment = bondInvestmentMapper.getBondInvestmentById(id);
        UpdataBondInvestmentVo UpdataBondInvestmentVo = new UpdataBondInvestmentVo();
        BeanUtils.copyProperties(bondInvestment, UpdataBondInvestmentVo);

        return UpdataBondInvestmentVo;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<BondInvestment> getBondInvestmentListByMap(Map<String, Object> param) throws Exception {
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer AddBondInvestment(BondInvestment bondInvestment) throws Exception {

        //向主表插入数据
        bondInvestmentMapper.insertBondInvestment(bondInvestment);
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(bondInvestment.getId().toString());
        AuditRecord.setInfoTypeid(bondInvestment.getStaffType());
        AuditRecord.setOperationId(1);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);

        AuditRecord.setTableNames("bond_investment");


        AuditRecord.setSubmitter(bondInvestment.getCreator());
        AuditRecord.setSubmitTime(bondInvestment.getCreateTime());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer ModifyBondInvestment(UpdataBondInvestmentDto bondInvestment) throws Exception {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        //把原数据的状态修改成《已发布不可操作》
        BondInvestment penStaff = new BondInvestment();
        penStaff.setStatus(32);
        penStaff.setId(bondInvestment.getId());
        bondInvestmentMapper.updateBondInvestment(penStaff);

        //先根据id查询原始人员数据信息
        BondInvestment bondInvestmen = bondInvestmentMapper.getBondInvestmentById(bondInvestment.getId());

        //初始化空集合
        BondInvestment OpenStaff = new BondInvestment();
        //先把原始人员信息复制到空集合中
        BeanUtils.copyProperties(bondInvestmen, OpenStaff);
        // 在使用修改的值替换掉复制好的空集合中的值
        OpenStaff.setCreateTime(DateUtils.dateTime(OpenStaff.getCreateTimes()));

        OpenStaff.setDeptName(bondInvestment.getDeptName());
        OpenStaff.setStaffName(bondInvestment.getStaffName());
        OpenStaff.setDuty(bondInvestment.getDuty());
        OpenStaff.setPostType(bondInvestment.getPostType());
        OpenStaff.setWorkPhone(bondInvestment.getWorkPhone());
        OpenStaff.setStaffSort(bondInvestment.getStaffSort());
        OpenStaff.setUpdater(loginUser.getUsername());
        OpenStaff.setUpdateTime(new Date());
        OpenStaff.setStatus(1);
        OpenStaff.setId(null);
        if (!"离职人员公示".equals(bondInvestmen.getStaffSort())) {
            if ("离职人员公示".equals(bondInvestment.getStaffSort())) {
                OpenStaff.setDimissionTime(new Date());
            }
        } else {
            if (!"离职人员公示".equals(bondInvestment.getStaffSort())) {
                OpenStaff.setDimissionTime(null);
            }
        }
//        插入数据
        bondInvestmentMapper.insertBondInvestment(OpenStaff);
//        ----------------------------------
        //封装参数，向中间审核表插入数据
        //初始化空对象
        AuditRecord AuditRecord = new AuditRecord();
        //封装原id
        AuditRecord.setFormerId(bondInvestment.getId().toString());
        //封装新添加数据的id
        AuditRecord.setStaffId(OpenStaff.getId().toString());
        AuditRecord.setInfoTypeid(OpenStaff.getStaffType());
        AuditRecord.setOperationId(16);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setTableNames("bond_investment");
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setSubmitTime(new Date());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;

    }


    /**
     * 逻辑删除：全量删除或批量删除
     * 要删除的
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer DeleteBondInvestment(Map<String, Object> param, String id, int i, Integer type) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(" ");
        AuditRecord.setInfoTypeid(type);

        AuditRecord.setTableNames("bond_investment");

        AuditRecord.setFormerId(id);
        //如果等于-1就是批量删除，否则就是全量删除
        if (i == -1) {
            AuditRecord.setOperationId(4);
        } else {
            AuditRecord.setOperationId(8);
        }
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setSubmitTime(new Date());
        AuditRecordService.AddAuditRecord(AuditRecord);
        return bondInvestmentMapper.deBondInvestment(param);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<BondInvestmentVo> queryBondInvestmentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<BondInvestment> bondInvestmentList = bondInvestmentMapper.getBondInvestmentPageListByMap(param);
        List<BondInvestmentVo> list = new ArrayList<>();
        for (BondInvestment bondInvestment : bondInvestmentList) {
            BondInvestmentVo BondInvestmentVo = new BondInvestmentVo();
            BeanUtils.copyProperties(bondInvestment, BondInvestmentVo);
            list.add(BondInvestmentVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<BondInvestmentVo> info = new PageInfo<BondInvestmentVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(list);
        return info;
    }

}
