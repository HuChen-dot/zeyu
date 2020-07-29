package com.ruoyi.swhysc.controller;

import com.ruoyi.swhysc.bean.dto.frontdto.front_ChannelDto;
import com.ruoyi.swhysc.bean.vo.ChannelVO;
import com.ruoyi.swhysc.bean.vo.ResultsSet;
import com.ruoyi.swhysc.config.RequestUrlconfig;
import com.ruoyi.swhysc.util.ResultSetUtil;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 频道控制层
 */
@RestController
@RequestMapping(RequestUrlconfig.CHANNELCONTROLLER_URL)
public class ChannelController {

    @Resource
    com.ruoyi.swhysc.service.channel.ChannelService ChannelService;

    /**
     * 初始化频道信息，返回分类好的频道信息
     */
    @RequestMapping("initChannel")
    public ResultsSet<List<ChannelVO>> initChannel() {
        List<ChannelVO> channelVO = null;
        try {
            channelVO = ChannelService.initChann(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }
        return ResultSetUtil.returnSuccess("操作成功", channelVO);
    }

    /**
     * 增加频道信息，返回是否增加成功
     */
    @RequestMapping("insertChannel")
    public ResultsSet insertChannel(@RequestBody front_ChannelDto ChannelDto) {
        try {
            ChannelService.AddChannel(ChannelDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }
        return ResultSetUtil.returnSuccess("操作成功");
    }

}
