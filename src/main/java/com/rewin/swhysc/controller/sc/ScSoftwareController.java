package com.rewin.swhysc.controller.sc;

import com.rewin.swhysc.bean.dto.DownloadCountDto;
import com.rewin.swhysc.bean.vo.SoftwareInfoForScVo;
import com.rewin.swhysc.common.constant.ExceptionCode;
import com.rewin.swhysc.common.utils.ExceptionMsgUtils;
import com.rewin.swhysc.service.SoftwareService;
import com.rewin.swhysc.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


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
    private SoftwareService softwareService;

    /**
     * @Description:软件下载信息列表查询
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/24 15:24
     */
    @ApiOperation("软件下载信息查询")
    @GetMapping("/infolist")
    public AjaxResult getSoftwareInfoList(@RequestParam @NotBlank Integer type) {
        List<SoftwareInfoForScVo> list;
        try {
            list =  softwareService.getSoftwareInfoForSc(type);
        }catch (Exception e){
            log.error("软件下载信息查询失败",e);
            return AjaxResult.error(exceptionMsgUtils.getExecptionMsg(ExceptionCode.QUERY_SOFTWAREINFO_EXCEPTION));
        }
        return AjaxResult.success(list);
    }

    /**
     * @Description:软件下载次数记录
     * @Param:
     * @return:
     * @Author: sinan@rewin.com.cn
     * @Date: 2020/8/26 14:30
     */
    @ApiOperation("软件下载次数记录")
    @PostMapping("/downloadcount")
    public AjaxResult insertSoftwareDownloadCount(@RequestBody @Valid DownloadCountDto downloadCountDto){
        try{
            softwareService.insertSoftwareDownloadCount(downloadCountDto);
        }catch (Exception e){
            log.error("记录软件下载次数失败",e);
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }
}
