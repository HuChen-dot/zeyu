package com.ruoyi.swhysc.controller.manage;

import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Advertise;
import com.ruoyi.swhysc.bean.ConvertRate;
import com.ruoyi.swhysc.bean.dto.AddAdvertiseDto;
import com.ruoyi.swhysc.bean.dto.AddConvertRateDto;
import com.ruoyi.swhysc.bean.vo.AdvertiseVo;
import com.ruoyi.swhysc.bean.vo.ConvertRateVo;
import com.ruoyi.swhysc.mapper.dao.ConvertRateMapper;
import com.ruoyi.swhysc.security.LoginUser;
import com.ruoyi.swhysc.service.ConvertRateService;
import com.ruoyi.swhysc.util.AjaxResult;
import com.ruoyi.swhysc.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
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
    com.ruoyi.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询折算率信息列表
     */
    @GetMapping("list")
    public AjaxResult getConverRateList(Integer pageNo, Integer pageSize,String stockCode,String stockName,String updateDate) {
        PageInfo<ConvertRate> converRateList = null;
        try {
            System.out.println("---------------"+pageNo);
            System.out.println("---------------"+pageSize);
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            converRateList = convertRateService.getConverRateList(pageNo, pageSize,stockCode,stockName,updateDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", converRateList);
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
    @PostMapping()
    public AjaxResult addConverRate(@RequestBody AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setCreateUser(loginUser.getUsername());
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setCreateDate(new java.util.Date());
        convertRate.setUpdateDate(new java.util.Date());
        convertRate.setState("0");
        try {
            convertRateMapper.insertConverRate(convertRate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     *
     */
    @PutMapping()
    public AjaxResult updateConverRate(@RequestBody AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setUpdateDate(new java.util.Date());
        try {
            convertRateMapper.updateConvertRate(convertRate);
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
        String[] ids = idStrings.split(",");
        for(int i=0;i<ids.length;i++){
            ConvertRate convertRate = new ConvertRate();
            int id = Integer.parseInt(ids[i]);
            convertRate.setId(id);
            convertRate.setState("1");
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
    @GetMapping("deleteAll")
    public AjaxResult deleteConverRateAll() {
        try {
            convertRateMapper.deleteConverRateAll();
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }
}