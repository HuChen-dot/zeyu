package com.rewin.swhysc.mapper.dao;

import com.rewin.swhysc.bean.DownloadSw;
import com.rewin.swhysc.util.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DownloadSwMapper extends BaseMapper<DownloadSw> {
    /**
     * 根据id查询；返回单个对象
     */
    List<DownloadSw> getDownloadSwById(@Param(value = "id") Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<DownloadSw> getDownloadSwListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getDownloadSwCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer insertDownloadSw(DownloadSw downloadSw) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateDownloadSw(DownloadSw downloadSw) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteDownloadSwByMap(Map<String, Object> param) throws Exception;

}
