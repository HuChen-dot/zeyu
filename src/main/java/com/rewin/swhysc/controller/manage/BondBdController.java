package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageHelper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swhyscmanage/bondBd")
public class BondBdController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BondBdController.class);
    @Resource
    BondBdService bondBdService;

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
            bondBdService.insertBondBd(bondBd);
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setInfoTypeid(1);//信息类型id
            auditRecord.setOperationId(1);//操作类型id(1:新增，2批量上传，4批量删除，8全部删除，16修改）
            auditRecord.setFlowType(1);//流程类型（1：代办流程， 2已办流程）
            auditRecord.setStatus(0);//审核状态（0待审核；1：通过，2：驳回，）
            auditRecord.setSubmitter(loginUser.getUsername());//提交人
            //auditRecord.setSubmitTime(new java.util.Date());//提交时间
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
                //auditRecord.setSubmitTime(new java.util.Date());//提交时间
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
                //auditRecord.setSubmitTime(new java.util.Date());//提交时间
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
                //auditRecord.setSubmitTime(new java.util.Date());//提交时间
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
                //auditRecord.setSubmitTime(new java.util.Date());//提交时间
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
            //auditRecord.setSubmitTime(new java.util.Date());//提交时间
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
    public AjaxResult fileImport(MultipartFile[] file) {
        AjaxResult result =  this.impExcel(file[0]);
        return result;
    }

    public AjaxResult impExcel(MultipartFile file){
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ExcelReader er = new ExcelReader();
        int count =0;
        int error =0;
        int success = 0;

        List<BondBd> list_BondBd = new ArrayList<BondBd>();
        String returnMsg = "";
        int index = 1;
        try {
            List<String[]> list = er.readExcel(file); //读取Excel数据内容
            count = list.size();

            for(int i=0;i<list.size();i++){
                String[] map = list.get(i);
                if(map[1]==null || "".equals(map[1])){
                    returnMsg += "第"+index+"行：【证券代码(必填)】列不能为空;";
                } else if(map[2]==null || "".equals(map[2])){
                    returnMsg += "第"+index+"行：【证券名称(必填)】列不能为空;";
                } else if(map[3]==null || "".equals(map[3])){
                    returnMsg += "第"+index+"行：【融资比例(必填)】列不能为空;";
                } else if(map[4]==null || "".equals(map[4])){
                    returnMsg += "第"+index+"行：【融券比例(必填)】列不能为空;";
                }else if(map[5]==null || "".equals(map[5])){
                    returnMsg += "第"+index+"行：【是否融资(必填)】列不能为空;";
                }else if(map[6]==null || "".equals(map[6])){
                    returnMsg += "第"+index+"行：【是否融券(必填)】列不能为空;";
                }else {
                    BondBd bondBd = new BondBd();
                    bondBd.setStockCode(map[1]);
                    bondBd.setStockName(map[2]);
                    bondBd.setRzRatio(map[3]);
                    bondBd.setRqRatio(map[4]);
                    bondBd.setIsRz(map[5]);
                    bondBd.setIsRq(map[6]);
                    bondBd.setBourse(map[7]);
                    bondBd.setCreateUser(loginUser.getUsername());
                    bondBd.setUpdateUser(loginUser.getUsername());
                    bondBd.setCreateDate(new java.util.Date());
                    bondBd.setUpdateDate(new java.util.Date());
                    bondBd.setState("1");
                    list_BondBd.add(bondBd);
                    index++;
                }
            }
            for (int j=0;j<list_BondBd.size();j++){
                try {
                    bondBdService.insertBondBd(list_BondBd.get(j));
                } catch (Exception e) {
                    log.error("查询数据库出错", e);
                    return AjaxResult.error("sql错误");
                }
            }

        } catch (Exception e) {
            log.error("批量导入信息异常", e.getMessage());
            return AjaxResult.error(returnMsg);
        }
        return AjaxResult.success("批量导入信息成功");
    }
}