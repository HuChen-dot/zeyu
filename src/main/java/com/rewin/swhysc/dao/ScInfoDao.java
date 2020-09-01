package com.rewin.swhysc.dao;

import com.rewin.swhysc.bean.pojo.Marketer;
import com.rewin.swhysc.bean.pojo.OpenDept;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 14:45
 **/
public interface ScInfoDao {
    List<OpenDept> getOpenDeptList();

    PageInfo<Marketer> getMarketerInfoList(String isWest, String staffType, String searchKey, Set<String> OpenDept,
                                           Integer pageNum, Integer pageSize);
}
