package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.pojo.ConverRateExc;
import com.rewin.swhysc.bean.pojo.NOSTemplate;
import com.rewin.swhysc.bean.vo.RzrqAuditVo;
import com.rewin.swhysc.common.utils.poi.ExcelUtil;
import com.rewin.swhysc.service.RzrqAuditService;
import com.rewin.swhysc.util.page.PageInfo;

import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.vo.ConvertRateVo;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.dto.AddConvertRateDto;
import com.rewin.swhysc.service.ConvertRateService;
import com.rewin.swhysc.util.StringUtils;
import com.rewin.swhysc.util.file.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/swhyscmanage/convertRate")
public class ConvertRateController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ConvertRateController.class);
    @Resource
    ConvertRateService convertRateService;

    @Resource
    RzrqAuditService rzrqAuditService;

    @Resource
    AuditRecordService auditRecordService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询折算率信息列表
     */
    @GetMapping("list")
    public AjaxResult getConverRateList(Integer pageNum, Integer pageSize,String stockCode,String stockName,String trimDate) {
        PageInfo<ConvertRateVo> converRateList = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            converRateList = convertRateService.getConverRateList(pageNum, pageSize,stockCode,stockName,trimDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", converRateList);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getConverRate(@PathVariable Integer id) {
        ConvertRate converRate = null;
        try {
            converRate = convertRateService.getConvertRateInfo(String.valueOf(id));
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", converRate);
    }

    /**
     *
     */
    @PostMapping("fileImport")
    public AjaxResult fileImport(MultipartFile[] file,Date trimDate) {
        try {
            LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
            ExcelUtil<ConverRateExc> util = new ExcelUtil<ConverRateExc>(ConverRateExc.class);
            List<ConverRateExc> list = util.importExcel(file[0].getInputStream());
            //创建空集合转换填充基础信息
            List<ConvertRate> convertRateList = new ArrayList<>();
            List<ConvertRateVo> convertRateVoList = convertRateService.getConverRateList(null,null,null);
            Map<String,String> converMap = new HashMap<String,String>();
            if(convertRateVoList.size()>0){
                for(int j=0;j<convertRateVoList.size();j++){
                    converMap.put(convertRateVoList.get(j).getStockCode(),convertRateVoList.get(j).getStockName());
                }
            }
            for (ConverRateExc convertRate : list) {
                if(converMap.containsKey(convertRate.getStockCode())){
                    log.info("已存在证券代码为"+convertRate.getStockCode()+"的数据，请确认后重新上传");
                    return AjaxResult.error("已存在证券代码为"+convertRate.getStockCode()+"的数据，请确认后重新上传");
                }
                ConvertRate conver = new ConvertRate();
                BeanUtils.copyProperties(convertRate, conver);
                conver.setCreateUser(loginUser.getUsername());
                conver.setUpdateUser(loginUser.getUsername());
                conver.setCreateDate(new java.util.Date());
                conver.setUpdateDate(new java.util.Date());
                conver.setState("1");
                convertRateList.add(conver);
            }
            String message = convertRateService.insertConvertRateList(convertRateList,loginUser.getUsername(), file);
            return AjaxResult.success(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("导入成功");
    }

    /**
     *
     */
    @PostMapping("addConverRate")
    public AjaxResult addConverRate(AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setCreateUser(loginUser.getUsername());
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setCreateDate(new java.util.Date());
        convertRate.setUpdateDate(new java.util.Date());
        convertRate.setState("1");
        try {
            List<ConvertRateVo> converRateList = convertRateService.getConverRateList(convertRate.getStockCode(),convertRate.getStockName(),null);
            if(!converRateList.isEmpty()){
                return AjaxResult.error("该产品已存在折算率记录，请确认");
            }
            convertRateService.insertConvertRate(convertRate);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(0);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(convertRate.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     *修改
     */
    @PutMapping("updateConverRate")
    public AjaxResult updateConverRate(AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setUpdateDate(new java.util.Date());
        try {
            ConvertRate convert = convertRateService.getConvertRateInfo(String.valueOf(convertRate.getId()));
            if("3".equals(convert.getState())){
                convertRate.setState("1");
                convertRateService.updateConvertRate(convertRate);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(0);//信息类型id
                auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(convertRate.getId()));//操作id
                auditRecordService.AddAuditRecord(auditRecord);
            }else if("2".equals(convert.getState())){
                convert.setState("5");
                convertRate.setState("4");
                convertRateService.updateConvertRate(convert);
                convertRateService.insertConvertRate(convertRate);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(0);//信息类型id
                auditRecord.setOperationId(16);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(convertRate.getId()));//操作id
                auditRecord.setFormerId(String.valueOf(convert.getId()));
                auditRecordService.AddAuditRecord(auditRecord);
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
    public AjaxResult deleteConverRateByID(String idStrings) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        String[] ids = idStrings.split(",");
        String retMsg = "";
        int success =0;
        String deltoapprovalids = "";
        String delids = "";
        try {
            for(int i=0;i<ids.length;i++){
                ConvertRate convertRate = convertRateService.getConvertRateInfo(ids[i]);
                if("2".equals(convertRate.getState())){
                    deltoapprovalids += convertRate.getId() +",";
                    success ++;
                }else if("3".equals(convertRate.getState())){
                    delids += convertRate.getId() +",";
                    success ++;
                }else{
                    retMsg += convertRate.getStockCode()+":"+convertRate.getStockName()+"有待审核流程未结";
                }
            }
            if(!StringUtils.isEmpty(deltoapprovalids)){
                deltoapprovalids = deltoapprovalids.substring(0,deltoapprovalids.length()-1);
                convertRateService.subDelApproval(deltoapprovalids);
            }
            if(!StringUtils.isEmpty(delids)){
                delids = delids.substring(0,delids.length()-1);
                convertRateService.delByIds(delids);
            }
            if(ids.length>1){
                //创建批量删除审核记录
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(0);//信息类型id
                auditRecord.setOperationId(4);//操作类型id(1:新增，2批量上传，3删除，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(deltoapprovalids);//操作id
                auditRecordService.AddAuditRecord(auditRecord);
                //id(,分隔)
            }else{
                //创建删除审核记录
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(0);//信息类型id
                auditRecord.setOperationId(3);//操作类型id(1:新增，2批量上传，3删除，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(deltoapprovalids);//操作id
                auditRecordService.AddAuditRecord(auditRecord);
                //id(,分隔)
            }
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success(success+"条删除成功，"+retMsg);
    }

    /**
     *
     */
    @PutMapping("deleteAll")
    public AjaxResult deleteConverRateAll() {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        try {
            List<ConvertRateVo> convertRateList = convertRateService.getConverRateState(null,null,null);
            String ids ="";
            for(int i =0;i<convertRateList.size();i++){
                ids += convertRateList.get(i).getId() +",";
            }
            ids = ids.substring(0,ids.length()-1);
            convertRateService.subDelApproval(ids);
            //创建全部删除审核记录
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(0);//信息类型id
            auditRecord.setOperationId(8);//操作类型id(1:新增，2批量上传，3删除，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(ids);//操作id
            auditRecordService.AddAuditRecord(auditRecord);
            //id(,分隔)
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }

    @GetMapping("auditlist")
    public AjaxResult getWarrantRatioList(Integer pageNum, Integer pageSize,String infoTypeid,String startDate,
                                          String endDate,String operationId,String flowType,String status) {
        PageInfo<RzrqAuditVo> auditRecordPageInfo = null;
        try {
            auditRecordPageInfo = rzrqAuditService.getRzrqAuditList(pageNum,pageSize,"0",startDate,endDate,operationId,flowType,status);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", auditRecordPageInfo);
    }
}