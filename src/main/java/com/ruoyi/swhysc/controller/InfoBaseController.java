package com.ruoyi.swhysc.controller;

import com.ruoyi.swhysc.bean.dto.frontdto.front_InfoDto;
import com.ruoyi.swhysc.bean.vo.InfobaseVO;
import com.ruoyi.swhysc.bean.vo.ResultsSet;
import com.ruoyi.swhysc.config.RequestUrlconfig;
import com.ruoyi.swhysc.util.ResultSetUtil;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信息表
 */
@RestController
@RequestMapping(RequestUrlconfig.INFOCONTENTCONTROLLER_URL)
public class InfoBaseController {

    @Autowired
    com.ruoyi.swhysc.service.infobase.InfobaseService InfobaseService;

    /**
     * 查询：根据频道id查询，对应频道下的所有信息
     */
    @RequestMapping("getInfobese/{channelId}")
    public ResultsSet<List<InfobaseVO>> getInfoBese(@PathVariable Integer channelId) {
        //判断参数是否正确
        if (channelId == null) {
            return ResultSetUtil.returnerror("参数错误", StateCode.PARAMETER.getCode());
        }
        // 声明参数并赋值
        Map<String, Object> map = new HashMap<>(1);
        map.put("channelid", channelId);
        List<InfobaseVO> infobaseListByMap = null;
        try {
            //调用逻辑层方法
            infobaseListByMap = InfobaseService.getInfobaseListByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }
        return ResultSetUtil.returnSuccess("操作成功", infobaseListByMap);
    }

    /**
     * 添加：添加信息表信息
     */
    /**
     * 根据频道id查询，对应频道下的所有信息
     */
    @RequestMapping("insertInfobese/{channelId}")
    public ResultsSet insertInfoBese(@RequestBody front_InfoDto InfoDto) {
        //判断参数是否正确
        //.。。。。。。。。。。。
        try {
            InfobaseService.AddInfobaseAndInfocontent(InfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }
        return ResultSetUtil.returnSuccess("操作成功");
    }

    /**
     * 根据信息表id删除信息表信息，并删除关联的正文表中的信息
     */
    @RequestMapping("insertInfobese/{id}")
    public ResultsSet deleInfoBese(@PathVariable Integer id) {
        try {
            InfobaseService.DeleteInfobaseById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }
        return ResultSetUtil.returnSuccess("操作成功");
    }
}
