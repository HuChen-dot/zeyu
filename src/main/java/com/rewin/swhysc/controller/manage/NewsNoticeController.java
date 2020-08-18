package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.dto.AddNewsDto;
import com.rewin.swhysc.bean.dto.newsDto;
import com.rewin.swhysc.bean.vo.UpdataNewsVo;
import com.rewin.swhysc.bean.vo.newsVo;
import com.rewin.swhysc.service.NewsNoticeService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻公告控制层
 */
@RestController
@RequestMapping("/swhyscmanage/news")
public class NewsNoticeController {
    private static final Logger log = LoggerFactory.getLogger(NewsNoticeController.class);
    @Resource
    NewsNoticeService NewsNoticeService;

    /**
     * 查询：根据传入的条件，分页查询新闻信息列表
     */
    @GetMapping("list")
    public AjaxResult getnewsBylist(newsDto newsDto) {
        System.err.println("news阐述：" + newsDto);
        Map<String, Object> map = new HashMap<>(4);
        if (newsDto.getNoticeTypeId() != null && newsDto.getNoticeTypeId() != 0) {
            map.put("noticeTypeId", newsDto.getNoticeTypeId());
        }
        if (newsDto.getStatus() != null && !newsDto.getStatus().equals("0")) {
            map.put("status", newsDto.getStatus());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        map.put("noticeTitle", newsDto.getNoticeTitle());
        Date beginTime = null;
        Date endTime = null;
        if (newsDto.getBeginTime() != null && newsDto.getEndTime() != null &&
                newsDto.getBeginTime() != "" && newsDto.getEndTime() != "") {
            try {
                beginTime = dateFormat.parse(newsDto.getBeginTime());
                endTime = dateFormat.parse(newsDto.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return AjaxResult.error("日期转换异常");
            }
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);

        }
        map.put("pageNum", newsDto.getPageNum());
        map.put("pageSize", newsDto.getPageSize());
        PageInfo<newsVo> info = null;
        try {
            info = NewsNoticeService.getNewsListByMap(map);
        } catch (Exception e) {
            log.error("查询出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", info);
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
        System.err.println("添加对象：" + AddNewsDto);
        AddNewsDto.setType("HTML");
        AddNewsDto.setNewsContent("测试");
        AddNewsDto.setAccessoryName("www.");
        AddNewsDto.setAccessoryPath("www.");

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
        System.err.println("修改对象：" + AddNewsDto);
        if (AddNewsDto.getStatus().equals("16")) {
            return AjaxResult.error("该条新闻已被删除，不能修改");
        }
        if (AddNewsDto.getStatus().equals("2")) {
            return AjaxResult.error("该条新闻已发布，不能修改");
        }
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
