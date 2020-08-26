package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.AdSpace;
import com.rewin.swhysc.bean.Advertise;
import com.rewin.swhysc.bean.dto.AddAdvertiseDto;
import com.rewin.swhysc.bean.vo.AdvertiseDetailVo;
import com.rewin.swhysc.bean.vo.AdvertiseVo;
import com.rewin.swhysc.bean.vo.viewOrAdVo;
import com.rewin.swhysc.mapper.dao.AdvertiseMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.AdSpaceService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 广告位控制层
 */
@RestController
@RequestMapping("/swhyscmanage/advertise")
public class AdSpaceController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AdSpaceController.class);
    @Resource
    AdSpaceService AdSpaceService;

    @Resource
    AdvertiseMapper AdvertiseMapper;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;


    /**
     * 根据parentId查询相应的数据字典(页面名称和页面广告位)
     */
    @GetMapping("space/{id}")
    public AjaxResult getAdSpace(@PathVariable Integer id) {
        id = id == null ? 0 : id;
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put("parentId", id);
        List<viewOrAdVo> list = new ArrayList<>();
        try {
            List<AdSpace> adSpaceListByMap = AdSpaceService.getAdSpaceListByMap(map);
            //开始转换复制数据
            if (adSpaceListByMap != null) {
                for (AdSpace adSpace : adSpaceListByMap) {
                    viewOrAdVo viewOrAdVo = new viewOrAdVo();
                    BeanUtils.copyProperties(adSpace, viewOrAdVo);
                    list.add(viewOrAdVo);
                }
            }
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.success("sql错误");
        }
        return AjaxResult.success("查询成功", list);
    }


    /**
     * 根据广告位id查询该广告位的图片大小
     */
    @GetMapping("imgsize/{id}")
    public AjaxResult getImgSize(@PathVariable Integer id) {
        id = id == null ? 0 : id;
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put("id", id);
        viewOrAdVo viewOrAdVo = null;
        try {
            List<AdSpace> adSpaceListByMap = AdSpaceService.getAdSpaceListByMap(map);
            //开始转换复制数据
            if (adSpaceListByMap != null) {
                for (AdSpace adSpace : adSpaceListByMap) {
                    viewOrAdVo = new viewOrAdVo();
                    BeanUtils.copyProperties(adSpace, viewOrAdVo);
                }
            }
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.success("sql错误");
        }
        return AjaxResult.success("查询成功", viewOrAdVo);
    }

    /**
     * 根据广告位id，分页查询广告信息列表
     */
    @GetMapping("list")
    public AjaxResult getAdSpaceByPaid(Integer id, Integer pageNum, Integer pageSize) {
        if (id == null) {
            return AjaxResult.error("广告位名称不能为空");
        }
        PageInfo<AdvertiseVo> adSpaceByPaid = null;
        try {
            adSpaceByPaid = AdSpaceService.getAdSpaceByPaid(id, pageNum, pageSize);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询错误，请重试");
        }
        return AjaxResult.success("查询成功", adSpaceByPaid);
    }


    /**
     * 根据广告id查询广告详细信息
     * 用来修改广告前的初始化工作
     */
    @GetMapping("info/{id}")
    public AjaxResult getAdvertiseByid(@PathVariable Integer id) {
        AdvertiseDetailVo advertise = null;
        try {
            advertise = AdSpaceService.getAdvertiseByid(id);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("查询错误，请重试");
        }
        return AjaxResult.success("查询成功", advertise);
    }


    /**
     * 添加广告
     */
    @PostMapping()
    public AjaxResult addAdvertise(@RequestBody AddAdvertiseDto AdverDto) {
        System.err.println("添加：" + AdverDto);
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        Advertise adse = new Advertise();
        BeanUtils.copyProperties(AdverDto, adse);
        adse.setOrderNo(AdverDto.getOrderNo() == null ? 1 : AdverDto.getOrderNo());
        adse.setTitle(AdverDto.getTitle().length() <= 0 ? " " : AdverDto.getTitle());
        adse.setPath(AdverDto.getPath() == null ? " " : AdverDto.getPath());
        adse.setCreator(loginUser.getUsername());
        adse.setCreateTime(DateUtils.dateTimes(new Date()));
        adse.setUpdateTime(DateUtils.dateTimes(new Date()));
        try {
            AdvertiseMapper.insertAdvertise(adse);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("添加错误，请重试");
        }
        return AjaxResult.success("添加成功");
    }


    /**
     * 修改广告
     */
    @PutMapping
    public AjaxResult updeAdvertise(@RequestBody AddAdvertiseDto AdverDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        Advertise adse = new Advertise();
        BeanUtils.copyProperties(AdverDto, adse);
        adse.setUpdater(loginUser.getUsername());
        adse.setUpdateTime(DateUtils.dateTimes(new Date()));
        try {
            AdvertiseMapper.updateAdvertise(adse);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("修改错误，请重试");
        }
        return AjaxResult.success("修改成功");
    }


    /**
     * 删除广告
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleAdvertise(@PathVariable Integer id) {
        Advertise adse = new Advertise();
        adse.setId(id);
        adse.setStatus(1);
        try {
            AdvertiseMapper.updateAdvertise(adse);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("删除错误，请重试");
        }
        return AjaxResult.success("删除成功");
    }
}