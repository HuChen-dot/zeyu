package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.dto.RecordDto;
import com.rewin.swhysc.bean.vo.AuditRecordVo;
import com.rewin.swhysc.bean.vo.StaffAuditVo;
import com.rewin.swhysc.mapper.dao.AuditRecordMapper;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.NotOpenStaffService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 信息公式审核控制层
 */
@RestController
@RequestMapping("/swhyscmanage/auditrecord")
public class AuditRecordController {
    private static final Logger log = LoggerFactory.getLogger(AuditRecordController.class);
    @Resource
    AuditRecordService AuditRecordService;

    @Resource
    NotOpenStaffService NotOpenStaffService;

    @Resource
    private AuditRecordMapper auditRecordMapper;

    /**
     * 查询：根据传入的条件，分页查询公示信息审核列表
     */
    @GetMapping("list")
    public AjaxResult getRecordBylist(RecordDto RecordDto) {
        Map<String, Object> map = new HashMap<>(10);
        //判断信息类型
        if (RecordDto.getInfoTypeid() != null && RecordDto.getInfoTypeid() != -1) {
            map.put("infoTypeid", RecordDto.getInfoTypeid());
        }
//        判断状态
        if (RecordDto.getStatus() != null && RecordDto.getStatus() != 0) {
            map.put("status", RecordDto.getStatus());
        }
        //判断操作类型
        if (RecordDto.getOperationId() != null && RecordDto.getOperationId() != 0) {
            map.put("operationId", RecordDto.getOperationId());
        }
        //判断代办流程类型
        if (RecordDto.getFlowType() != null) {
            map.put("flowType", RecordDto.getFlowType());
        }
        if (RecordDto.getBeginTime() != null && RecordDto.getEndTime() != null &&
                RecordDto.getBeginTime().length() > 0 && RecordDto.getEndTime().length() > 0) {
            map.put("beginTime", DateUtils.parseDate(RecordDto.getBeginTime()));
            map.put("endTime", DateUtils.parseDate(RecordDto.getEndTime()));
        }
        PageInfo<AuditRecordVo> info = null;
        try {
            info = AuditRecordService.getAuditRecordList(map, RecordDto.getPageNum(), RecordDto.getPageSize());
        } catch (Exception e) {
            log.error("查询失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("查询成功", info);
    }


    /**
     * 根据审核表id查询，审核信息的详细信息
     * 用来审核前的初始化工作
     */
    @GetMapping("info/{id}")
    public AjaxResult getRecordByid(@PathVariable Integer id) {
        //获取审核表信息
        try {
            AuditRecord auditRecord = auditRecordMapper.getAuditRecordById(id);
            if (auditRecord.getInfoTypeid() == 113) {
                StaffAuditVo audit = NotOpenStaffService.audit(auditRecord);
                return AjaxResult.success("查询成功", audit);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询错误，请重试");
        }
        return AjaxResult.error("查询错误，请重试");

    }

    /**
     * 统一信息公式审核接口
     */
    @GetMapping("make/{id}/{falg}/{auditOpinion}")
    public AjaxResult verifierIofo(@PathVariable Integer id, @PathVariable Integer falg, @PathVariable String auditOpinion) {
        System.err.println(id + "和" + falg + "和" + auditOpinion);
        try {
            //获取审核表信息
            AuditRecord auditRecord = auditRecordMapper.getAuditRecordById(id);
            Integer integer = AuditRecordService.verifyInfo(auditRecord, falg, auditOpinion);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询错误，请重试");
        }

        return AjaxResult.success("查询成功");


    }


}
