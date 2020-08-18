package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.dto.AddNewsDto;
import com.rewin.swhysc.bean.dto.newsDto;
import com.rewin.swhysc.bean.vo.UpdataNewsVo;
import com.rewin.swhysc.bean.vo.newsVo;
import com.rewin.swhysc.service.NewsNoticeService;
import com.rewin.swhysc.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swhyscmanage/news")
public class NewsNoticeController {
    private static final Logger log = LoggerFactory.getLogger(NewsNoticeController.class);
    @Resource
    NewsNoticeService NewsNoticeService;

    /**
     * 查询：根据传入的条件，查询新闻信息
     */
    @GetMapping("list")
    public AjaxResult getnewsBylist(@RequestBody newsDto newsDto) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("noticeTitle", newsDto.getNoticeTitle());
        map.put("noticeTypeId", newsDto.getNoticeTypeId());
        map.put("status", newsDto.getStatus());
        map.put("updateTime", newsDto.getCreateTime());
        List<newsVo> newsList = null;
        try {
            newsList = NewsNoticeService.getNewsListByMap(map);
        } catch (Exception e) {
            log.error("查询出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", newsList);
    }

    /**
     * 查询：修改前，根据新闻id 查询新闻的详细信息
     */
    @GetMapping("/{id}")
    public AjaxResult getnewsByid(@PathVariable Integer id) {

        Map<String, Object> map = new HashMap<>(4);

        UpdataNewsVo newslistById = null;
        try {
            newslistById = NewsNoticeService.getNewslistById(id);
        } catch (Exception e) {
            log.error("查询出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", newslistById);
    }

    /**
     * 增加
     */
    @PostMapping
    public AjaxResult addNews(@RequestBody AddNewsDto AddNewsDto) {
        Integer integer = null;
        try {
            integer = NewsNoticeService.AddNewsNotice(AddNewsDto);
        } catch (Exception e) {
            log.error("添加出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功", integer);
    }

    /**
     * 修改
     */
    @PutMapping
    public AjaxResult updataNews(@RequestBody AddNewsDto AddNewsDto) {
        Integer integer = null;
        try {
            integer = NewsNoticeService.ModifyNewsNotice(AddNewsDto);
        } catch (Exception e) {
            log.error("修改出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("修改成功", integer);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public AjaxResult delNews(@PathVariable Integer id) {
        Integer integer = null;
        try {
            integer = NewsNoticeService.DeleteNewsNoticeById(id);
        } catch (Exception e) {
            log.error("删除出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功", integer);
    }

}
