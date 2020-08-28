package com.rewin.swhysc.service.impl;

import com.rewin.swhysc.bean.dto.MarketerDto;
import com.rewin.swhysc.bean.dto.UserMsgDto;
import com.rewin.swhysc.bean.pojo.OpenDept;
import com.rewin.swhysc.bean.pojo.UserMsg;
import com.rewin.swhysc.bean.vo.MarketerInfoVo;
import com.rewin.swhysc.common.constant.BusinessConstants;
import com.rewin.swhysc.common.constant.ExceptionCode;
import com.rewin.swhysc.common.exception.CustomException;
import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.common.utils.bean.BeanUtils;
import com.rewin.swhysc.dao.ScInfoDao;
import com.rewin.swhysc.framework.aspectj.lang.annotation.DataSource;
import com.rewin.swhysc.framework.aspectj.lang.enums.DataSourceType;
import com.rewin.swhysc.mapper.dao.OpenDeptMapper;
import com.rewin.swhysc.mapper.dao.UserMsgMapper;
import com.rewin.swhysc.service.ScStaffInfoService;
import org.apache.poi.ss.formula.functions.T;
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
    public List<MarketerInfoVo> getMarketerInfo(MarketerDto marketerDto) throws Exception {
        List<MarketerInfoVo> list = new ArrayList<>();
        try {
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

            //System.out.println(scInfoDao.getMarketerInfoList().get(0).getJjrxm());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
