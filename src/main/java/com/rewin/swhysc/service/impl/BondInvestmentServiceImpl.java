package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.UpdataBondInvestmentDto;
import com.rewin.swhysc.bean.vo.*;
import com.rewin.swhysc.common.exception.CustomException;
import com.rewin.swhysc.common.exception.file.InvalidExtensionException;
import com.rewin.swhysc.controller.manage.BondInvestmentController;
import com.rewin.swhysc.mapper.dao.BondInvestmentMapper;
import com.rewin.swhysc.mapper.dao.SysDictTypeMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.BondInvestmentService;
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

@Service
public class BondInvestmentServiceImpl implements BondInvestmentService {

    private static final Logger log = LoggerFactory.getLogger(BondInvestmentServiceImpl.class);
    @Resource
    private BondInvestmentMapper bondInvestmentMapper;
    @Resource
    AuditRecordService AuditRecordService;

    @Resource
    TokenService tokenService;
    @Resource
    SysDictTypeMapper SysDictTypeMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public BondInvestment getBondInvestmentById(Integer id) throws Exception {
        return bondInvestmentMapper.getBondInvestmentById(id);
    }

    /**
     * 根据id查询；修改信息前查询数据回添
     */
    @Override
    public UpdataBondInvestmentVo getBondInvestment(Integer id) throws Exception {
        BondInvestment bondInvestment = bondInvestmentMapper.getBondInvestmentById(id);
        UpdataBondInvestmentVo UpdataBondInvestmentVo = new UpdataBondInvestmentVo();
        BeanUtils.copyProperties(bondInvestment, UpdataBondInvestmentVo);

        return UpdataBondInvestmentVo;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<BondInvestment> getBondInvestmentListByMap(Map<String, Object> param) throws Exception {
        return bondInvestmentMapper.getBondInvestmentListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getBondInvestmentCountByMap(Map<String, Object> param) throws Exception {
        return bondInvestmentMapper.getBondInvestmentCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer AddBondInvestment(BondInvestment bondInvestment) throws Exception {

        //向主表插入数据
        bondInvestmentMapper.insertBondInvestment(bondInvestment);
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(bondInvestment.getId().toString());
        AuditRecord.setInfoTypeid(bondInvestment.getStaffType());
        AuditRecord.setOperationId(1);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setTableNames("bond_investment");
        AuditRecord.setSubmitter(bondInvestment.getCreator());
        AuditRecord.setSubmitTime(bondInvestment.getCreateTime());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer ModifyBondInvestment(UpdataBondInvestmentDto bondInvestment) throws Exception {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        //把原数据的状态修改成《已发布不可操作》
        BondInvestment penStaff = new BondInvestment();
        penStaff.setStatus(32);
        penStaff.setId(bondInvestment.getId());
        bondInvestmentMapper.updateBondInvestment(penStaff);

        //先根据id查询原始人员数据信息
        BondInvestment bondInvestmen = bondInvestmentMapper.getBondInvestmentById(bondInvestment.getId());

        //初始化空集合
        BondInvestment OpenStaff = new BondInvestment();
        //先把原始人员信息复制到空集合中
        BeanUtils.copyProperties(bondInvestmen, OpenStaff);
        // 在使用修改的值替换掉复制好的空集合中的值
        OpenStaff.setCreateTime(DateUtils.dateTime(OpenStaff.getCreateTimes()));

        OpenStaff.setDeptName(bondInvestment.getDeptName());
        OpenStaff.setStaffName(bondInvestment.getStaffName());
        OpenStaff.setDuty(bondInvestment.getDuty());
        OpenStaff.setPostType(bondInvestment.getPostType());
        OpenStaff.setWorkPhone(bondInvestment.getWorkPhone());
        OpenStaff.setStaffSort(bondInvestment.getStaffSort());
        OpenStaff.setUpdater(loginUser.getUsername());
        OpenStaff.setUpdateTime(new Date());
        OpenStaff.setStatus(1);
        OpenStaff.setId(null);
        if ("离职人员公示".equals(bondInvestmen.getStaffSort())) {
            OpenStaff.setDimissionTime(DateUtils.dateTime(bondInvestment.getDimissionTimes()));
        }
//        插入数据
        bondInvestmentMapper.insertBondInvestment(OpenStaff);
//        ----------------------------------
        //封装参数，向中间审核表插入数据
        //初始化空对象
        AuditRecord AuditRecord = new AuditRecord();
        //封装原id
        AuditRecord.setFormerId(bondInvestment.getId().toString());
        //封装新添加数据的id
        AuditRecord.setStaffId(OpenStaff.getId().toString());
        AuditRecord.setInfoTypeid(OpenStaff.getStaffType());
        AuditRecord.setOperationId(16);
        AuditRecord.setFlowType(1);
        AuditRecord.setStatus(0);
        AuditRecord.setTableNames("bond_investment");
        AuditRecord.setSubmitter(loginUser.getUsername());
        AuditRecord.setSubmitTime(new Date());
        Integer integer = AuditRecordService.AddAuditRecord(AuditRecord);
        return integer;

    }


    /**
     * 逻辑删除：全量删除或批量删除
     * 要删除的
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer DeleteBondInvestment(Map<String, Object> param, String id, int i, Integer type) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //封装参数，向中间表插入数据
        AuditRecord AuditRecord = new AuditRecord();
        AuditRecord.setStaffId(" ");
        AuditRecord.setInfoTypeid(type);

        AuditRecord.setTableNames("bond_investment");

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
        return bondInvestmentMapper.deBondInvestment(param);
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
    public String importOpenStaff(List<BondInvestment> list, String operName, MultipartFile[] file) {
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new CustomException("导入员工数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<Integer> count = new ArrayList<>();
        for (BondInvestment user : list) {
            try {
                bondInvestmentMapper.insertBondInvestment(user);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + user.getStaffName() + " 导入成功");
                count.add(user.getId());

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
            AuditRecord.setInfoTypeid(114);
            AuditRecord.setOperationId(2);
            AuditRecord.setFlowType(1);
            AuditRecord.setStatus(0);
            AuditRecord.setTableNames("bond_investment");
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
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<BondInvestmentVo> queryBondInvestmentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<BondInvestment> bondInvestmentList = bondInvestmentMapper.getBondInvestmentPageListByMap(param);
        List<BondInvestmentVo> list = new ArrayList<>();
        for (BondInvestment bondInvestment : bondInvestmentList) {
            BondInvestmentVo BondInvestmentVo = new BondInvestmentVo();
            BeanUtils.copyProperties(bondInvestment, BondInvestmentVo);
            list.add(BondInvestmentVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<BondInvestmentVo> info = new PageInfo<BondInvestmentVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(list);
        return info;
    }


    /**
     * 根据审核表id查询，《批量上传》审核信息的详细信息
     */
    @Override
    public BInvestMentVo uploadingAudit(AuditRecord auditRecord) throws Exception {
//        List<BInvestMentVo> list = new ArrayList<>();
        String[] split = auditRecord.getStaffId().split(",");

        //获取债券投资人员
        BondInvestment bondInvestment = bondInvestmentMapper.getBondInvestmentById(Integer.parseInt(split[0]));

        //初始化空集合
        BInvestMentVo StaffAuditVo = new BInvestMentVo();
        //封装参数
        BeanUtils.copyProperties(bondInvestment, StaffAuditVo);
        StaffAuditVo.setId(auditRecord.getId());
        StaffAuditVo.setAuditOpinion(auditRecord.getAuditOpinion());
        StaffAuditVo.setStaffType(SysDictTypeMapper.getNameById(bondInvestment.getStaffType()));
        StaffAuditVo.setStaffSort(bondInvestment.getStaffSort());
//        StaffAuditVo.setList(list);
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
     * 根据审核表id查询，《增加或修改》审核信息的详细信息
     */
    @Override
    public BInvestMentVo audit(AuditRecord auditRecord) throws Exception {
        //获取债券投资人员
        BondInvestment bondInvestment = bondInvestmentMapper.getBondInvestmentById(Integer.parseInt(auditRecord.getStaffId()));

        //初始化空集合
        BInvestMentVo StaffAuditVo = new BInvestMentVo();
        //封装参数
        BeanUtils.copyProperties(bondInvestment, StaffAuditVo);
        StaffAuditVo.setId(auditRecord.getId());
        StaffAuditVo.setAuditOpinion(auditRecord.getAuditOpinion());
        StaffAuditVo.setDeptName(bondInvestment.getDeptName());
        StaffAuditVo.setStaffType(SysDictTypeMapper.getNameById(bondInvestment.getStaffType()));
        StaffAuditVo.setStaffSort(bondInvestment.getStaffSort());
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
    public BInvestMentVo deteAudit(AuditRecord auditRecord) throws Exception {
        List<BatchesRemVo> list = new ArrayList<>();
        String[] split = auditRecord.getFormerId().split(",");
        //遍历获取，要删除的人员信息
        for (String s : split) {
            BatchesRemVo BatchesRemVo = new BatchesRemVo();
            BondInvestment bondInvestment = bondInvestmentMapper.getBondInvestmentById(Integer.parseInt(s));
            BatchesRemVo.setStaffName(bondInvestment.getStaffName());
            BatchesRemVo.setDeptName(bondInvestment.getDeptName());
            list.add(BatchesRemVo);
        }
        //获取债券投资人员
        BondInvestment bondInvestment = bondInvestmentMapper.getBondInvestmentById(Integer.parseInt(split[0]));
        //初始化空集合
        BInvestMentVo StaffAuditVo = new BInvestMentVo();
        //封装参数
        BeanUtils.copyProperties(bondInvestment, StaffAuditVo);
        StaffAuditVo.setId(auditRecord.getId());
        StaffAuditVo.setAuditOpinion(auditRecord.getAuditOpinion());
        StaffAuditVo.setStaffType(SysDictTypeMapper.getNameById(bondInvestment.getStaffType()));
        StaffAuditVo.setStaffSort(bondInvestment.getStaffSort());
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
}
