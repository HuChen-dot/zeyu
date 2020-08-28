package com.rewin.swhysc.controller.manage;


import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.SysUser;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.vo.FileName;
import com.rewin.swhysc.bean.vo.NotOpenStaffVo;
import com.rewin.swhysc.bean.vo.SoftwareVo;
import com.rewin.swhysc.bean.vo.UpdaNotOpenStaffVo;
import com.rewin.swhysc.common.exception.file.InvalidExtensionException;
import com.rewin.swhysc.common.utils.poi.ExcelUtil;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.NotOpenStaffService;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.file.FileUploadUtils;
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
    public AjaxResult getNotOpenStaff(String name, String certificateNo, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new ConcurrentHashMap<>(3);
        if (name != null && name.length() > 0) {
            map.put("staffName", name);
        }
        if (certificateNo != null && certificateNo.length() > 0) {
            map.put("certificateNo", certificateNo);
        }
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
        notOpenStaff.setStaffType(113);
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
     * 删除操作
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteNotOpenStaff(@PathVariable String id) {
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
            NotOpenStaffService.deNotOpenStaff(map, id, i);
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
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<NotOpenStaff> util = new ExcelUtil<NotOpenStaff>(NotOpenStaff.class);
        return util.importTemplateExcel("员工数据模板");
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
     * 获取批量上传的文件并转换成list集合
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        ExcelUtil<NotOpenStaff> util = new ExcelUtil<NotOpenStaff>(NotOpenStaff.class);
        List<NotOpenStaff> list = util.importExcel(file.getInputStream());


        //创建空集合转换填充基础信息
        List<NotOpenStaff> OpenStaffList = new ArrayList<>();
        for (NotOpenStaff notOpenStaff : list) {
            NotOpenStaff OpenStaff = new NotOpenStaff();
            BeanUtils.copyProperties(notOpenStaff, OpenStaff);
            OpenStaff.setStatus(1);
            OpenStaff.setStaffType(113);
            OpenStaff.setCreator(loginUser.getUsername());
            OpenStaff.setCreateTime(new Date());
            OpenStaffList.add(OpenStaff);
        }
        String message = NotOpenStaffService.importOpenStaff(OpenStaffList, loginUser.getUsername());
        return AjaxResult.success(message);
    }

}
