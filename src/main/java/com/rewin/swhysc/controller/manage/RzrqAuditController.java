package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.vo.AuditRecordVo;
import com.rewin.swhysc.bean.vo.RzrqAuditVo;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.RzrqAuditService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/swhyscmanage/rzrqAudit")
public class RzrqAuditController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RzrqAuditController.class);
    @Resource
    RzrqAuditService rzrqAuditService;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询利率费率信息列表
     */
    @GetMapping("list")
    public AjaxResult getWarrantRatioList(Integer pageNum, Integer pageSize,String infoTypeid,String startDate,
                                          String endDate,String operationId,String flowType,String status) {
        PageInfo<RzrqAuditVo> auditRecordPageInfo = null;
        try {
            auditRecordPageInfo = rzrqAuditService.getRzrqAuditList(pageNum,pageSize,infoTypeid,startDate,endDate,operationId,flowType,status);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", auditRecordPageInfo);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getWarrantRatio(@PathVariable Integer id) {
        WarrantRatio warrantRatio = null;
        try {

        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", warrantRatio);
    }
}