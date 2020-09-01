package com.rewin.swhysc.mapper.dao;

import com.rewin.swhysc.bean.NotOpenStaff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NotOpenStaffMapper {
    /**
     * 根据id查询；返回单个对象
     */
    NotOpenStaff getNotOpenStaffById(@Param(value = "id") Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<NotOpenStaff> getNotOpenStaffListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getNotOpenStaffCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer insertNotOpenStaff(NotOpenStaff notOpenStaff) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer updateNotOpenStaff(NotOpenStaff notOpenStaff) throws Exception;

    /**
     * 逻辑删除
     */
    Integer deNotOpenStaff(Map<String, Object> param) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    Integer deleteNotOpenStaffByMap(Map<String, Object> param) throws Exception;

    /**
     * 根据条件模糊查询；返回多个对象
     */
    List<NotOpenStaff> getStaffInfoListByType(@Param(value = "searchKey") String searchKey,
                                              @Param(value = "staffType") String staffType) throws Exception;

}
