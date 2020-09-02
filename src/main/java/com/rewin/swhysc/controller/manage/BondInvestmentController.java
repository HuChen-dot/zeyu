package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.Advertise;
import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.AddAdvertiseDto;
import com.rewin.swhysc.bean.dto.AddOpenStaffDto;
import com.rewin.swhysc.bean.dto.BondInvestmentListDto;
import com.rewin.swhysc.bean.dto.UpdataBondInvestmentDto;
import com.rewin.swhysc.bean.pojo.BondTemplate;
import com.rewin.swhysc.bean.pojo.NOSTemplate;
import com.rewin.swhysc.bean.pojo.NOSZTemplate;
import com.rewin.swhysc.bean.vo.BondInvestmentVo;
import com.rewin.swhysc.bean.vo.UpdaNotOpenStaffVo;
import com.rewin.swhysc.bean.vo.UpdataBondInvestmentVo;
import com.rewin.swhysc.common.utils.poi.ExcelUtil;
import com.rewin.swhysc.mapper.dao.AdvertiseMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.BondInvestmentService;
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
 * 债券投资相关人员控制层
 */
@RestController
@RequestMapping("/swhyscmanage/bonintment")
public class BondInvestmentController {

    private static final Logger log = LoggerFactory.getLogger(BondInvestmentController.class);
    @Resource
    BondInvestmentService BondInvestmentService;

    @Resource
    TokenService tokenService;


    /**
     * 分页查询首页信息
     *
     * @param BondInvestmentListDto
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getList(BondInvestmentListDto BondInvestmentListDto) {
        Map<String, Object> map = new ConcurrentHashMap<>(2);
        if (null != BondInvestmentListDto.getStaffSort() && !"-1".equals(BondInvestmentListDto.getStaffSort()) && BondInvestmentListDto.getStaffSort().length() > 0) {
            map.put("staffSort", BondInvestmentListDto.getStaffSort());
        }
        if (BondInvestmentListDto.getStaffName() != null) {
            map.put("staffName", BondInvestmentListDto.getStaffName());
        }
        map.put("status", 2);
        PageInfo<BondInvestmentVo> Info = null;
        try {
            Info = BondInvestmentService.queryBondInvestmentPageByMap(map, BondInvestmentListDto.getPageNum(), BondInvestmentListDto.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询数据库出错：", e);
            return AjaxResult.error("查询错误，请重试");
        }
        return AjaxResult.success("查询成功", Info);
    }

    /**
     * 根据id查询人员信息
     * 修改前信息回添
     */
    @GetMapping("/{id}")
    public AjaxResult getupdaBondInvestment(@PathVariable Integer id) {

        UpdataBondInvestmentVo UpdataBondInvestmentVo = null;
        try {
            UpdataBondInvestmentVo = BondInvestmentService.getBondInvestment(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", UpdataBondInvestmentVo);

    }

    /**
     * 添加债券管理人员信息
     */
    @PostMapping
    public AjaxResult addBondInvestment(@RequestBody UpdataBondInvestmentDto UpdataBondInvestmentDto) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        BondInvestment BondInvestment = new BondInvestment();
        BeanUtils.copyProperties(UpdataBondInvestmentDto, BondInvestment);
        BondInvestment.setCreator(loginUser.getUsername());
        BondInvestment.setCreateTime(new Date());
        if ("离职人员公示".equals(UpdataBondInvestmentDto.getStaffSort())) {
            BondInvestment.setDimissionTime(DateUtils.dateTime(UpdataBondInvestmentDto.getDimissionTimes()));
        }
        BondInvestment.setStatus(1);
        BondInvestment.setStaffType(UpdataBondInvestmentDto.getStaffType());
        try {
            BondInvestmentService.AddBondInvestment(BondInvestment);
        } catch (Exception e) {
            log.error("添加数据库出错", e);
            return AjaxResult.error("添加失败，请重试");
        }
        return AjaxResult.success("添加成功");
    }


    /**
     * 修改债券管理人员信息
     */
    @PutMapping
    public AjaxResult updBondInvestment(@RequestBody UpdataBondInvestmentDto UpdataBondInvestmentDto) {
        try {
            BondInvestmentService.ModifyBondInvestment(UpdataBondInvestmentDto);
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
    public AjaxResult deleteBondInvestment(@PathVariable String id, @PathVariable Integer type) {
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
            BondInvestmentService.DeleteBondInvestment(map, id, i, type);
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
        ExcelUtil<BondTemplate> util = new ExcelUtil<BondTemplate>(BondTemplate.class);
        return util.importTemplateExcel("债券投资相关人数据模板");
    }

    /**
     * 批量上传
     * 获取批量上传的文件并转换成list集合
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile[] file) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        ExcelUtil<BondTemplate> util = new ExcelUtil<BondTemplate>(BondTemplate.class);
        List<BondTemplate> list = util.importExcel(file[0].getInputStream());
        //创建空集合转换填充基础信息
        List<BondInvestment> OpenStaffList = new ArrayList<>();
        for (BondTemplate notOpenStaff : list) {
            BondInvestment OpenStaff = new BondInvestment();
            BeanUtils.copyProperties(notOpenStaff, OpenStaff);
            OpenStaff.setCreator(loginUser.getUsername());
            OpenStaff.setCreateTime(new Date());
            OpenStaff.setStatus(1);
            OpenStaff.setStaffType(114);
            if ("离职人员公示".equals(notOpenStaff.getStaffSort())) {
                OpenStaff.setDimissionTime(new Date());
            }
            OpenStaffList.add(OpenStaff);
        }
        String message = BondInvestmentService.importOpenStaff(OpenStaffList, loginUser.getUsername(), file);
        return AjaxResult.success(message);
    }
}
