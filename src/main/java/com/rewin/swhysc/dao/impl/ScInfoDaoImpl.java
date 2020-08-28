package com.rewin.swhysc.dao.impl;

import com.rewin.swhysc.bean.pojo.Marketer;
import com.rewin.swhysc.bean.pojo.OpenDept;
import com.rewin.swhysc.dao.ScInfoDao;
import com.rewin.swhysc.framework.aspectj.lang.annotation.DataSource;
import com.rewin.swhysc.framework.aspectj.lang.enums.DataSourceType;
import com.rewin.swhysc.mapper.dao.MarketerMapper;
import com.rewin.swhysc.mapper.dao.OpenDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Marketer> getMarketerInfoList(String isWest,String searcKey,String staffType) {
        return marketerMapper.selectAll();
    }
}
