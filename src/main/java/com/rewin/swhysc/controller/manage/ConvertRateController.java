package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.rewin.swhysc.bean.RzrqAudit;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.RzrqAuditService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.dto.AddConvertRateDto;
import com.rewin.swhysc.mapper.dao.ConvertRateMapper;
import com.rewin.swhysc.service.ConvertRateService;
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
@RequestMapping("/swhyscmanage/convertRate")
public class ConvertRateController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ConvertRateController.class);
    @Resource
    ConvertRateService convertRateService;

    @Resource
    ConvertRateMapper convertRateMapper;

    @Resource
    RzrqAuditService rzrqAuditService;
    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询折算率信息列表
     */
    @GetMapping("list")
    public AjaxResult getConverRateList(Integer pageNum, Integer pageSize,String stockCode,String stockName,String startDate,String endDate ) {
        PageInfo<ConvertRate> converRateList = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            converRateList = convertRateService.getConverRateList(pageNum, pageSize,stockCode,stockName,startDate,endDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", converRateList);
    }

    /**
     * 官网使用折算率查询
     */
    @GetMapping("queryList")
    public AjaxResult queryConverRateList(Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("state", 2);
        PageInfo<ConvertRate> convertRatePageInfo = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            //设置分页的起始页数和页面容量
            PageHelper.startPage(pageNum, pageSize);
            List<ConvertRate> converRateList = convertRateMapper.getConverRateList(map);

            //把查询出来分页好的数据放进插件的分页对象中
            convertRatePageInfo = new PageInfo<ConvertRate>(converRateList);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", convertRatePageInfo);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getConverRate(@PathVariable Integer id) {
        ConvertRate converRate = null;
        try {
            Map<String, Object> map = new HashMap<>(1);
            map.put("ID", id);
            converRate = convertRateMapper.getConverRateInfo(map);
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
    public AjaxResult fileImport(MultipartFile[] file) {
        AjaxResult result =  this.impExcel(file[0]);
        return result;
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
            convertRateMapper.insertConverRate(convertRate);
            RzrqAudit rzrqAudit = new RzrqAudit();
            rzrqAudit.setInfoType("0");
            rzrqAudit.setCommitTime(new java.util.Date());
            rzrqAudit.setCommitUser(loginUser.getUsername());
            rzrqAudit.setHandleType("0");
            rzrqAudit.setAuditStatus("0");
            rzrqAudit.setHandleNum(String.valueOf(convertRate.getId()));
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
    @PutMapping("updateConverRate")
    public AjaxResult updateConverRate(AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setUpdateDate(new java.util.Date());
        convertRate.setState("4");
        try {
            convertRateMapper.updateConvertRate(convertRate);
            RzrqAudit rzrqAudit = new RzrqAudit();
            rzrqAudit.setInfoType("0");
            rzrqAudit.setCommitTime(new java.util.Date());
            rzrqAudit.setCommitUser(loginUser.getUsername());
            rzrqAudit.setHandleType("1");
            rzrqAudit.setAuditStatus("0");
            rzrqAudit.setHandleNum(String.valueOf(convertRate.getId()));
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
    public AjaxResult deleteConverRateByID(String idStrings) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        String[] ids = idStrings.split(",");
        for(int i=0;i<ids.length;i++){
            ConvertRate convertRate = new ConvertRate();
            int id = Integer.parseInt(ids[i]);
            convertRate.setId(id);
            convertRate.setState("6");
            try {
                convertRateMapper.updateConvertRate(convertRate);
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
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        try {
            convertRateMapper.deleteConverRateAll();
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }


    public AjaxResult impExcel(MultipartFile file){
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ExcelReader er = new ExcelReader();
        int count =0;
        int error =0;
        int success = 0;

        List<ConvertRate> list_ConverRate = new ArrayList<ConvertRate>();
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
                    returnMsg += "第"+index+"行：【折算率(必填)】列不能为空;";
                } else if(map[4]==null || "".equals(map[4])){
                    returnMsg += "第"+index+"行：【交易所编号(必填)】列不能为空;";
                }else {
                    ConvertRate convertRate = new ConvertRate();
                    convertRate.setStockCode(map[1]);
                    convertRate.setStockName(map[2]);
                    convertRate.setRate(map[3]);
                    convertRate.setBourse(map[4]);
                    convertRate.setCreateUser(loginUser.getUsername());
                    convertRate.setUpdateUser(loginUser.getUsername());
                    convertRate.setCreateDate(new java.util.Date());
                    convertRate.setUpdateDate(new java.util.Date());
                    convertRate.setState("1");
                    list_ConverRate.add(convertRate);
                    index++;
                }
            }
            for (int j=0;j<list_ConverRate.size();j++){
                try {
                    convertRateMapper.insertConverRate(list_ConverRate.get(j));
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