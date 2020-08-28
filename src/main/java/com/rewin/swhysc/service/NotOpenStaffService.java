package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.vo.NotOpenStaffVo;
import com.rewin.swhysc.bean.vo.UpdaNotOpenStaffVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface NotOpenStaffService {

    /**
     * 根据id查询；返回单个对象
     */
    UpdaNotOpenStaffVo getNotOpenStaffById(Integer id) throws Exception;

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
    Integer AddNotOpenStaff(NotOpenStaff notOpenStaff) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyNotOpenStaff(AddOpenStaffDto addOpenStaffDto) throws Exception;

    /**
     * 逻辑删除：全量删除或批量删除
     */
    Integer deNotOpenStaff(Map<String, Object> param, String id, int i) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<NotOpenStaffVo> queryNotOpenStaffPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
