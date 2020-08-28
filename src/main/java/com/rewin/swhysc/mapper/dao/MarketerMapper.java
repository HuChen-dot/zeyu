package com.rewin.swhysc.mapper.dao;


import com.rewin.swhysc.bean.pojo.Marketer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Set;

@Repository
public interface MarketerMapper extends BaseMapper<Marketer> {
    List<Marketer> queryMarketerInfoList(@Param("isWest") String isWest,
                                         @Param("searchKey") String searcKey,
                                         @Param("staffType") String staffType,
                                         @Param("list") Set<String> searchKey);
}