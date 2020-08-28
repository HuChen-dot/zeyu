package com.rewin.swhysc.controller.manage;

import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.dto.WarrantRatioDto;
import com.rewin.swhysc.bean.vo.WarrantRatioVo;
import com.rewin.swhysc.mapper.dao.WarrantRatioMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.WarrantRatioService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swhyscmanage/warrantRatio")
public class WarrantRatioController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WarrantRatioController.class);
    @Resource
    WarrantRatioService warrantRatioService;

    @Resource
    WarrantRatioMapper warrantRatioMapper;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询利率费率信息列表
     */
    @GetMapping("list")
    public AjaxResult getWarrantRatioList(Integer pageNum, Integer pageSize,String state,String startDate,String endDate) {
        PageInfo<WarrantRatioVo> warrantRatioPageInfo = null;
        try {
            System.out.println("---------------"+pageNum);
            System.out.println("---------------"+pageSize);
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            warrantRatioPageInfo = warrantRatioService.getWarrantRatioVoList(pageNum,pageSize,state,startDate,endDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", warrantRatioPageInfo);
    }

    /**
     * 官网使用维持担保比例查询
     */
    @GetMapping("queryList")
    public AjaxResult queryInterestRateList() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("state", 2);
        List<WarrantRatioVo> warrantRatioList = null;
        try {
            warrantRatioList = warrantRatioMapper.getWarrantRatioList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", warrantRatioList);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getWarrantRatio(@PathVariable Integer id) {
        WarrantRatio warrantRatio = null;
        try {
            warrantRatio = warrantRatioService.getWarrantRatioInfo(String.valueOf(id));
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", warrantRatio);
    }

    /**
     *
     */
    @PostMapping("add")
    public AjaxResult addWarrantRatio(WarrantRatioDto warrantRatioDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        WarrantRatio warrantRatio = new WarrantRatio();
        BeanUtils.copyProperties(warrantRatioDto, warrantRatio);
        warrantRatio.setCreateUser(loginUser.getUsername());
        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setCreateDate(new java.util.Date());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setState("1");
        try {
            warrantRatioService.insertWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     *提交
     */
    @GetMapping("commit/{id}")
    public AjaxResult commitWarrantRatio(@PathVariable Integer id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();
        warrantRatio.setId(id);
        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setState("1");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("提交成功");
    }

    /**
     *
     */
    @PutMapping("update")
    public AjaxResult updateWarrantRatio(WarrantRatioDto warrantRatioDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();
        BeanUtils.copyProperties(warrantRatioDto, warrantRatio);
        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setState("4");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
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
    public AjaxResult deleteWarrantRatioByID(String id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();

        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setId(Integer.parseInt(id));
        warrantRatio.setState("6");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }

    /**
     *
     */
    @PutMapping("undercarriage")
    public AjaxResult undercarriage(String id) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        WarrantRatio warrantRatio = new WarrantRatio();

        warrantRatio.setUpdateUser(loginUser.getUsername());
        warrantRatio.setUpdateDate(new java.util.Date());
        warrantRatio.setId(Integer.parseInt(id));
        warrantRatio.setState("7");
        try {
            warrantRatioService.updateWarrantRatio(warrantRatio);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("下架成功");
    }


}