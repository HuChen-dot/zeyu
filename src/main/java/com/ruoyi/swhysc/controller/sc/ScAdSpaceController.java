package com.ruoyi.swhysc.controller.sc;

import com.ruoyi.swhysc.bean.Advertise;
import com.ruoyi.swhysc.mapper.dao.AdvertiseMapper;
import com.ruoyi.swhysc.util.AjaxResult;
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

    @Resource
    AdvertiseMapper AdvertiseMapper;

    /**
     * 根据广告位id，查询广告信息列表
     */
    @GetMapping("/{id}")
    public AjaxResult getAdSpaceByPaid(@PathVariable Integer id) {
        List<Advertise> advertiseList = null;
        try {
            Map<String, Object> map = new HashMap<>(1);
            map.put("parentId", id);
            advertiseList = AdvertiseMapper.getAdvertiseListByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", advertiseList);
    }
}
