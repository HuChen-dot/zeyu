package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.SysDept;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.vo.NotOpenStaffVo;
import com.rewin.swhysc.bean.vo.UpdaNotOpenStaffVo;
import com.rewin.swhysc.mapper.dao.NotOpenStaffMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.ISysDeptService;
import com.rewin.swhysc.service.NotOpenStaffService;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class NotOpenStaffServiceImpl implements NotOpenStaffService {


    @Resource
    private NotOpenStaffMapper notOpenStaffMapper;

    @Resource
    private ISysDeptService ISysDeptService;

    @Resource
    AuditRecordService AuditRecordService;

    @Resource
    TokenService tokenService;

    /**
     * 根据id查询；返回单个对象
     */
    public UpdaNotOpenStaffVo getNotOpenStaffById(Integer id) throws Exception {
        NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(id);
        UpdaNotOpenStaffVo UpdaNotOpenStaffVo = new UpdaNotOpenStaffVo();
        BeanUtils.copyProperties(notOpenStaff, UpdaNotOpenStaffVo);
        return UpdaNotOpenStaffVo;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<NotOpenStaff> getNotOpenStaffListByMap(Map
                                                               <String, Object> param) throws Exception {
        return notOpenStaffMapper.getNotOpenStaffListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getNotOpenStaffCountByMap(Map
                                                     <String, Object> param) throws Exception {
        return notOpenStaffMapper.getNotOpenStaffCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    @Transactional
    public Integer AddNotOpenStaff(NotOpenStaff notOpenStaff) throws Exception {

        //向主表插入数据
        notOpenStaffMapper.insertNotOpenStaff(notOpenStaff);
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(notOpenStaff.getId().toString());
        AuditRecord.setInfoTypeid(notOpenStaff.getStaffType());
        AuditRecord.setOperationId(1);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setSubmitter(notOpenStaff.getCreator());
        AuditRecord.setAuditor(" ");
        AuditRecord.setAuditOpinion(" ");
        AuditRecord.setAuditTime(new Date());
        AuditRecord.setSubmitTime(notOpenStaff.getCreateTime());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类
     */
    public Integer ModifyNotOpenStaff(AddOpenStaffDto addOpenStaffDto) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        AuditRecord AuditRecord = new AuditRecord();
        NotOpenStaff OpenStaff = new NotOpenStaff();
        AuditRecord.setFormerId(addOpenStaffDto.getId().toString());
        //先根据id查询人员信息
        NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(addOpenStaffDto.getId());
        if (addOpenStaffDto.getIsAdd() == 0) {
            notOpenStaff.setStatus(1);
            //修改原数据的状态
            NotOpenStaff upOpenStaff = new NotOpenStaff();
            upOpenStaff.setId(addOpenStaffDto.getId());
            upOpenStaff.setStatus(4);
            notOpenStaffMapper.updateNotOpenStaff(upOpenStaff);
            //向主表插入数据
            BeanUtils.copyProperties(notOpenStaff, OpenStaff);
            System.err.println("id：" + OpenStaff.getId());
            OpenStaff.setStaffName(addOpenStaffDto.getStaffName());
            OpenStaff.setCertificateNo(addOpenStaffDto.getCertificateNo());
            OpenStaff.setDeptId(addOpenStaffDto.getDeptId());
            OpenStaff.setStaffNo(addOpenStaffDto.getStaffNo());
            OpenStaff.setStaffType(addOpenStaffDto.getStaffType());
            OpenStaff.setUpdater(loginUser.getUsername());
            OpenStaff.setUpdateTime(new Date());
            OpenStaff.setId(null);
            System.err.println("id：" + OpenStaff.getId());
            notOpenStaffMapper.insertNotOpenStaff(OpenStaff);
        } else {
            notOpenStaff.setStatus(16);
            //向主表插入数据
            //向主表插入数据
            BeanUtils.copyProperties(notOpenStaff, OpenStaff);
            OpenStaff.setStaffName(addOpenStaffDto.getStaffName());
            OpenStaff.setCertificateNo(addOpenStaffDto.getCertificateNo());
            OpenStaff.setDeptId(addOpenStaffDto.getDeptId());
            OpenStaff.setStaffNo(addOpenStaffDto.getStaffNo());
            OpenStaff.setStaffType(addOpenStaffDto.getStaffType());
            OpenStaff.setUpdater(loginUser.getUsername());
            OpenStaff.setUpdateTime(new Date());
            OpenStaff.setId(null);

            notOpenStaffMapper.insertNotOpenStaff(OpenStaff);
        }
        System.err.println("id：" + OpenStaff.getId());
        //封装参数，向中间表插入数据
        AuditRecord.setStaffId(OpenStaff.getId().toString());
        AuditRecord.setInfoTypeid(addOpenStaffDto.getStaffType());
        AuditRecord.setOperationId(16);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setAuditor(" ");
        AuditRecord.setAuditOpinion(" ");
        AuditRecord.setAuditTime(new Date());
        AuditRecord.setSubmitTime(new Date());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;
    }

    /**
     * 逻辑删除：全量删除或批量删除
     */
    @Transactional
    public Integer deNotOpenStaff(Map<String, Object> param, String id) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(" ");
        AuditRecord.setInfoTypeid(113);
        AuditRecord.setFormerId(id);
        //如果不等于空就是批量删除，否则就是全量删除
            AuditRecord.setOperationId(4);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setAuditor(" ");
        AuditRecord.setAuditOpinion(" ");
        AuditRecord.setAuditTime(new Date());
        AuditRecord.setSubmitTime(new Date());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return notOpenStaffMapper.deNotOpenStaff(param);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<NotOpenStaffVo> queryNotOpenStaffPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<NotOpenStaff> notOpenStaffList = notOpenStaffMapper.getNotOpenStaffListByMap(param);
        List<NotOpenStaffVo> list = new ArrayList<>();
        for (NotOpenStaff notOpenStaff : notOpenStaffList) {
            NotOpenStaffVo NotOpenStaffVo = new NotOpenStaffVo();
            BeanUtils.copyProperties(notOpenStaff, NotOpenStaffVo);
            SysDept sysDept = ISysDeptService.selectDeptById(Long.parseLong(notOpenStaff.getDeptId().toString()));
            NotOpenStaffVo.setDeptName(sysDept.getDeptName());
            if (notOpenStaff.getStaffType() == 113) {
                NotOpenStaffVo.setStaffType("非现场开户见证人员");
            }
            list.add(NotOpenStaffVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<NotOpenStaffVo> info = new PageInfo<NotOpenStaffVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(list);
        return info;
    }

}
