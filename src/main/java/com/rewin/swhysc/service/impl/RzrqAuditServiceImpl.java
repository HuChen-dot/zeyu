package com.rewin.swhysc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.ConvertRate;
import com.rewin.swhysc.bean.InterestRate;
import com.rewin.swhysc.bean.WarrantRatio;
import com.rewin.swhysc.bean.vo.BondBdVo;
import com.rewin.swhysc.bean.vo.ConvertRateVo;
import com.rewin.swhysc.bean.vo.RzrqAuditVo;
import com.rewin.swhysc.mapper.dao.AuditRecordMapper;
import com.rewin.swhysc.mapper.dao.ConvertRateMapper;
import com.rewin.swhysc.service.*;
import com.rewin.swhysc.util.StringUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 融资融卷专栏------审核流程
 */
@Service
public class RzrqAuditServiceImpl implements RzrqAuditService {
    @Resource
    private AuditRecordMapper auditRecordMapper;
    @Resource
    private ConvertRateService convertRateService;
    @Resource
    private BondBdService bondBdService;
    @Resource
    private InterestRateService interestRateService;
    @Resource
    private WarrantRatioService warrantRatioService;

    public PageInfo<RzrqAuditVo> getRzrqAuditList(Integer pageNum, Integer pageSize, String infoTypeid, String startDate,
                                                  String endDate, String operationId, String flowType, String status) throws Exception {
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(infoTypeid)){
            map.put("infoTypeid", infoTypeid);
        }
        if(!StringUtils.isEmpty(operationId)){
            map.put("operationId", operationId);
        }
        if(!StringUtils.isEmpty(flowType)){
            map.put("flowType", flowType);
        }
        if(!StringUtils.isEmpty(status)){
            map.put("status", status);
        }
        if(!StringUtils.isEmpty(startDate)){
            map.put("startDate", startDate+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endDate)){
            map.put("endDate", endDate+" 23:59:59");
        }

        List<RzrqAuditVo> rzrqAuditList = auditRecordMapper.getRzrqAuditList(map);

        PageInfo<RzrqAuditVo> info = new PageInfo<RzrqAuditVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(rzrqAuditList);
        return info;
    }

    /**
     * 根据id查询；返回单个对象
     */
    public RzrqAuditVo getRzrqAuditInfo(Integer id) throws Exception {
        RzrqAuditVo rzrqAuditVo =  auditRecordMapper.getRzrqAuditById(id);
        if(rzrqAuditVo.getInfoTypeid()==0){
            List<ConvertRateVo> infoList = convertRateService.getConverRateList(rzrqAuditVo.getStaffId(),null,null,null,null);
            rzrqAuditVo.setInfolist(infoList);
            if(!StringUtils.isEmpty(rzrqAuditVo.getFormerId())){
                List<ConvertRateVo> formList = convertRateService.getConverRateList(rzrqAuditVo.getFormerId(),null,null,null,null);
                rzrqAuditVo.setInfolist(formList);
            }
        }else if(rzrqAuditVo.getInfoTypeid()==1){
            List<BondBdVo> infoList = bondBdService.getBondBdList(rzrqAuditVo.getStaffId(),null,null,null,null);
            rzrqAuditVo.setInfolist(infoList);
            if(!StringUtils.isEmpty(rzrqAuditVo.getFormerId())){
                List<BondBdVo> formList = bondBdService.getBondBdList(rzrqAuditVo.getFormerId(),null,null,null,null);
                rzrqAuditVo.setInfolist(formList);
            }
        }else if(rzrqAuditVo.getInfoTypeid()==2){
            List<InterestRate> infoList = interestRateService.getInterestRateList(rzrqAuditVo.getStaffId());
            rzrqAuditVo.setInfolist(infoList);
            if(!StringUtils.isEmpty(rzrqAuditVo.getFormerId())){
                List<InterestRate> formList = interestRateService.getInterestRateList(rzrqAuditVo.getFormerId());
                rzrqAuditVo.setInfolist(formList);
            }
        }else if(rzrqAuditVo.getInfoTypeid()==3){
            List<WarrantRatio> infoList = warrantRatioService.getWarrantRatioList(rzrqAuditVo.getStaffId());
            rzrqAuditVo.setInfolist(infoList);
            if(!StringUtils.isEmpty(rzrqAuditVo.getFormerId())){
                List<WarrantRatio> formList = warrantRatioService.getWarrantRatioList(rzrqAuditVo.getFormerId());
                rzrqAuditVo.setInfolist(formList);
            }
        }
        return rzrqAuditVo;
    }

    /**
     * 根据id查询；返回单个对象
     */
    public RzrqAuditVo getRzrqAuditById(Integer id) throws Exception {
        return auditRecordMapper.getRzrqAuditById(id);
    }

    public Integer examineRzrqAudit(AuditRecord auditRecord) throws Exception {
        RzrqAuditVo rzrqAuditVo = getRzrqAuditById(auditRecord.getId());
        if(rzrqAuditVo.getInfoTypeid()==0){
            if(rzrqAuditVo.getOperationId() == 1 || rzrqAuditVo.getOperationId() == 2){
                if(auditRecord.getStatus() == 1){
                    convertRateService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }else if (auditRecord.getStatus() == 2){
                    convertRateService.setstateByIds(rzrqAuditVo.getStaffId(),"3");
                }
            }else if(rzrqAuditVo.getOperationId() == 3 || rzrqAuditVo.getOperationId() == 4 || rzrqAuditVo.getOperationId() == 8){
                if(auditRecord.getStatus() == 1){
                    convertRateService.setstateByIds(rzrqAuditVo.getStaffId(),"7");
                }else if (auditRecord.getStatus() == 2){
                    convertRateService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }
            }else if(rzrqAuditVo.getOperationId() == 16){
                if(auditRecord.getStatus() == 1){
                    convertRateService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                    convertRateService.setstateByIds(rzrqAuditVo.getFormerId(),"7");
                }else if (auditRecord.getStatus() == 2){
                    convertRateService.setstateByIds(rzrqAuditVo.getStaffId(),"7");
                    convertRateService.setstateByIds(rzrqAuditVo.getFormerId(),"2");
                }
            }
        }else if(rzrqAuditVo.getInfoTypeid()==1){
            if(rzrqAuditVo.getOperationId() == 1 || rzrqAuditVo.getOperationId() == 2){
                if(auditRecord.getStatus() == 1){
                    bondBdService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }else if (auditRecord.getStatus() == 2){
                    bondBdService.setstateByIds(rzrqAuditVo.getStaffId(),"3");
                }
            }else if(rzrqAuditVo.getOperationId() == 3 || rzrqAuditVo.getOperationId() == 4 || rzrqAuditVo.getOperationId() == 8){
                if(auditRecord.getStatus() == 1){
                    bondBdService.setstateByIds(rzrqAuditVo.getStaffId(),"7");
                }else if (auditRecord.getStatus() == 2){
                    bondBdService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }
            }else if(rzrqAuditVo.getOperationId() == 16){
                if(auditRecord.getStatus() == 1){
                    bondBdService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                    bondBdService.setstateByIds(rzrqAuditVo.getFormerId(),"7");
                }else if (auditRecord.getStatus() == 2){
                    bondBdService.setstateByIds(rzrqAuditVo.getStaffId(),"7");
                    bondBdService.setstateByIds(rzrqAuditVo.getFormerId(),"2");
                }
            }
        }else if(rzrqAuditVo.getInfoTypeid()==2){
            if(rzrqAuditVo.getOperationId() == 1){
                if(auditRecord.getStatus() == 1){
                    interestRateService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }else if (auditRecord.getStatus() == 2){
                    interestRateService.setstateByIds(rzrqAuditVo.getStaffId(),"3");
                }
            }else if(rzrqAuditVo.getOperationId() == 3){
                if(auditRecord.getStatus() == 1){
                    interestRateService.setstateByIds(rzrqAuditVo.getStaffId(),"7");
                }else if (auditRecord.getStatus() == 2){
                    interestRateService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }
            }
        }else if(rzrqAuditVo.getInfoTypeid()==3){
            if(rzrqAuditVo.getOperationId() == 1){
                if(auditRecord.getStatus() == 1){
                    warrantRatioService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }else if (auditRecord.getStatus() == 2){
                    warrantRatioService.setstateByIds(rzrqAuditVo.getStaffId(),"3");
                }
            }else if(rzrqAuditVo.getOperationId() == 3){
                if(auditRecord.getStatus() == 1){
                    warrantRatioService.setstateByIds(rzrqAuditVo.getStaffId(),"7");
                }else if (auditRecord.getStatus() == 2){
                    warrantRatioService.setstateByIds(rzrqAuditVo.getStaffId(),"2");
                }
            }
        }
        return auditRecordMapper.updateAuditRecord(auditRecord);
    }
}
