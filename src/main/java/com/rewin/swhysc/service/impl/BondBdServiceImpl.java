package com.rewin.swhysc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.AuditRecord;
import com.rewin.swhysc.bean.BondBd;
import com.rewin.swhysc.bean.vo.BondBdVo;
import com.rewin.swhysc.bean.vo.FileName;
import com.rewin.swhysc.common.exception.CustomException;
import com.rewin.swhysc.common.exception.file.InvalidExtensionException;
import com.rewin.swhysc.mapper.dao.BondBdMapper;
import com.rewin.swhysc.service.AuditRecordService;
import com.rewin.swhysc.service.BondBdService;
import com.rewin.swhysc.util.StringUtils;
import com.rewin.swhysc.util.file.FileUploadUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 融资融卷专栏------标的保证金
 */
@Service
public class BondBdServiceImpl implements BondBdService {
    @Resource
    private BondBdMapper bondBdMapper;

    private static final Logger log = LoggerFactory.getLogger(NotOpenStaffServiceImpl.class);

    @Resource
    AuditRecordService auditRecordService;

    @Override
    public PageInfo<BondBdVo> getBondBdList(Integer pageNo, Integer pageSize, String stockCode, String stockName, String trimDate) throws Exception {
       //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);

        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(trimDate)){
            map.put("trimDate", trimDate+" 00:00:00");
        }
        List<BondBdVo> bondBdList = bondBdMapper.getBondBdList(map);

        PageInfo<BondBdVo> info = new PageInfo<BondBdVo>();
        info.setPageSize(objects.getPageSize());
        info.setPageNum(objects.getPageNum());
        info.setPages(objects.getPages());
        info.setTotal(objects.getTotal());
        info.setData(bondBdList);
        return info;
    }

    @Override
    public List<BondBdVo> getBondBdList(String stockCode, String stockName, String trimDate) throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(trimDate)){
            map.put("trimDate", trimDate+" 00:00:00");
        }
        List<BondBdVo> bondBdList = bondBdMapper.getAllBondBd(map);

        return bondBdList;
    }

    @Override
    public List<BondBdVo> getBondBdState(String stockCode, String stockName, String trimDate) throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        if(!StringUtils.isEmpty(stockCode)){
            map.put("stockCode", stockCode);
        }
        if(!StringUtils.isEmpty(stockName)){
            map.put("stockName", stockName);
        }
        if(!StringUtils.isEmpty(trimDate)){
            map.put("trimDate", trimDate+" 00:00:00");
        }

        List<BondBdVo> bondBdList = bondBdMapper.getBondBdState(map);

        return bondBdList;
    }

    @Override
    public BondBd getBondBdInfo(String id) throws Exception {
        if(!StringUtils.isEmpty(id)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("id", id);
            return bondBdMapper.getBondBdInfo(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertBondBd(BondBd bondBd) throws Exception {
        return bondBdMapper.insertBondBd(bondBd);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String insertBondList(List<BondBd> bondBdList, String operName, MultipartFile[] file) throws Exception {
        if (StringUtils.isNull(bondBdList) || bondBdList.size() == 0) {
            throw new CustomException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<Integer> count = new ArrayList<>();
        for (BondBd bondBd : bondBdList) {
            try {
                bondBdMapper.insertBondBd(bondBd);
                successNum++;
                successMsg.append("<br/>" + successNum + "、证券代码 " + bondBd.getStockCode() + " 导入成功");
                count.add(bondBd.getId());
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、证券代码 " + bondBd.getStockCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
                throw new RuntimeException();
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            //封装参数，向中间表插入数据
            AuditRecord AuditRecord = new AuditRecord();
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < count.size(); j++)
                if (j < count.size() - 1) {
                    builder.append(count.get(j) + ",");
                } else {
                    builder.append(count.get(j));
                }
            //此处文件上传开始
            FileName fileName = null;
            try {
                fileName = FileUploadUtils.upload(file);
            } catch (IOException e) {
                e.printStackTrace();
                failureMsg.insert(0, "很抱歉，文件上传失败！请重新上传");
            } catch (InvalidExtensionException e) {
                e.printStackTrace();
            }
//        文件上传结束----------------------------------------------
            AuditRecord.setFileName(fileName.getFileName());
            AuditRecord.setFileUrl(fileName.getRandomName());
            AuditRecord.setStaffId(builder.toString());
            AuditRecord.setInfoTypeid(1);
            AuditRecord.setOperationId(2);
            AuditRecord.setFlowType(1);
            AuditRecord.setStatus(0);
            AuditRecord.setSubmitter(operName);
            AuditRecord.setSubmitTime(new Date());
            try {
                auditRecordService.AddAuditRecord(AuditRecord);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入中间表失败", e);
                throw new RuntimeException();
            }
        }
        return successMsg.toString();
    }

    @Override
    public Integer updateBondBd(BondBd bondBd) throws Exception {
        return bondBdMapper.updateBondBd(bondBd);
    }

    @Override
    public Integer subDelApproval(String ids) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            return bondBdMapper.subDelApproval(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer delByIds(String ids) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            return bondBdMapper.delByIds(param);
        }else{
            return null;
        }
    }

    @Override
    public Integer setstateByIds(String ids,String state) throws Exception {
        if(!StringUtils.isEmpty(ids)){
            Map<String, Object> param = new HashMap<>(1);
            param.put("ids", ids);
            param.put("state", state);
            return bondBdMapper.setstateByIds(param);
        }else{
            return null;
        }
    }
}
