package com.ruoyi.swhysc.controller;

import com.ruoyi.swhysc.bean.Infocontent;
import com.ruoyi.swhysc.bean.vo.ResultsSet;
import com.ruoyi.swhysc.util.ResultSetUtil;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 信息表下的正文信息表
 */
@RestController
@RequestMapping("/infocontentController")
public class InfocontentController {

    @Resource
    com.ruoyi.swhysc.service.infocontent.InfocontentService InfocontentService;

    /**
     * 根据信息表id查询信息正文
     */
    @RequestMapping("gettextbyid/{id}")
    public ResultsSet getTextById(@PathVariable Integer id) {
        Infocontent infocontentById = null;
        try {
            infocontentById = InfocontentService.getInfocontentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }

        return ResultSetUtil.returnSuccess("操作成功", infocontentById);
    }
}
