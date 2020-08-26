package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.RzrqAudit;
import com.rewin.swhysc.bean.dto.AddConvertRateDto;
import com.rewin.swhysc.bean.dto.BondBdDto;
import com.rewin.swhysc.mapper.dao.BondBdMapper;
import com.rewin.swhysc.mapper.dao.ConvertRateMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.BondBdService;
import com.rewin.swhysc.service.ConvertRateService;
import com.rewin.swhysc.service.RzrqAuditService;
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
@RequestMapping("/swhyscmanage/bondBd")
public class BondBdController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BondBdController.class);
    @Resource
    BondBdService bondBdService;

    @Resource
    BondBdMapper bondBdMapper;

    @Resource
    RzrqAuditService rzrqAuditService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询标的信息列表
     */
    @GetMapping("list")
    public AjaxResult getBondBdList(Integer pageNum, Integer pageSize,String stockCode,String stockName,String startDate,String endDate) {
        PageInfo<BondBd> bondBdList = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            bondBdList = bondBdService.getBondBdList(pageNum, pageSize,stockCode,stockName,startDate,endDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", bondBdList);
    }

    /**
     * 官网使用标的查询
     */
    @GetMapping("queryList")
    public AjaxResult queryBondBdList(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("state", 2);
        PageInfo<BondBd> bondBdPageInfo = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            //设置分页的起始页数和页面容量
            PageHelper.startPage(pageNum, pageSize);
            List<BondBd> bondBdList = bondBdMapper.getBondBdList(map);

            //把查询出来分页好的数据放进插件的分页对象中
            bondBdPageInfo = new PageInfo<BondBd>(bondBdList);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", bondBdPageInfo);
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
            RzrqAudit rzrqAudit = new RzrqAudit();
            rzrqAudit.setInfoType("1");
            rzrqAudit.setCommitTime(new java.util.Date());
            rzrqAudit.setCommitUser(loginUser.getUsername());
            rzrqAudit.setHandleType("0");
            rzrqAudit.setAuditStatus("0");
            rzrqAudit.setHandleNum(String.valueOf(bondBd.getId()));
            rzrqAudit.setState("0");
            rzrqAuditService.insertRzrqAudit(rzrqAudit);
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
        bondBd.setState("4");
        try {
            bondBdService.updateBondBd(bondBd);
            RzrqAudit rzrqAudit = new RzrqAudit();
            rzrqAudit.setInfoType("1");
            rzrqAudit.setCommitTime(new java.util.Date());
            rzrqAudit.setCommitUser(loginUser.getUsername());
            rzrqAudit.setHandleType("1");
            rzrqAudit.setAuditStatus("0");
            rzrqAudit.setHandleNum(String.valueOf(bondBd.getId()));
            rzrqAudit.setState("0");
            rzrqAuditService.insertRzrqAudit(rzrqAudit);
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
        String[] ids = idStrings.split(",");
        for(int i=0;i<ids.length;i++){
            BondBd bondBd = new BondBd();
            int id = Integer.parseInt(ids[i]);
            bondBd.setUpdateUser(loginUser.getUsername());
            bondBd.setUpdateDate(new java.util.Date());
            bondBd.setId(id);
            bondBd.setState("6");
            try {
                bondBdService.updateBondBd(bondBd);
            } catch (Exception e) {
                log.error("查询数据库出错", e);
                return AjaxResult.error("sql错误");
            }
        }
        return AjaxResult.success("删除成功");
    }

    /**
     *
     */
    @PutMapping("deleteAll")
    public AjaxResult deleteConverRateAll() {
        try {
            bondBdService.deleteBondBdAll();
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }

}