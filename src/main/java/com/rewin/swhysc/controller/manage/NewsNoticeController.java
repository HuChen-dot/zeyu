package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.NewsNotice;
import com.rewin.swhysc.bean.dto.AddNewsDto;
import com.rewin.swhysc.bean.dto.VerifierDto;
import com.rewin.swhysc.bean.dto.newsDto;
import com.rewin.swhysc.bean.vo.UpdataNewsVo;
import com.rewin.swhysc.bean.vo.newsVo;
import com.rewin.swhysc.project.monitor.service.ISysJobService;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.security.service.TokenService;
import com.rewin.swhysc.service.NewsNoticeService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.file.FileUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysJobService jobService;

    /**
     * 查询：根据传入的条件，分页查询新闻信息列表
     */
    @GetMapping("list")
    public AjaxResult getnewsBylist(newsDto newsDto) {
        Map<String, Object> map = new HashMap<>(4);
        if (newsDto.getNoticeTypeId() != null && newsDto.getNoticeTypeId() != 0) {
            map.put("noticeTypeId", newsDto.getNoticeTypeId());
        }
        if (newsDto.getStatus() != null) {
            map.put("status", newsDto.getStatus());
        } else {
            map.put("status", '0');
        }
        if (newsDto.getFlow() != null) {
            map.put("flow", newsDto.getFlow());
        }

        map.put("noticeTitle", newsDto.getNoticeTitle());
        Date beginTime = null;
        Date endTime = null;
        if (newsDto.getBeginTime() != null && newsDto.getEndTime() != null &&
                newsDto.getBeginTime() != "" && newsDto.getEndTime() != "") {

            beginTime = DateUtils.parseDate(newsDto.getBeginTime());
            endTime = DateUtils.parseDate(newsDto.getEndTime());

            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
        }
        map.put("pageNum", newsDto.getPageNum());
        map.put("pageSize", newsDto.getPageSize());
        PageInfo<newsVo> info = null;
        try {
            info = NewsNoticeService.getNewsListByMap(map);
        } catch (Exception e) {
            log.error("查询失败", e);
            return AjaxResult.error("系统错误，请重试");
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
            log.error("查询失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("查询成功", newslistById);
    }

    /**
     * 增加
     */
    @PostMapping
    public AjaxResult addNews(@RequestBody AddNewsDto AddNewsDto) {
        System.err.println("添加：" + AddNewsDto);
        Integer integer = null;
        try {
            integer = NewsNoticeService.AddNewsNotice(AddNewsDto);
        } catch (Exception e) {
            log.error("添加失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("添加成功", integer);
    }

    /**
     * 审核新闻公告
     */
    @PostMapping("verifier")
    public AjaxResult verifierNews(@RequestBody VerifierDto VerifierDto) {

//        return AjaxResult.error("系统错误，请重试");
        //获得当前登录对象
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Integer integer = null;
        try {
            //把前台的参数封装进数据库传输对象中
            NewsNotice NewsNotice = new NewsNotice();
            NewsNotice.setFlow(1);
            NewsNotice.setId(VerifierDto.getId());
            NewsNotice.setOpinion(VerifierDto.getOpinion());
            NewsNotice.setStatus(VerifierDto.getStatus());
            NewsNotice.setVerifier(loginUser.getUsername());
            integer = NewsNoticeService.updateNewsNotice(NewsNotice);
        } catch (Exception e) {
            log.error("审核失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("审核成功", integer);
    }


    /**
     * 修改
     */
    @PutMapping
    public AjaxResult updataNews(@RequestBody AddNewsDto AddNewsDto) {
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
            log.error("修改失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("修改成功", integer);
    }

    /**
     * 删除（已删除）
     */
    @DeleteMapping("/{id}")
    public AjaxResult delNews(@PathVariable Integer id) {
        Integer integer = null;
        try {
            integer = NewsNoticeService.DeleteNewsNoticeById(id);
        } catch (Exception e) {
            log.error("删除失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("删除成功", integer);
    }

    /**
     * 提交（已提交）
     */
    @PutMapping("/sbt/{id}")
    public AjaxResult sbtNewsSold(@PathVariable Integer id) {
        Integer integer = null;
        try {
            integer = NewsNoticeService.sbtNewsNotice(id);
        } catch (Exception e) {
            log.error("提交失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("提交成功", integer);
    }

    /**
     * 删除（已下架）
     */
    @DeleteMapping("/sold/{id}")
    public AjaxResult delNewsSold(@PathVariable Integer id) {
        Integer integer = null;
        try {
            integer = NewsNoticeService.DeleteNewsNotice(id);
        } catch (Exception e) {
            log.error("下架失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("下架成功", integer);
    }
}
