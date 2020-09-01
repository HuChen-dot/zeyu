package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.pojo.BondbdExc;
import com.rewin.swhysc.bean.vo.RzrqAuditVo;
import com.rewin.swhysc.common.utils.poi.ExcelUtil;
import com.rewin.swhysc.service.RzrqAuditService;
import com.rewin.swhysc.util.page.PageInfo;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.bean.dto.BondBdDto;
import com.rewin.swhysc.bean.vo.BondBdVo;
import com.rewin.swhysc.mapper.dao.BondBdMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.BondBdService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
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
@RequestMapping("/swhyscmanage/bondBd")
public class BondBdController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BondBdController.class);
    @Resource
    BondBdService bondBdService;

    @Resource
    RzrqAuditService rzrqAuditService;

    @Resource
    AuditRecordService auditRecordService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询标的信息列表
     */
    @GetMapping("list")
    public AjaxResult getBondBdList(Integer pageNum, Integer pageSize,String stockCode,String stockName,String trimDate) {
        PageInfo<BondBdVo> bondBdList = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            bondBdList = bondBdService.getBondBdList(pageNum, pageSize,stockCode,stockName,trimDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", bondBdList);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getBondBd(@PathVariable Integer id) {
        BondBd bondBd = null;
        try {
            bondBd = bondBdService.getBondBdInfo(String.valueOf(id));
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", bondBd);
    }

    /**
     *
     */
    @PostMapping("add")
    public AjaxResult addBondBd(BondBdDto bondBdDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        BondBd bondBd = new BondBd();
        BeanUtils.copyProperties(bondBdDto, bondBd);
        bondBd.setCreateUser(loginUser.getUsername());
        bondBd.setUpdateUser(loginUser.getUsername());
        bondBd.setCreateDate(new java.util.Date());
        bondBd.setUpdateDate(new java.util.Date());
        bondBd.setState("1");
        try {
            List<BondBdVo> bondBdVoList = bondBdService.getBondBdState(bondBd.getStockCode(),bondBd.getStockName(),null);
            if(!bondBdVoList.isEmpty()){
                return AjaxResult.error("该产品已存在标的记录，请确认");
            }
            bondBdService.insertBondBd(bondBd);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(1);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(String.valueOf(bondBd.getId()));//操作id
            auditRecordService.AddAuditRecord(auditRecord);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     *
     */
    @PutMapping("update")
    public AjaxResult updateBondBd(BondBdDto bondBdDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        BondBd bondBd = new BondBd();
        BeanUtils.copyProperties(bondBdDto, bondBd);
        bondBd.setUpdateUser(loginUser.getUsername());
        bondBd.setUpdateDate(new java.util.Date());
        try {
            BondBd bond = bondBdService.getBondBdInfo(String.valueOf(bondBd.getId()));
            if("3".equals(bond.getState())){
                bondBd.setState("1");
                bondBdService.updateBondBd(bondBd);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(1);//信息类型id
                auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(bondBd.getId()));//操作id
                auditRecordService.AddAuditRecord(auditRecord);
            }else if("2".equals(bond.getState())){
                bond.setState("5");
                bondBd.setState("4");
                bondBdService.updateBondBd(bond);
                bondBdService.insertBondBd(bondBd);
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(1);//信息类型id
                auditRecord.setOperationId(16);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(String.valueOf(bondBd.getId()));//操作id
                auditRecord.setFormerId(String.valueOf(bond.getId()));
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
    public AjaxResult deleteBondBdByID(String idStrings) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        String retMsg = "";
        int success =0;
        String deltoapprovalids = "";
        String delids = "";
        String[] ids = idStrings.split(",");
        try {
            for(int i=0;i<ids.length;i++){
                BondBd bondBd = bondBdService.getBondBdInfo(ids[i]);
                if("2".equals(bondBd.getState())){
                    deltoapprovalids += bondBd.getId() +",";
                    success ++;
                }else if("3".equals(bondBd.getState())){
                    delids += bondBd.getId() +",";
                    success ++;
                }else{
                    retMsg += bondBd.getStockCode()+":"+bondBd.getStockName()+"有待审核流程未结;";
                }
            }
            if(!StringUtils.isEmpty(deltoapprovalids)){
                deltoapprovalids = deltoapprovalids.substring(0,deltoapprovalids.length()-1);
                bondBdService.subDelApproval(deltoapprovalids);
            }
            if(!StringUtils.isEmpty(delids)){
                delids = delids.substring(0,delids.length()-1);
                bondBdService.delByIds(delids);
            }
            if(ids.length>1){
                //创建批量删除审核记录
                //id(,分隔)
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(1);//信息类型id
                auditRecord.setOperationId(4);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(deltoapprovalids);//操作id
                auditRecordService.AddAuditRecord(auditRecord);
            }else{
                //创建删除审核记录
                //id(,分隔)
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setInfoTypeid(1);//信息类型id
                auditRecord.setOperationId(3);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
                auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
                auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
                auditRecord.setSubmitter(loginUser.getUsername());//提交人
                auditRecord.setSubmitTime(new java.util.Date());//提交时间
                auditRecord.setStaffId(deltoapprovalids);//操作id
                auditRecordService.AddAuditRecord(auditRecord);
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
            List<BondBdVo> bondBdList = bondBdService.getBondBdState(null,null,null);
            String ids ="";
            for(int i =0;i<bondBdList.size();i++){
                ids += bondBdList.get(i).getId() +",";
            }
            ids = ids.substring(0,ids.length()-1);
            bondBdService.subDelApproval(ids);
            //创建全部删除审核记录
            //id(,分隔)
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(1);//信息类型id
            auditRecord.setOperationId(8);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            auditRecord.setSubmitTime(new java.util.Date());//提交时间
            auditRecord.setStaffId(ids);//操作id
            auditRecordService.AddAuditRecord(auditRecord);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }

    /**
     *
     */
    @PostMapping("fileImport")
    public AjaxResult fileImport(MultipartFile[] file,Date trimDate) {
        try {
            LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
            ExcelUtil<BondbdExc> util = new ExcelUtil<BondbdExc>(BondbdExc.class);
            List<BondbdExc> list = util.importExcel(file[0].getInputStream());
            //创建空集合转换填充基础信息
            List<BondBd> bondBdList = new ArrayList<>();
            List<BondBdVo> bondBdVoList = bondBdService.getBondBdState(null,null,null);
            Map<String,String> converMap = new HashMap<String,String>();
            if(bondBdVoList.size()>0){
                for(int j=0;j<bondBdVoList.size();j++){
                    converMap.put(bondBdVoList.get(j).getStockCode(),bondBdVoList.get(j).getStockName());
                }
            }
            for (BondbdExc bondbdExc : list) {
                if(converMap.containsKey(bondbdExc.getStockCode())){
                    log.info("已存在证券代码为"+bondbdExc.getStockCode()+"的数据，请确认后重新上传");
                    return AjaxResult.error("已存在证券代码为"+bondbdExc.getStockCode()+"的数据，请确认后重新上传");
                }
                BondBd bondBd = new BondBd();
                BeanUtils.copyProperties(bondbdExc, bondBd);
                bondBd.setCreateUser(loginUser.getUsername());
                bondBd.setUpdateUser(loginUser.getUsername());
                bondBd.setCreateDate(new java.util.Date());
                bondBd.setUpdateDate(new java.util.Date());
                bondBd.setState("1");
                bondBdList.add(bondBd);
            }
            String message = bondBdService.insertBondList(bondBdList,loginUser.getUsername(), file);
            return AjaxResult.success(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("导入成功");
    }



    @GetMapping("auditlist")
    public AjaxResult getWarrantRatioList(Integer pageNum, Integer pageSize,String infoTypeid,String startDate,
                                          String endDate,String operationId,String flowType,String status) {
        PageInfo<RzrqAuditVo> auditRecordPageInfo = null;
        try {
            auditRecordPageInfo = rzrqAuditService.getRzrqAuditList(pageNum,pageSize,"1",startDate,endDate,operationId,flowType,status);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", auditRecordPageInfo);
    }
}