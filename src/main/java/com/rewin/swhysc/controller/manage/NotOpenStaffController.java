package com.rewin.swhysc.controller.manage;


import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.vo.NotOpenStaffVo;
import com.rewin.swhysc.bean.vo.SoftwareVo;
import com.rewin.swhysc.bean.vo.UpdaNotOpenStaffVo;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.NotOpenStaffService;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 非现场开户人员
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
        System.err.println("添加：" + AddOpenStaffDto);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        NotOpenStaff notOpenStaff = new NotOpenStaff();
        BeanUtils.copyProperties(AddOpenStaffDto, notOpenStaff);
        notOpenStaff.setCreator(loginUser.getUsername());
        notOpenStaff.setCreateTime(new Date());
        notOpenStaff.setUpdater(" ");
        notOpenStaff.setUpdateTime(new Date());
        if (AddOpenStaffDto.getIsAdd() == 0) {
            notOpenStaff.setStatus(1);
        } else {
            notOpenStaff.setStatus(16);
        }
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
        System.err.println("修改：" + AddOpenStaffDto);
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
        System.err.println("删除：" + id);
        int i = id.indexOf("-2");
        Map<String, Object> map = new ConcurrentHashMap<>(6);
        if (i == -1) {
            String[] split = id.split(",");
            map.put("array", split);
            map.put("status", 4);
        } else {
            id = id.substring(0, id.indexOf("-2"));
            System.err.println("截取后：" + id);
            String[] split = id.split(",");
            map.put("array", split);
            map.put("status", 8);
        }
        try {
            NotOpenStaffService.deNotOpenStaff(map, id, i);
        } catch (Exception e) {
            log.error("删除数据库出错", e);
            return AjaxResult.error("删除失败，请重试");
        }
        return AjaxResult.success("提交删除请求成功，请等待审核");
    }
}
