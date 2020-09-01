package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.util.page.PageInfo;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.dto.InterestRateDto;
import com.rewin.swhysc.bean.vo.InterestRateVo;
import com.rewin.swhysc.mapper.dao.InterestRateMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.InterestRateService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swhyscmanage/interestRate")
public class InterestRateController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(InterestRateController.class);
    @Resource
    InterestRateService interestRateService;

    @Resource
    AuditRecordService auditRecordService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询利率费率信息列表
     */
    @GetMapping("list")
    public AjaxResult getInterestRateList(Integer pageNum, Integer pageSize,String state,String startDate,String endDate) {
        PageInfo<InterestRateVo> interestRateVoPageInfo = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            interestRateVoPageInfo = interestRateService.getInterestRateVoList(pageNum,pageSize,state,startDate,endDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", interestRateVoPageInfo);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getInterestRate(@PathVariable Integer id) {
        InterestRate interestRate = null;
        try {
            interestRate = interestRateService.getInterestRateInfo(String.valueOf(id));
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", interestRate);
    }

    /**
     *
     */
    @PostMapping("add")
    public AjaxResult addInterestRate(InterestRateDto interestRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        InterestRate interestRate = new InterestRate();
        BeanUtils.copyProperties(interestRateDto, interestRate);
        interestRate.setCreateUser(loginUser.getUsername());
        interestRate.setUpdateUser(loginUser.getUsername());
        interestRate.setCreateDate(new java.util.Date());
        interestRate.setUpdateDate(new java.util.Date());
        interestRate.setState("1");
        try {
            interestRateService.insertInterestRate(interestRate);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(3);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(interestRate.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
            interestRate.setAuditId(String.valueOf(auditRecord.getId()));
            interestRateService.updateInterestRate(interestRate);
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
    public AjaxResult commitInterestRate(@PathVariable Integer id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        InterestRate interestRate = new InterestRate();
        interestRate.setId(id);
        interestRate.setUpdateUser(loginUser.getUsername());
        interestRate.setUpdateDate(new java.util.Date());
        interestRate.setState("1");
        try {
            interestRateService.updateInterestRate(interestRate);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(3);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(interestRate.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
            interestRate.setAuditId(String.valueOf(auditRecord.getId()));
            interestRateService.updateInterestRate(interestRate);
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
    public AjaxResult updateInterestRate(InterestRateDto interestRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        InterestRate interestRate = new InterestRate();
        BeanUtils.copyProperties(interestRateDto, interestRate);
        interestRate.setUpdateUser(loginUser.getUsername());
        interestRate.setUpdateDate(new java.util.Date());
        try {
            InterestRate interest = interestRateService.getInterestRateInfo(String.valueOf(interestRate.getId()));
            if("3".equals(interest.getState())){
                interestRate.setState("1");
                interestRateService.updateInterestRate(interestRate);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(3);//信息类型id
                auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(interestRate.getId()));//操作id
                auditRecordService.AddAuditRecord(auditRecord);
                interestRate.setAuditId(String.valueOf(auditRecord.getId()));
                interestRateService.updateInterestRate(interestRate);
            }else if("2".equals(interest.getState())){
                interest.setState("5");
                interestRate.setState("4");
                interestRateService.updateInterestRate(interest);
                interestRateService.insertInterestRate(interestRate);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(3);//信息类型id
                auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setFormerId(String.valueOf(interest.getId()));//操作id
                auditRecord.setStaffId(String.valueOf(interestRate.getId()));//操作id
                auditRecordService.AddAuditRecord(auditRecord);
                interestRate.setAuditId(String.valueOf(auditRecord.getId()));
                interestRateService.updateInterestRate(interestRate);
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
    public AjaxResult deleteInterestRateByID(String id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        InterestRate interestRate = new InterestRate();
        interestRate.setUpdateUser(loginUser.getUsername());
        interestRate.setUpdateDate(new java.util.Date());
        interestRate.setId(Integer.parseInt(id));
        try {
            InterestRate interest = interestRateService.getInterestRateInfo(String.valueOf(interestRate.getId()));
            if("3".equals(interest.getState())){
                interestRate.setState("7");
                interestRateService.updateInterestRate(interestRate);
            }else if("2".equals(interest.getState())){
                interestRate.setState("6");
                interestRateService.updateInterestRate(interestRate);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(3);//信息类型id
                auditRecord.setOperationId(3);//操作类型id(1:新增，2批量上传，3,删除；4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(interestRate.getId()));//操作id
                auditRecordService.AddAuditRecord(auditRecord);
                interestRate.setAuditId(String.valueOf(auditRecord.getId()));
                interestRateService.updateInterestRate(interestRate);
            }else{
                return AjaxResult.error("该条数据有待审核流程未结");
            }
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }

    /**
     *下架
     */
    @PutMapping("undercarriage")
    public AjaxResult undercarriage(String id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        InterestRate interestRate = new InterestRate();

        interestRate.setUpdateUser(loginUser.getUsername());
        interestRate.setUpdateDate(new java.util.Date());
        interestRate.setId(Integer.parseInt(id));
        interestRate.setState("8");
        try {
            interestRateService.updateInterestRate(interestRate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("下架成功");
    }


}