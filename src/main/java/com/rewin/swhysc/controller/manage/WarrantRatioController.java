package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.dto.WarrantRatioDto;
import com.rewin.swhysc.bean.vo.WarrantRatioVo;
import com.rewin.swhysc.mapper.dao.WarrantRatioMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.WarrantRatioService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swhyscmanage/warrantRatio")
public class WarrantRatioController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WarrantRatioController.class);
    @Resource
    WarrantRatioService warrantRatioService;

    @Resource
    AuditRecordService auditRecordService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询利率费率信息列表
     */
    @GetMapping("list")
    public AjaxResult getWarrantRatioList(Integer pageNum, Integer pageSize,String state,String startDate,String endDate) {
        PageInfo<WarrantRatioVo> warrantRatioPageInfo = null;
        try {
            System.out.println("---------------"+pageNum);
            System.out.println("---------------"+pageSize);
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            warrantRatioPageInfo = warrantRatioService.getWarrantRatioVoList(pageNum,pageSize,state,startDate,endDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", warrantRatioPageInfo);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getWarrantRatio(@PathVariable Integer id) {
        WarrantRatio warrantRatio = null;
        try {
            warrantRatio = warrantRatioService.getWarrantRatioInfo(String.valueOf(id));
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", warrantRatio);
    }

    /**
     *
     */
    @PostMapping("add")
    public AjaxResult addWarrantRatio(WarrantRatioDto warrantRatioDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        WarrantRatio warrantRatio = new WarrantRatio();
        BeanUtils.copyProperties(warrantRatioDto, warrantRatio);
        warrantRatio.setCreateUser(loginUser.getUsername());
        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setCreateDate(new java.util.Date());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setState("1");
        try {
            warrantRatioService.insertWarrantRatio(warrantRatio);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(2);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(warrantRatio.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
            warrantRatio.setAuditId(String.valueOf(auditRecord.getId()));
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     *提交
     */
    @GetMapping("commit/{id}")
    public AjaxResult commitWarrantRatio(@PathVariable Integer id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();
        warrantRatio.setId(id);
        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setState("1");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(2);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(warrantRatio.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
            warrantRatio.setAuditId(String.valueOf(auditRecord.getId()));
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("提交成功");
    }

    /**
     *
     */
    @PutMapping("update")
    public AjaxResult updateWarrantRatio(WarrantRatioDto warrantRatioDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();
        BeanUtils.copyProperties(warrantRatioDto, warrantRatio);
        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        try {
            WarrantRatio warrant = warrantRatioService.getWarrantRatioInfo(String.valueOf(warrantRatio.getId()));
            if("3".equals(warrant.getState())){
                warrantRatio.setState("1");
                warrantRatioService.updateWarrantRatio(warrantRatio);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(2);//信息类型id
                auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(warrantRatio.getId()));//操作id
                auditRecordService.AddAuditRecord(auditRecord);
                warrantRatio.setAuditId(String.valueOf(auditRecord.getId()));
                warrantRatioService.updateWarrantRatio(warrantRatio);
            }else if("2".equals(warrant.getState())){
                warrant.setState("5");
                warrantRatio.setState("4");
                warrantRatioService.updateWarrantRatio(warrant);
                warrantRatioService.insertWarrantRatio(warrantRatio);
            }else{
                return AjaxResult.error("该条数据有待审核流程未结");
            }
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("修改成功");
    }

    /**
     *
     */
    @PutMapping("delete")
    public AjaxResult deleteWarrantRatioByID(String id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();

        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setId(Integer.parseInt(id));
        warrantRatio.setState("6");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(2);//信息类型id
            auditRecord.setOperationId(3);//操作类型id(1:新增，2批量上传，3,删除；4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(warrantRatio.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
            warrantRatio.setAuditId(String.valueOf(auditRecord.getId()));
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }

    /**
     *
     */
    @PutMapping("undercarriage")
    public AjaxResult undercarriage(String id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();

        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setId(Integer.parseInt(id));
        warrantRatio.setState("8");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("下架成功");
    }


}