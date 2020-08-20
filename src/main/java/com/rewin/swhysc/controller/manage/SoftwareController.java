package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.Software;
import com.rewin.swhysc.bean.dto.SoftwareDto;
import com.rewin.swhysc.bean.vo.SoftwareByidVo;
import com.rewin.swhysc.bean.vo.SoftwareVo;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("swhyscmanage/software")
public class SoftwareController {

    private static final Logger log = LoggerFactory.getLogger(SoftwareController.class);
    @Resource
    com.rewin.swhysc.service.SoftwareService SoftwareService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;

    /**
     * 根据软件类型和软件名称查询软件列表
     */
    @GetMapping("list")
    public AjaxResult getSoftware(Integer typeId, String softwareName, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new ConcurrentHashMap<>(3);
        if (typeId != 0) {
            map.put("softwareType", typeId);
        }
        if (softwareName != null) {
            map.put("softwareName", softwareName);
        }
        map.put("status", 2);
        PageInfo<SoftwareVo> softwareVoPageInfo = null;
        try {
            softwareVoPageInfo = SoftwareService.querySoftwarePageByMap(map, pageNum, pageSize);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.success("sql错误");
        }
        return AjaxResult.success("查询成功", softwareVoPageInfo);
    }

    /**
     * 根据软件id查询软件详细信息
     * 用来修改软件前的初始化工作
     */
    @GetMapping("info/{id}")
    public AjaxResult getSoftwareByid(@PathVariable Integer id) {
        SoftwareByidVo SoftwareByidVo = null;
        try {
            SoftwareByidVo = SoftwareService.getSoftwareById(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", SoftwareByidVo);
    }


    /**
     * 添加软件
     */
    @PostMapping()
    public AjaxResult addSoftware(@RequestBody SoftwareDto SoftwareDto) {
        try {

            SoftwareDto.setUpdateTime("2020-18-12");
            SoftwareDto.setCellUpdateTime("2020-18-12");


            SoftwareService.AddSoftware(SoftwareDto);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }


    /**
     * 修改软件信息
     */
    @PutMapping
    public AjaxResult updeSoftware(@RequestBody SoftwareDto SoftwareDto) {
        System.err.println("修改：" + SoftwareDto);
        try {
            SoftwareService.ModifySoftware(SoftwareDto);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("修改成功");
    }


    /**
     * 删除软件（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleSoftware(@PathVariable Integer id) {
        try {
            SoftwareService.DeleteSoftwareById(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }
}
