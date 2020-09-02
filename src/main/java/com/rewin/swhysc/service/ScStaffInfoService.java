package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.dto.*;
import com.rewin.swhysc.bean.vo.BondInvestmentInfoVo;
import com.rewin.swhysc.bean.vo.MarketerInfoVo;
import com.rewin.swhysc.bean.vo.OpenAccStaffVo;
import com.rewin.swhysc.bean.vo.PrivateEquityStaffVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @program: swhyscManageServer
 * @description:前台用户操作
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/26 16:47
 **/
public interface ScStaffInfoService {
    /**
     * @Description:在线留言信息保存
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/26 16:57
     */
    Integer insertUserOnlineMsg(UserMsgDto userMsgDto) throws Exception;

    /**
     * @Description:营销人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/27 14:07
     */
    MarketerInfoVo getMarketerInfo(MarketerDto marketerDto) throws Exception;

    /**
     * @Description:非现场开户人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/31 16:18
     */
    OpenAccStaffVo getOpenAccStaffInfoList(OpenAccStaffDto openAccStaffDto) throws Exception;

    /**
     * @Description:债券投资相关人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/9/1 10:31
     */
    BondInvestmentInfoVo getBondInvestmentInfoList(BondinvestmentDto bondinvestmentDto) throws Exception;

    /**
     * @Description:私募资产管理业务从业人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/9/1 17:31
     */
    PrivateEquityStaffVo getPrivateEquityStaffInfoList(PrivateEquityStaffDto privateEquityStaffDto)throws Exception;
}
