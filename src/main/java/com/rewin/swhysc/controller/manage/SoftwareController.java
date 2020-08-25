package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.Software;
import com.rewin.swhysc.bean.dto.SoftwareDto;
import com.rewin.swhysc.bean.dto.TabDto;
import com.rewin.swhysc.bean.vo.SoftwareByidVo;
import com.rewin.swhysc.bean.vo.SoftwareVo;
import com.rewin.swhysc.bean.vo.TabSoftwareVo;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/swhyscmanage/software")
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
    public AjaxResult getSoftware(@RequestParam(value = "typeId", required = false) Integer typeId, String softwareName, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new ConcurrentHashMap<>(3);
        if (typeId != null && typeId != 0) {
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
            return AjaxResult.success("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", softwareVoPageInfo);
    }

    /**
     * 查询软件列表
     * TAB管理根据软件类型id查询软件列表
     */
    @GetMapping("tablist/{typeId}")
    public AjaxResult getSoftwareTab(@PathVariable Integer typeId) {
        Map<String, Object> map = new ConcurrentHashMap<>(3);
        if (typeId == null) {
            return AjaxResult.success("软件类型id 参数为空，请重试");
        }
        map.put("softwareType", typeId);
        map.put("status", 2);
        List<TabSoftwareVo> lis = new ArrayList<>();
        try {
            List<Software> softwareList = SoftwareService.getSoftwareListByMap(map);
            for (Software software : softwareList) {
                TabSoftwareVo TabSoftwareVo = new TabSoftwareVo();
                TabSoftwareVo.setId(software.getId());
                TabSoftwareVo.setSoftwareName(software.getSoftwareName());
                lis.add(TabSoftwareVo);
            }
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.success("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", lis);
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
            return AjaxResult.error("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", SoftwareByidVo);
    }


    /**
     * 根据tab类型id查询软件详细信息
     * 用来TAB管理修改软件前的初始化工作
     */
    @GetMapping("tabinfo/{id}")
    public AjaxResult getSoftwaretabByid(@PathVariable Integer id) {
        TabSoftwareVo softwaretab = null;
        try {
            softwaretab = SoftwareService.getSoftwaretabById(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", softwaretab);
    }


    /**
     * 根据TAB类型id获取软件列表
     */
    @GetMapping("listtab")
    public AjaxResult getSoftwareByTab(Integer tabId, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put("isShow", tabId);
        PageInfo<TabSoftwareVo> softwareVoPageInfo = null;
        try {
            softwareVoPageInfo = SoftwareService.getSoftwareListByTabId(map, pageNum, pageSize);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.success("查询失败，请重试");
        }
        return AjaxResult.success("查询成功", softwareVoPageInfo);
    }

    /**
     * 添加软件
     */
    @PostMapping()
    public AjaxResult addSoftware(@RequestBody SoftwareDto SoftwareDto) {
        try {
            SoftwareDto.setDescribe(SoftwareDto.getDescribe() == null ? " " : SoftwareDto.getDescribe());
            SoftwareDto.setUpdateExplain(SoftwareDto.getUpdateExplain() == null ? " " : SoftwareDto.getUpdateExplain());
            SoftwareDto.setVersion(SoftwareDto.getVersion() == null ? "1.0" : SoftwareDto.getVersion());
            SoftwareService.AddSoftware(SoftwareDto);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("添加失败，请确认参数重试");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 添加或修改前端需要展示的软件
     */
    @PostMapping("isshow")
    public AjaxResult addisShow(@RequestBody TabDto tabDto) {
        try {
            Software software = new Software();
            software.setId(tabDto.getId());
            software.setIsShow(tabDto.getIsShow());
            software.setSort(tabDto.getSort());
            SoftwareService.updateSoftwareById(software);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("操作失败，请确认参数重试");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     * 修改软件信息
     */
    @PutMapping
    public AjaxResult updeSoftware(@RequestBody SoftwareDto SoftwareDto) {
        try {
            SoftwareService.ModifySoftware(SoftwareDto);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("修改失败，请确认参数重试");
        }
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除TAB管理的软件（逻辑删除）
     */
    @DeleteMapping("tab/{id}")
    public AjaxResult deleSoftwareTab(@PathVariable Integer id) {
        try {
            Software software = new Software();
            software.setId(id);
            software.setIsShow(0);
            SoftwareService.updateSoftwareById(software);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("删除失败，请联系管理员");
        }
        return AjaxResult.success("删除成功");
    }

    /**
     * 删除软件（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleSoftware(@PathVariable Integer id) {
        try {
            Software software = new Software();
            software.setId(id);
            software.setStatus(3);
            SoftwareService.updateSoftwareById(software);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("删除失败，请联系管理员");
        }
        return AjaxResult.success("删除成功");
    }
}
