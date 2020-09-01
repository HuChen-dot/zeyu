package com.rewin.swhysc.service.impl;

import com.rewin.swhysc.bean.BondInvestment;
import com.rewin.swhysc.bean.NotOpenStaff;
import com.rewin.swhysc.bean.dto.BondinvestmentDto;
import com.rewin.swhysc.bean.dto.MarketerDto;
import com.rewin.swhysc.bean.dto.OpenAccStaffDto;
import com.rewin.swhysc.bean.dto.UserMsgDto;
import com.rewin.swhysc.bean.pojo.Marketer;
import com.rewin.swhysc.bean.pojo.OpenDept;
import com.rewin.swhysc.bean.pojo.UserMsg;
import com.rewin.swhysc.bean.vo.*;
import com.rewin.swhysc.common.constant.BusinessConstants;
import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.common.utils.bean.BeanUtils;
import com.rewin.swhysc.dao.ScInfoDao;
import com.rewin.swhysc.mapper.dao.UserMsgMapper;
import com.rewin.swhysc.service.ScStaffInfoService;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: swhyscManageServer
 * @description:
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/26 16:47
 **/
@Service
public class ScStaffInfoServiceImpl implements ScStaffInfoService {
    private static final Logger log = LoggerFactory.getLogger(ScStaffInfoServiceImpl.class);

    @Autowired
    private ExceptionMsgUtils exceptionMsgUtils;

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private ScInfoDao scInfoDao;

    @Override
    public Integer insertUserOnlineMsg(UserMsgDto userMsgDto) throws Exception {
        UserMsg userMsg = new UserMsg();
        BeanUtils.copyBeanProp(userMsg, userMsgDto);
        return userMsgMapper.insertSelective(userMsg);
    }

    @Override
    public MarketerInfoVo getMarketerInfo(MarketerDto marketerDto) throws Exception {
        List<MarketerInfo> dateList = new ArrayList<>();
        MarketerInfoVo marketerInfoVo = new MarketerInfoVo();
        //查询西部营业部列表
        List<OpenDept> openDepts = scInfoDao.getOpenDeptList();
        //存储截取好的西部营业部
        Set<String> westCode = new HashSet<>();
        if (openDepts.size() > 0) {
            for (OpenDept openDept : openDepts) {
                String shortOpenDept = openDept.getOpenDept().substring(openDept.getOpenDept().length() - 4);
                westCode.add(shortOpenDept);
            }
        }
        //分页查询营销人员信息
        PageInfo<Marketer> getMarketerInfoList = scInfoDao.getMarketerInfoList(marketerDto.getCompanyType(),
                marketerDto.getStaffType(),
                marketerDto.getSearchKey(),
                westCode,
                marketerDto.getPageNum(),
                marketerDto.getPageSize());

        marketerInfoVo.setTotal(getMarketerInfoList.getPages());
        marketerInfoVo.setPageNum(getMarketerInfoList.getPageNum());
        marketerInfoVo.setPageSize(getMarketerInfoList.getPageSize());
        List<Marketer> marketers = getMarketerInfoList.getData();
        //信息处理
        if (marketers.size() > 0) {
            for (Marketer marketer : marketers) {
                MarketerInfo marketerInfo = new MarketerInfo();
                marketerInfo.setName(marketer.getJjrxm());
                marketerInfo.setNumber(marketer.getJjrbh());
                marketerInfo.setAgentTime(marketer.getHtqj());
                marketerInfo.setCertificate(marketer.getCertNo());
                marketerInfo.setBranchName(marketer.getYybName());
                marketerInfo.setWorkAddress(marketer.getHdfw());
                dateList.add(marketerInfo);
            }
        }
        marketerInfoVo.setMarketerInfos(dateList);
        return marketerInfoVo;
    }

    @Override
    public OpenAccStaffVo getOpenAccStaffInfoList(OpenAccStaffDto openAccStaffDto) throws Exception {
        List<OpenAccStaff> openAccStaffs = new ArrayList<>();
        OpenAccStaffVo openAccStaffVo = new OpenAccStaffVo();
        //查询非现场开户人员信息
        PageInfo<NotOpenStaff> openAccStaffInfoList = scInfoDao.getOpenAccStaffInfoList(openAccStaffDto);
        openAccStaffVo.setTotal(openAccStaffInfoList.getPages());
        openAccStaffVo.setPageNum(openAccStaffInfoList.getPageNum());
        openAccStaffVo.setPageSize(openAccStaffInfoList.getPageSize());
        List<NotOpenStaff> notOpenStaffs = openAccStaffInfoList.getData();
        //数据处理
        if (notOpenStaffs.size() > 0) {
            for (NotOpenStaff notOpenStaff : notOpenStaffs) {
                OpenAccStaff openAccStaff = new OpenAccStaff();
                openAccStaff.setBranch(notOpenStaff.getSales());//营业部
                openAccStaff.setCertificate(notOpenStaff.getCertificateNo());//证书编号
                openAccStaff.setName(notOpenStaff.getStaffName());//员工姓名
                openAccStaff.setNumber(notOpenStaff.getStaffNo());//员工编号
                openAccStaff.setStaffType(notOpenStaff.getPersonnelType());//非现场开户人员类别
                openAccStaffs.add(openAccStaff);
            }
        }
        openAccStaffVo.setOpenAccStaffs(openAccStaffs);
        return openAccStaffVo;
    }

    @Override
    public BondInvestmentInfoVo getBondInvestmentInfoList(BondinvestmentDto bondinvestmentDto) throws Exception {
        List<BondInvestmentInfo> bondInvestmentInfos = new ArrayList<>();
        BondInvestmentInfoVo bondInvestmentInfoVo = new BondInvestmentInfoVo();
        //债券投资相关人员信息查询
        //字典值转换
        switch (bondinvestmentDto.getStaffSort()) {
            case BusinessConstants.SELF_OPERATED_NUM:
                bondinvestmentDto.setStaffSort(BusinessConstants.SELF_OPERATED_CN);
                break;
            case BusinessConstants.ASSET_MANAGEMENT_NUM:
                bondinvestmentDto.setStaffSort(BusinessConstants.ASSET_MANAGEMENT_CN);
                break;
            case BusinessConstants.CHECK_STAFF_NUM:
                bondinvestmentDto.setStaffSort(BusinessConstants.CHECK_STAFF_CN);
                break;
            case BusinessConstants.WITHDRAWN_NUM:
                bondinvestmentDto.setStaffSort(BusinessConstants.WITHDRAWN_CN);
                break;
        }
        //数据库查询债券人员相关信息
        PageInfo<BondInvestment> bondInvestmentInfoList = scInfoDao.getBondInvestmentInfoList(bondinvestmentDto);
        bondInvestmentInfoVo.setTotal(bondInvestmentInfoList.getPages());
        bondInvestmentInfoVo.setPageNum(bondInvestmentInfoList.getPageNum());
        bondInvestmentInfoVo.setPageSize(bondInvestmentInfoList.getPageSize());
        List<BondInvestment> data = bondInvestmentInfoList.getData();
        if (data.size() > 0) {
            for (BondInvestment bondInvestment : data){
                BondInvestmentInfo bondInvestmentInfo = new BondInvestmentInfo();
            }
        }
        return null;
    }
}
