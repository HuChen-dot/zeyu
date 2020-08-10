package com.rewin.swhysc.controller.sc;

import com.rewin.swhysc.bean.Advertise;
import com.rewin.swhysc.bean.vo.ScAdvertiseVo;
import com.rewin.swhysc.mapper.dao.AdvertiseMapper;
import com.rewin.swhysc.service.AdSpaceService;
import com.rewin.swhysc.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/swhysc/advertise")
@RestController
public class ScAdSpaceController {
    private static final Logger log = LoggerFactory.getLogger(ScAdSpaceController.class);
    @Resource
    AdSpaceService AdSpaceService;

    /**
     * 根据广告位id，查询广告信息列表
     */
    @GetMapping("/{id}")
    public AjaxResult getAdSpaceByPaid(@PathVariable Integer id) {
        List<ScAdvertiseVo> scAdvertiseVos = null;
        try {
            scAdvertiseVos = AdSpaceService.scgetAdSpaceByPaid(id);
        } catch (Exception e) {
            log.error("查询出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", scAdvertiseVos);
    }
}
