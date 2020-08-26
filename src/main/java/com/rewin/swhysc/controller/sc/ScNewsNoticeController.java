package com.rewin.swhysc.controller.sc;


import com.rewin.swhysc.bean.vo.ScNewsDetailsVo;
import com.rewin.swhysc.bean.vo.ScNewsVo;
import com.rewin.swhysc.controller.manage.NewsNoticeController;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/swhysc/notice")
public class ScNewsNoticeController {

    private static final Logger log = LoggerFactory.getLogger(NewsNoticeController.class);

    @Resource
    com.rewin.swhysc.service.NewsNoticeService NewsNoticeService;

    /**
     * 查询：根据传入的新闻类型id，分页查询新闻公告列表
     * 页面页码
     * 页面容量
     * 默认第一页和10条
     */
    @GetMapping("list")
    public AjaxResult getnewsBylist(Integer id, @RequestParam(value = "pageNo", required = false) Integer pageNo,
        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("noticeTypeId", id);
        PageInfo<ScNewsVo> scNews = null;

        try {
            scNews = NewsNoticeService.queryNewsNoticePageByMap(map, pageNo, pageSize);
        } catch (Exception e) {
            log.error("查询出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", scNews);
    }

    /**
     * 查询：根据新闻id，查询新闻公告详细信息
     */
    @GetMapping("detail")
    public AjaxResult getnewsdetails(Integer id) {
        ScNewsDetailsVo newsdetails = null;
        try {
            newsdetails = NewsNoticeService.getNewsdetailsById(id);

        } catch (Exception e) {
            log.error("查询出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", newsdetails);
    }
}
