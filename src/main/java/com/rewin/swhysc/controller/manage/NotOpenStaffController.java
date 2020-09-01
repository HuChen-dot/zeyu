package com.rewin.swhysc.controller.manage;


import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.pojo.NOSTemplate;
import com.rewin.swhysc.bean.pojo.NOSZTemplate;
import com.rewin.swhysc.bean.vo.*;
import com.rewin.swhysc.common.utils.poi.ExcelUtil;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.NotOpenStaffService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 非现场开户人员控制层
 */
@RestController
@RequestMapping("/swhyscmanage/spenstaff")
public class NotOpenStaffController {
    private static final Logger log = LoggerFactory.getLogger(NotOpenStaffController.class);
    @Resource
    NotOpenStaffService NotOpenStaffService;

    @Resource
    TokenService tokenService;


    /**
     * 根据姓名或者证书编号，分页获取人员信息
     */
    @GetMapping("list")
    public AjaxResult getNotOpenStaff(String name, String certificateNo, Integer infoType, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new ConcurrentHashMap<>(3);
        if (name != null && name.length() > 0) {
            map.put("staffName", name);
        }
        if (certificateNo != null && certificateNo.length() > 0) {
            map.put("certificateNo", certificateNo);
        }
        map.put("staffType", infoType);
        map.put("status", 2);
        PageInfo<NotOpenStaffVo> notOpenStaffPageInfo = null;
        try {
            notOpenStaffPageInfo = NotOpenStaffService.queryNotOpenStaffPageByMap(map, pageNum, pageSize);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", notOpenStaffPageInfo);

    }


    /**
     * 根据id查询人员信息
     * 修改前信息回添
     */
    @GetMapping("/{id}")
    public AjaxResult getupdaNotOpenStaff(@PathVariable Integer id) {

        UpdaNotOpenStaffVo notOpenStaff = null;
        try {
            notOpenStaff = NotOpenStaffService.getNotOpenStaffById(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", notOpenStaff);

    }

    /**
     * 根据审核表id查询人员审核信息
     * 在列表中展示
     */
    @GetMapping("check/{id}")
    public AjaxResult getcheck(@PathVariable Integer id) {

        UpdaNotOpenStaffVo notOpenStaff = null;
        try {
            notOpenStaff = NotOpenStaffService.getNotOpenStaffById(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", notOpenStaff);

    }


    /**
     * 添加
     */
    @PostMapping
    public AjaxResult addNotOpenStaff(@RequestBody AddOpenStaffDto AddOpenStaffDto) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        NotOpenStaff notOpenStaff = new NotOpenStaff();
        BeanUtils.copyProperties(AddOpenStaffDto, notOpenStaff);
        notOpenStaff.setCreator(loginUser.getUsername());
        notOpenStaff.setCreateTime(new Date());
        notOpenStaff.setStatus(1);
        notOpenStaff.setStaffType(AddOpenStaffDto.getStaffType());
        notOpenStaff.setCertificatetime(DateUtils.dateTime(AddOpenStaffDto.getCertificatetimes()));
        notOpenStaff.setCertificatetype(AddOpenStaffDto.getCertificatetype());
        try {
            NotOpenStaffService.AddNotOpenStaff(notOpenStaff);
        } catch (Exception e) {
            log.error("添加数据库出错", e);
            return AjaxResult.error("添加失败，请重试");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult updataNotOpenStaff(@RequestBody AddOpenStaffDto AddOpenStaffDto) {
        try {
            NotOpenStaffService.ModifyNotOpenStaff(AddOpenStaffDto);
        } catch (Exception e) {
            log.error("添加数据库出错", e);
            return AjaxResult.error("添加失败，请重试");
        }
        return AjaxResult.success("添加成功");
    }


    /**
     * 删除操作(批量删除和全量删除
     */
    @DeleteMapping("/{id}/{type}")
    public AjaxResult deleteNotOpenStaff(@PathVariable String id, @PathVariable Integer type) {
        int i = id.indexOf("-2");
        Map<String, Object> map = new ConcurrentHashMap<>(6);
        String[] split = null;
        if (i == -1) {
            split = id.split(",");
        } else {
            id = id.substring(0, id.indexOf("-2"));
            split = id.split(",");
        }
        map.put("array", split);
        map.put("status", 32);
        try {
            NotOpenStaffService.deNotOpenStaff(map, id, i, type);
        } catch (Exception e) {
            log.error("删除数据库出错", e);
            return AjaxResult.error("删除失败，请重试");
        }
        return AjaxResult.success("提交删除请求成功，请等待审核");
    }


    /**
     * 下载数据导入的模板
     *
     * @return
     */
    @GetMapping("/importTemplate/{count}")
    public AjaxResult importTemplate(@PathVariable Integer count) {
        if (count == 113) {
            ExcelUtil<NOSTemplate> util = new ExcelUtil<NOSTemplate>(NOSTemplate.class);
            return util.importTemplateExcel("非现场开户人员数据模板");
        } else {
            ExcelUtil<NOSZTemplate> util = new ExcelUtil<NOSZTemplate>(NOSZTemplate.class);
            return util.importTemplateExcel("债券投资相关人员数据模板");
        }
    }

    /**
     * 通过证书编号检查数据库中是否以存在相同的证书编号
     */
    @GetMapping("/iscredential/{certificateNo}")
    public AjaxResult isCredential(@PathVariable String certificateNo) {
        boolean isexist = NotOpenStaffService.isexist(certificateNo);
        String mag = "-1";
        if (!isexist) {
            mag = "-2";
        }
        ;
        return AjaxResult.success(mag);
    }


    /**
     * 非现场开户人员
     * 获取批量上传的文件并转换成list集合
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile[] file, Integer count) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        ExcelUtil<NOSTemplate> util = new ExcelUtil<NOSTemplate>(NOSTemplate.class);
        List<NOSTemplate> list = util.importExcel(file[0].getInputStream());
        //创建空集合转换填充基础信息
        List<NotOpenStaff> OpenStaffList = new ArrayList<>();
        for (NOSTemplate notOpenStaff : list) {
            NotOpenStaff OpenStaff = new NotOpenStaff();
            BeanUtils.copyProperties(notOpenStaff, OpenStaff);
            OpenStaff.setStatus(1);
            OpenStaff.setStaffType(113);
            OpenStaff.setCreator(loginUser.getUsername());
            OpenStaff.setCreateTime(new Date());
            OpenStaffList.add(OpenStaff);
        }
        String message = NotOpenStaffService.importOpenStaff(OpenStaffList, loginUser.getUsername(), file, 113);
        return AjaxResult.success(message);
    }

    /**
     * 债券投资相关人员信息管理
     * 获取批量上传的文件并转换成list集合
     */
    @PostMapping("/importDatas")
    public AjaxResult importDatas(MultipartFile[] file) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        ExcelUtil<NOSZTemplate> util = new ExcelUtil<NOSZTemplate>(NOSZTemplate.class);
        List<NOSZTemplate> list = util.importExcel(file[0].getInputStream());
        //创建空集合转换填充基础信息
        List<NotOpenStaff> OpenStaffList = new ArrayList<>();
        for (NOSZTemplate notOpenStaff : list) {
            NotOpenStaff OpenStaff = new NotOpenStaff();
            BeanUtils.copyProperties(notOpenStaff, OpenStaff);
            OpenStaff.setStatus(1);
            OpenStaff.setStaffType(115);
            OpenStaff.setCreator(loginUser.getUsername());
            OpenStaff.setCreateTime(new Date());
            OpenStaffList.add(OpenStaff);
        }
        String message = NotOpenStaffService.importOpenStaff(OpenStaffList, loginUser.getUsername(), file, 115);
        return AjaxResult.success(message);
    }
}
