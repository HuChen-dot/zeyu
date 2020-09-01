package com.rewin.swhysc.dao.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.pojo.Marketer;
import com.rewin.swhysc.bean.pojo.OpenDept;
import com.rewin.swhysc.dao.ScInfoDao;
import com.rewin.swhysc.framework.aspectj.lang.annotation.DataSource;
import com.rewin.swhysc.framework.aspectj.lang.enums.DataSourceType;
import com.rewin.swhysc.mapper.dao.MarketerMapper;
import com.rewin.swhysc.mapper.dao.OpenDeptMapper;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OpenDept> getOpenDeptList() {
        return openDeptMapper.selectAll();
    }

    @Override
    @DataSource(value = DataSourceType.SWHYBASE)
    public PageInfo<Marketer> getMarketerInfoList(String isWest, String staffType, String searcKey, Set<String> OpenDept,
                                              Integer pageNum, Integer pageSize) {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);
        List<Marketer> marketers = marketerMapper.queryMarketerInfoList(isWest, searcKey, staffType, OpenDept);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Marketer> info = new PageInfo<Marketer>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(marketers);
        return info;
    }
}
