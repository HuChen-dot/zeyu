package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.*;
import com.rewin.swhysc.bean.dto.AddConvertRateDto;
import com.rewin.swhysc.bean.dto.RzrqAuditDto;
import com.rewin.swhysc.mapper.dao.ConvertRateMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.*;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
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
@RequestMapping("/swhyscmanage/rzrqAudit")
public class RzrqAuditController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RzrqAuditController.class);
    @Resource
    RzrqAuditService rzrqAuditService;

    @Resource
    ConvertRateService convertRateService;

    @Resource
    BondBdService bondBdService;

    @Resource
    InterestRateService interestRateService;

    @Resource
    WarrantRatioService warrantRatioService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询折算率信息列表
     */
    @GetMapping("list")
    public AjaxResult getRzrqAuditList(Integer pageNum, Integer pageSize, String infoType, String commitTime, String auditStatus, String handleType, String state) {
        PageInfo<RzrqAudit> rzrqAuditPageInfo = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            rzrqAuditPageInfo = rzrqAuditService.getRzrqAuditList( pageNum,  pageSize,  infoType,  commitTime,  auditStatus,  handleType,  state);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", rzrqAuditPageInfo);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getRzrqAudit(@PathVariable Integer id) {
        RzrqAudit rzrqAudit = null;
        try {
            rzrqAudit = rzrqAuditService.getRzrqAuditInfo(String.valueOf(id));
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", rzrqAudit);
    }

    /**
     *
     */
    @PutMapping("adopt")
    public AjaxResult adoptRzrqAudit(RzrqAuditDto rzrqAuditDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        try {
            RzrqAudit rzrqAudit = new RzrqAudit();
            BeanUtils.copyProperties(rzrqAuditDto, rzrqAudit);
            rzrqAudit.setAuditUser(loginUser.getUsername());
            rzrqAudit.setAuditTime(new java.util.Date());
            rzrqAudit.setAuditStatus("1");
            rzrqAudit.setState("1");
            rzrqAuditService.updateRzrqAudit(rzrqAudit);
            if("0".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    ConvertRate convertRate = convertRateService.getConvertRateInfo(rzrqAudit.getHandleNum());
                    convertRate.setState("2");
                    convertRateService.updateConvertRate(convertRate);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    ConvertRate convertRate = convertRateService.getConvertRateInfo(rzrqAudit.getHandleNum());
                    convertRate.setState("2");
                    convertRateService.updateConvertRate(convertRate);
                }else if("3".equals(rzrqAudit.getHandleType())){
                    String idStrings = rzrqAudit.getHandleNum();
                    String[] ids = idStrings.split(",");
                    for(int i=0;i<ids.length;i++){
                        ConvertRate convertRate = convertRateService.getConvertRateInfo(ids[i]);
                        convertRate.setState("2");
                        convertRateService.updateConvertRate(convertRate);
                    }
                }
            }else if("1".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    BondBd bondBd = bondBdService.getBondBdInfo(rzrqAudit.getHandleNum());
                    bondBd.setState("2");
                    bondBdService.updateBondBd(bondBd);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    BondBd bondBd = bondBdService.getBondBdInfo(rzrqAudit.getHandleNum());
                    bondBd.setState("2");
                    bondBdService.updateBondBd(bondBd);
                }else if("3".equals(rzrqAudit.getHandleType())){
                    String idStrings = rzrqAudit.getHandleNum();
                    String[] ids = idStrings.split(",");
                    for(int i=0;i<ids.length;i++){
                        BondBd bondBd = bondBdService.getBondBdInfo(ids[i]);
                        bondBd.setState("2");
                        bondBdService.updateBondBd(bondBd);
                    }
                }
            }else if("2".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    WarrantRatio warrantRatio = warrantRatioService.getWarrantRatioInfo(rzrqAudit.getHandleNum());
                    warrantRatio.setState("2");
                    warrantRatioService.updateWarrantRatio(warrantRatio);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    WarrantRatio warrantRatio = warrantRatioService.getWarrantRatioInfo(rzrqAudit.getHandleNum());
                    warrantRatio.setState("2");
                    warrantRatioService.updateWarrantRatio(warrantRatio);
                }
            }else if("3".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    InterestRate interestRate = interestRateService.getInterestRateInfo(rzrqAudit.getHandleNum());
                    interestRate.setState("2");
                    interestRateService.updateInterestRate(interestRate);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    InterestRate interestRate = interestRateService.getInterestRateInfo(rzrqAudit.getHandleNum());
                    interestRate.setState("2");
                    interestRateService.updateInterestRate(interestRate);
                }
            }

        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("审核通过");
    }

    @PutMapping("reject")
    public AjaxResult rejectRzrqAudit(RzrqAuditDto rzrqAuditDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        try {
            RzrqAudit rzrqAudit = new RzrqAudit();
            BeanUtils.copyProperties(rzrqAuditDto, rzrqAudit);
            rzrqAudit.setAuditUser(loginUser.getUsername());
            rzrqAudit.setAuditTime(new java.util.Date());
            rzrqAudit.setAuditStatus("2");
            rzrqAudit.setState("1");
            rzrqAuditService.updateRzrqAudit(rzrqAudit);
            if("0".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    ConvertRate convertRate = convertRateService.getConvertRateInfo(rzrqAudit.getHandleNum());
                    convertRate.setState("3");
                    convertRateService.updateConvertRate(convertRate);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    ConvertRate convertRate = convertRateService.getConvertRateInfo(rzrqAudit.getHandleNum());
                    convertRate.setState("3");
                    convertRateService.updateConvertRate(convertRate);
                }else if("3".equals(rzrqAudit.getHandleType())){
                    String idStrings = rzrqAudit.getHandleNum();
                    String[] ids = idStrings.split(",");
                    for(int i=0;i<ids.length;i++){
                        ConvertRate convertRate = convertRateService.getConvertRateInfo(ids[i]);
                        convertRate.setState("3");
                        convertRateService.updateConvertRate(convertRate);
                    }
                }
            }else if("1".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    BondBd bondBd = bondBdService.getBondBdInfo(rzrqAudit.getHandleNum());
                    bondBd.setState("2");
                    bondBdService.updateBondBd(bondBd);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    BondBd bondBd = bondBdService.getBondBdInfo(rzrqAudit.getHandleNum());
                    bondBd.setState("2");
                    bondBdService.updateBondBd(bondBd);
                }else if("3".equals(rzrqAudit.getHandleType())){
                    String idStrings = rzrqAudit.getHandleNum();
                    String[] ids = idStrings.split(",");
                    for(int i=0;i<ids.length;i++){
                        BondBd bondBd = bondBdService.getBondBdInfo(ids[i]);
                        bondBd.setState("2");
                        bondBdService.updateBondBd(bondBd);
                    }
                }
            }else if("2".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    WarrantRatio warrantRatio = warrantRatioService.getWarrantRatioInfo(rzrqAudit.getHandleNum());
                    warrantRatio.setState("2");
                    warrantRatioService.updateWarrantRatio(warrantRatio);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    WarrantRatio warrantRatio = warrantRatioService.getWarrantRatioInfo(rzrqAudit.getHandleNum());
                    warrantRatio.setState("2");
                    warrantRatioService.updateWarrantRatio(warrantRatio);
                }
            }else if("3".equals(rzrqAudit.getInfoType())){
                if("0".equals(rzrqAudit.getHandleType())){
                    InterestRate interestRate = interestRateService.getInterestRateInfo(rzrqAudit.getHandleNum());
                    interestRate.setState("2");
                    interestRateService.updateInterestRate(interestRate);
                }else if("1".equals(rzrqAudit.getHandleType())){
                    InterestRate interestRate = interestRateService.getInterestRateInfo(rzrqAudit.getHandleNum());
                    interestRate.setState("2");
                    interestRateService.updateInterestRate(interestRate);
                }
            }

        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("审核驳回");
    }
}