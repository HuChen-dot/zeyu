package com.rewin.swhysc.controller.sc;

import com.rewin.swhysc.bean.dto.BondinvestmentDto;
import com.rewin.swhysc.bean.dto.MarketerDto;
import com.rewin.swhysc.bean.dto.OpenAccStaffDto;
import com.rewin.swhysc.bean.dto.UserMsgDto;
import com.rewin.swhysc.bean.vo.BondInvestmentInfoVo;
import com.rewin.swhysc.bean.vo.MarketerInfoVo;
import com.rewin.swhysc.bean.vo.OpenAccStaffVo;
import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.service.ScStaffInfoService;
import com.rewin.swhysc.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @program: swhyscManageServer
 * @description:前台用户操作
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/24 16:14
 **/
@RequestMapping("/swhysc/info")
@RestController
public class ScInfoController {
    private static final Logger log = LoggerFactory.getLogger(ScInfoController.class);

    @Autowired
    private ExceptionMsgUtils exceptionMsgUtils;

    @Autowired
    private ScStaffInfoService scStaffInfoService;

    @ApiOperation("在线留言信息保存")
    @PostMapping("/onlinemsg")
    /**
     * @Description:在线留言信息保存
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/26 16:42
     */
    public AjaxResult insertUserOnlineMsg(@RequestBody @Valid UserMsgDto userMsgDto) {
        try {
            scStaffInfoService.insertUserOnlineMsg(userMsgDto);
        } catch (Exception e) {
            log.error("保存留言信息失败", e);
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    /**
     * @Description:营销人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/27 14:07
     */
    @ApiOperation("营销人员信息查询")
    @PostMapping("/marketer")
    public AjaxResult getMarketerInfo(@RequestBody @Valid MarketerDto marketerDto) {
        MarketerInfoVo marketerInfoVo;
        try {
            marketerInfoVo = scStaffInfoService.getMarketerInfo(marketerDto);
        } catch (Exception e) {
            log.error("查询营销人员信息失败", e);
            return AjaxResult.error();
        }
        return AjaxResult.success(marketerInfoVo);
    }

    /**
     * @Description:非现场开户人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/31 16:09
     */
    @ApiOperation("非现场开户人员查询")
    @GetMapping("/openacc")
    public AjaxResult getOpenAccStaffInfoList(@ModelAttribute @Valid OpenAccStaffDto openAccStaffDto) {
        OpenAccStaffVo openAccStaffVo;
        try {
            openAccStaffVo = scStaffInfoService.getOpenAccStaffInfoList(openAccStaffDto);
        } catch (Exception e) {
            log.error("查询非现场开户人员信息失败", e);
            return AjaxResult.error();
        }
        return AjaxResult.success(openAccStaffVo);
    }

    /**
     * @Description:债券投资相关人员信息查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/9/1 10:31
     */
    @ApiOperation("债券投资相关人员信息查询")
    @GetMapping("/bondinvestment")
    public AjaxResult getBondInvestmentInfoList(@ModelAttribute @Valid BondinvestmentDto bondinvestmentDto) {
        BondInvestmentInfoVo bondInvestmentInfoVo;
        try {
            bondInvestmentInfoVo = scStaffInfoService.getBondInvestmentInfoList(bondinvestmentDto);
        } catch (Exception e) {
            log.error("查询债券投资相关人员信息失败", e);
            return AjaxResult.error();
        }
        return AjaxResult.success(bondInvestmentInfoVo);
    }

}
