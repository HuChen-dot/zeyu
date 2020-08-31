package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.SysUser;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.vo.DeleStaffAuditVo;
import com.rewin.swhysc.bean.vo.NotOpenStaffVo;
import com.rewin.swhysc.bean.vo.StaffAuditVo;
import com.rewin.swhysc.bean.vo.UpdaNotOpenStaffVo;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.web.multipart.MultipartFile;

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
     * 根据审核表id查询，《增加或修改》审核信息的详细信息
     */
    StaffAuditVo audit(AuditRecord auditRecord) throws Exception;

    /**
     * 根据审核表id查询，《批量删除或全量删除》审核信息的详细信息
     */
    DeleStaffAuditVo deteAudit(AuditRecord auditRecord) throws Exception;


    /**
     * 根据审核表id查询，《批量上传》审核信息的详细信息
     */
    DeleStaffAuditVo uploadingAudit(AuditRecord auditRecord) throws Exception;

    /**
     * 逻辑删除：全量删除或批量删除
     */
    Integer deNotOpenStaff(Map<String, Object> param, String id, int i, Integer type) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<NotOpenStaffVo> queryNotOpenStaffPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 导入员工数据
     *
     * @param userList 用户数据列表
     * @param operName 操作用户
     * @return 结果
     */
    String importOpenStaff(List<NotOpenStaff> userList, String operName, MultipartFile[] file);

    /**
     * 根据条件查询对象，返回布尔值，是否存在该对象
     * 存在返回true
     * 不存在返回false
     */
    boolean isexist(String certificateNo);
}
