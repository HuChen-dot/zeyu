package com.rewin.swhysc.controller.sc;

import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: swhyscManageServer
 * @description:前台软件信息查询
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/24 16:14
 **/
@RequestMapping("/swhysc/software")
@RestController
public class ScSoftwareController {
    private static final Logger log = LoggerFactory.getLogger(ScSoftwareController.class);

    @Autowired
    private ExceptionMsgUtils exceptionMsgUtils;

    @Autowired
    private SoftwareService softwareServiceImpl;

    /**
     * @Description:软件下载信息列表查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/24 15:24
     */
    @ApiOperation("软件下载信息查询")
    @PostMapping("/infolist")
    public AjaxResult getSoftwareInfoList(@RequestBody Integer type) throws Exception {
        System.out.println(exceptionMsgUtils.getExecptionMsg("-006101"));
        softwareServiceImpl.getSoftwareInfoForSc(type);
        return null;
    }
}
