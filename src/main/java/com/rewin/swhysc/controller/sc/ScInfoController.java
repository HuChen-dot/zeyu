package com.rewin.swhysc.controller.sc;

import com.rewin.swhysc.bean.dto.MarketerDto;
import com.rewin.swhysc.bean.dto.UserMsgDto;
import com.rewin.swhysc.bean.vo.MarketerInfoVo;
import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.service.ScStaffInfoService;
import com.rewin.swhysc.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AjaxResult insertUserOnlineMsg(@RequestBody @Valid UserMsgDto userMsgDto){
        try{
            scStaffInfoService.insertUserOnlineMsg(userMsgDto);
        }catch (Exception e){
            e.printStackTrace();
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
    public AjaxResult getMarketerInfo(@RequestBody @Valid MarketerDto marketerDto){
        List<MarketerInfoVo> list;
        try{
            list = scStaffInfoService.getMarketerInfo(marketerDto);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
        return AjaxResult.success(list);
    }

}
