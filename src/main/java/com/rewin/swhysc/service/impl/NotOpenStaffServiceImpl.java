package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.SysDept;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.vo.*;
import com.rewin.swhysc.common.exception.CustomException;
import com.rewin.swhysc.common.exception.file.InvalidExtensionException;
import com.rewin.swhysc.mapper.dao.AuditRecordMapper;
import com.rewin.swhysc.mapper.dao.NotOpenStaffMapper;
import com.rewin.swhysc.mapper.dao.SysDictTypeMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.ISysDeptService;
import com.rewin.swhysc.service.NotOpenStaffService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.StringUtils;
import com.rewin.swhysc.util.file.FileUploadUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 非现场开户人员，业务实现层
 */
@Service
public class NotOpenStaffServiceImpl implements NotOpenStaffService {

    private static final Logger log = LoggerFactory.getLogger(NotOpenStaffServiceImpl.class);
    @Resource
    private NotOpenStaffMapper notOpenStaffMapper;

    @Resource
    private ISysDeptService ISysDeptService;

    @Resource
    AuditRecordService AuditRecordService;


    @Resource
    SysDictTypeMapper SysDictTypeMapper;

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
    public List<NotOpenStaff> getNotOpenStaffListByMap(Map<String, Object> param) throws Exception {
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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

        AuditRecord.setTableNames("not_open_staff");


        AuditRecord.setSubmitter(notOpenStaff.getCreator());
        AuditRecord.setSubmitTime(notOpenStaff.getCreateTime());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer ModifyNotOpenStaff(AddOpenStaffDto addOpenStaffDto) throws Exception {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        //先根据id查询原始人员数据信息
        NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(addOpenStaffDto.getId());
        //把原数据的状态修改成《已发布不可操作》
        NotOpenStaff penStaff = new NotOpenStaff();
        penStaff.setStatus(32);
        penStaff.setId(addOpenStaffDto.getId());
        notOpenStaffMapper.updateNotOpenStaff(penStaff);
        //向主表插入新的数据
        //初始化空集合
        NotOpenStaff OpenStaff = new NotOpenStaff();
        //先把原始人员信息复制到空集合中
        BeanUtils.copyProperties(notOpenStaff, OpenStaff);
        // 在使用修改的值替换掉复制好的空集合中的值

        OpenStaff.setStaffName(addOpenStaffDto.getStaffName());
        OpenStaff.setCertificateNo(addOpenStaffDto.getCertificateNo());
        OpenStaff.setStatus(1);
        OpenStaff.setDeptName(addOpenStaffDto.getDeptName());
        OpenStaff.setStaffNo(addOpenStaffDto.getStaffNo());
        OpenStaff.setPersonnelType(addOpenStaffDto.getPersonnelType());
        OpenStaff.setUpdater(loginUser.getUsername());
        OpenStaff.setUpdateTime(new Date());
        OpenStaff.setCreateTime(DateUtils.parseDate(addOpenStaffDto.getCertificatetimes()));
        OpenStaff.setCertificatetype(addOpenStaffDto.getCertificatetype());
        OpenStaff.setId(null);
        notOpenStaffMapper.insertNotOpenStaff(OpenStaff);
//        ----------------------------------
        //封装参数，向中间审核表插入数据
        //初始化空对象
        AuditRecord AuditRecord = new AuditRecord();
        //封装原id
        AuditRecord.setFormerId(addOpenStaffDto.getId().toString());
        //封装新添加数据的id
        AuditRecord.setStaffId(OpenStaff.getId().toString());
        AuditRecord.setInfoTypeid(113);
        AuditRecord.setOperationId(16);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setTableNames("not_open_staff");
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setSubmitTime(new Date());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;
    }


    /**
     * 根据审核表id查询，增加，修改操作 审核信息的详细信息
     */
    @Override
    public StaffAuditVo audit(AuditRecord auditRecord) throws Exception {
        //获取非现场人员信息
        NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(Integer.parseInt(auditRecord.getStaffId()));
        //初始化空集合
        StaffAuditVo StaffAuditVo = new StaffAuditVo();
        //封装参数
        BeanUtils.copyProperties(notOpenStaff, StaffAuditVo);
        StaffAuditVo.setId(auditRecord.getId());
        StaffAuditVo.setAuditOpinion(auditRecord.getAuditOpinion());
        StaffAuditVo.setDeptName(notOpenStaff.getDeptName());
        StaffAuditVo.setStaffType(SysDictTypeMapper.getNameById(notOpenStaff.getStaffType()));
        StaffAuditVo.setPersonnelType(notOpenStaff.getPersonnelType());
        if (auditRecord.getOperationId() == 1) {
            StaffAuditVo.setOperationId("新增");
        } else if (auditRecord.getOperationId() == 16) {
            StaffAuditVo.setOperationId("修改");
        }
        if (auditRecord.getStatus() != null && auditRecord.getStatus() == 1) {
            StaffAuditVo.setStatus("通过");
        } else if (auditRecord.getStatus() != null && auditRecord.getStatus() == 2) {
            StaffAuditVo.setStatus("驳回");
        }
        StaffAuditVo.setFlowType(auditRecord.getFlowType());
        StaffAuditVo.setSubmitter(auditRecord.getSubmitter());
        StaffAuditVo.setSubmitTime(auditRecord.getSubmitTimes());
        return StaffAuditVo;
    }


    /**
     * 根据审核表id查询，《批量删除或全量删除》审核信息的详细信息
     */
    @Override
    public DeleStaffAuditVo deteAudit(AuditRecord auditRecord) throws Exception {
        List<BatchesRemVo> list = new ArrayList<>();
        String[] split = auditRecord.getFormerId().split(",");
        for (String s : split) {
            BatchesRemVo BatchesRemVo = new BatchesRemVo();
            NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(Integer.parseInt(s));
            BatchesRemVo.setStaffName(notOpenStaff.getStaffName());
            BatchesRemVo.setDeptName(notOpenStaff.getDeptName());
            list.add(BatchesRemVo);
        }
        //获取非现场人员信息
        NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(Integer.parseInt(split[0]));
        //初始化空集合
        DeleStaffAuditVo StaffAuditVo = new DeleStaffAuditVo();
        //封装参数
        BeanUtils.copyProperties(notOpenStaff, StaffAuditVo);
        StaffAuditVo.setId(auditRecord.getId());
        StaffAuditVo.setAuditOpinion(auditRecord.getAuditOpinion());
        StaffAuditVo.setStaffType(SysDictTypeMapper.getNameById(notOpenStaff.getStaffType()));
        StaffAuditVo.setPersonnelType(notOpenStaff.getPersonnelType());
        StaffAuditVo.setList(list);
        if (auditRecord.getOperationId() == 4) {
            StaffAuditVo.setOperationId("批量删除");
        } else if (auditRecord.getOperationId() == 8) {
            StaffAuditVo.setOperationId("全量删除");
        }
        if (auditRecord.getStatus() != null && auditRecord.getStatus() == 1) {
            StaffAuditVo.setStatus("通过");
        } else if (auditRecord.getStatus() != null && auditRecord.getStatus() == 2) {
            StaffAuditVo.setStatus("驳回");
        }
        StaffAuditVo.setFlowType(auditRecord.getFlowType());
        StaffAuditVo.setSubmitter(auditRecord.getSubmitter());
        StaffAuditVo.setSubmitTime(auditRecord.getSubmitTimes());
        return StaffAuditVo;
    }


    /**
     * 根据审核表id查询，《批量上传》审核信息的详细信息
     */
    @Override
    public DeleStaffAuditVo uploadingAudit(AuditRecord auditRecord) throws Exception {
        List<BatchesRemVo> list = new ArrayList<>();
        String[] split = auditRecord.getStaffId().split(",");
        for (String s : split) {
            BatchesRemVo BatchesRemVo = new BatchesRemVo();
            NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(Integer.parseInt(s));
            BatchesRemVo.setStaffName(notOpenStaff.getStaffName());
            BatchesRemVo.setDeptName(notOpenStaff.getDeptName());
            list.add(BatchesRemVo);
        }
        //获取非现场人员信息
        NotOpenStaff notOpenStaff = notOpenStaffMapper.getNotOpenStaffById(Integer.parseInt(split[0]));
        //初始化空集合
        DeleStaffAuditVo StaffAuditVo = new DeleStaffAuditVo();
        //封装参数
        BeanUtils.copyProperties(notOpenStaff, StaffAuditVo);
        StaffAuditVo.setId(auditRecord.getId());
        StaffAuditVo.setAuditOpinion(auditRecord.getAuditOpinion());
        StaffAuditVo.setStaffType(SysDictTypeMapper.getNameById(notOpenStaff.getStaffType()));
        StaffAuditVo.setPersonnelType(notOpenStaff.getPersonnelType());
        StaffAuditVo.setList(list);
        if (auditRecord.getOperationId() == 2) {
            StaffAuditVo.setOperationId("批量上传");
        }
        if (auditRecord.getStatus() != null && auditRecord.getStatus() == 1) {
            StaffAuditVo.setStatus("通过");
        } else if (auditRecord.getStatus() != null && auditRecord.getStatus() == 2) {
            StaffAuditVo.setStatus("驳回");
        }
        StaffAuditVo.setFileUrl(FileUploadUtils.getAccessorys() + "/" + auditRecord.getFileUrl());
        StaffAuditVo.setFileName(auditRecord.getFileName());
        StaffAuditVo.setFlowType(auditRecord.getFlowType());
        StaffAuditVo.setSubmitter(auditRecord.getSubmitter());
        StaffAuditVo.setSubmitTime(auditRecord.getSubmitTimes());
        return StaffAuditVo;
    }

    /**
     * 逻辑删除：全量删除或批量删除
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer deNotOpenStaff(Map<String, Object> param, String id, int i, Integer type) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(" ");
//        if()
        AuditRecord.setInfoTypeid(type);

        AuditRecord.setTableNames("not_open_staff");

        AuditRecord.setFormerId(id);
        //如果等于-1就是批量删除，否则就是全量删除
        if (i == -1) {
            AuditRecord.setOperationId(4);
        } else {
            AuditRecord.setOperationId(8);
        }
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setSubmitTime(new Date());
        AuditRecordService.AddAuditRecord(AuditRecord);
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
            NotOpenStaffVo.setDeptName(notOpenStaff.getDeptName());
            NotOpenStaffVo.setPersonnelType(notOpenStaff.getPersonnelType());

            NotOpenStaffVo.setCertificatetype(notOpenStaff.getCertificatetype());


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


    /**
     * 导入员工数据
     *
     * @param list     员工数据列表
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String importOpenStaff(List<NotOpenStaff> list, String operName, MultipartFile[] file) {
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new CustomException("导入员工数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<Integer> count = new ArrayList<>();
        for (NotOpenStaff user : list) {
            try {
                // 验证是否存在这个员工编号
                boolean isexist = isexist(user.getCertificateNo());
                if (!isexist) {
                    notOpenStaffMapper.insertNotOpenStaff(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getStaffName() + " 导入成功");
                    count.add(user.getId());
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getCertificateNo() + " 证书编号已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getStaffName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
                throw new RuntimeException();
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            //封装参数，向中间表插入数据
            AuditRecord AuditRecord = new AuditRecord();
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < count.size(); j++)
                if (j < count.size() - 1) {
                    builder.append(count.get(j) + ",");
                } else {
                    builder.append(count.get(j));
                }
            //此处文件上传开始
            FileName fileName = null;
            try {
                fileName = FileUploadUtils.upload(file);
            } catch (IOException e) {
                e.printStackTrace();
                failureMsg.insert(0, "很抱歉，导入成功，但是上传失败！请重新上传");
            } catch (InvalidExtensionException e) {
                e.printStackTrace();
            }
//        文件上传结束----------------------------------------------
            AuditRecord.setFileName(fileName.getFileName());
            AuditRecord.setFileUrl(fileName.getRandomName());
            AuditRecord.setStaffId(builder.toString());
            AuditRecord.setInfoTypeid(113);
            AuditRecord.setOperationId(2);
            AuditRecord.setFlowType(1);
            AuditRecord.setStatus(0);
            AuditRecord.setTableNames("not_open_staff");
            AuditRecord.setSubmitter(operName);
            AuditRecord.setSubmitTime(new Date());
            try {
                AuditRecordService.AddAuditRecord(AuditRecord);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入中间表失败", e);
                throw new RuntimeException();
            }
        }
        return successMsg.toString();
    }

    /**
     * 根据条件查询对象，返回布尔值，是否存在该对象
     * 存在返回true
     * 不存在返回false
     */
    @Override
    public boolean isexist(String certificateNo) {
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put("certificateNo", certificateNo);
        boolean far = false;

        try {
            List<NotOpenStaff> notOpenStaffList = notOpenStaffMapper.getNotOpenStaffListByMap(map);
            if (notOpenStaffList != null && notOpenStaffList.size() > 0) {
                far = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return far;
    }

}
