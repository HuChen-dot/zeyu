package com.rewin.swhysc.dao.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.BondinvestmentDto;
import com.rewin.swhysc.bean.dto.OpenAccStaffDto;
import com.rewin.swhysc.bean.dto.PrivateEquityStaffDto;
import com.rewin.swhysc.bean.pojo.BondInvestmentExample;
import com.rewin.swhysc.bean.pojo.Marketer;
import com.rewin.swhysc.bean.pojo.OpenDept;
import com.rewin.swhysc.common.constant.BusinessConstants;
import com.rewin.swhysc.dao.ScInfoDao;
import com.rewin.swhysc.framework.aspectj.lang.annotation.DataSource;
import com.rewin.swhysc.framework.aspectj.lang.enums.DataSourceType;
import com.rewin.swhysc.mapper.dao.BondInvestmentMapper;
import com.rewin.swhysc.mapper.dao.MarketerMapper;
import com.rewin.swhysc.mapper.dao.NotOpenStaffMapper;
import com.rewin.swhysc.mapper.dao.OpenDeptMapper;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 14:46
 **/
@Repository
public class ScInfoDaoImpl implements ScInfoDao {
    @Autowired
    private OpenDeptMapper openDeptMapper;

    @Autowired
    private MarketerMapper marketerMapper;

    @Autowired
    private NotOpenStaffMapper notOpenStaffMapper;

    @Autowired
    private BondInvestmentMapper bondInvestmentMapper;

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OpenDept> getOpenDeptList() {
        return openDeptMapper.selectAll();
    }

    @Override
    @DataSource(value = DataSourceType.SWHYBASE)
    public PageInfo<Marketer> getMarketerInfoList(String isWest, String staffType, String searcKey, Set<String> OpenDept,
                                                  Integer pageNum, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);
        List<Marketer> marketers = marketerMapper.queryMarketerInfoList(isWest, searcKey, staffType, OpenDept);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Marketer> info = new PageInfo<>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(marketers);
        return info;
    }

    @Override
    public PageInfo<NotOpenStaff> getOpenAccStaffInfoList(OpenAccStaffDto openAccStaffDto) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(openAccStaffDto.getPageNum(), openAccStaffDto.getPageSize());
        List<NotOpenStaff> notOpenStaffs = notOpenStaffMapper.getStaffInfoListByType(openAccStaffDto.getSearchKey(),
                BusinessConstants.OPEN_ACC_STAFF);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<NotOpenStaff> info = new PageInfo<>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(notOpenStaffs);
        return info;
    }

    @Override
    public PageInfo<BondInvestment> getBondInvestmentInfoList(BondinvestmentDto bondinvestmentDto) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(bondinvestmentDto.getPageNum(), bondinvestmentDto.getPageSize());
        Map<String, Object> param = new HashMap<>();
        param.put("staffSort",bondinvestmentDto.getStaffSort());
        List<BondInvestment> bondInvestments = bondInvestmentMapper.getBondInvestmentPageListByMap(param);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<BondInvestment> info = new PageInfo<>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(bondInvestments);
        return info;
    }

    @Override
    public PageInfo<NotOpenStaff> getPrivateEquityStaffInfoList(PrivateEquityStaffDto privateEquityStaffDto) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(privateEquityStaffDto.getPageNum(), privateEquityStaffDto.getPageSize());
        List<NotOpenStaff> notOpenStaffs = notOpenStaffMapper.getStaffInfoListByType(privateEquityStaffDto.getSearchKey(),
                BusinessConstants.PRIVATE_EQUITY_STAFF);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<NotOpenStaff> info = new PageInfo<>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(notOpenStaffs);
        return info;
    }
}
