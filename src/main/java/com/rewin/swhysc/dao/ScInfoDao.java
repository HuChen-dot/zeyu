package com.rewin.swhysc.dao;

import com.rewin.swhysc.bean.pojo.Marketer;
import com.rewin.swhysc.bean.pojo.OpenDept;

import java.util.List;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 14:45
 **/
public interface ScInfoDao {
    List<OpenDept> getOpenDeptList();

    List<Marketer> getMarketerInfoList(String isWest,String searcKey,String staffType);
}
