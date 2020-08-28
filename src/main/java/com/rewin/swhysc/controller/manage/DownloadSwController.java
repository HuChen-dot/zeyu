package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.DownloadSw;
import com.rewin.swhysc.bean.SysUser;
import com.rewin.swhysc.bean.dto.DnSwDto;
import com.rewin.swhysc.bean.dto.newsDto;
import com.rewin.swhysc.bean.vo.DownloadSwVo;
import com.rewin.swhysc.bean.vo.newsVo;
import com.rewin.swhysc.common.utils.poi.ExcelUtil;
import com.rewin.swhysc.framework.aspectj.lang.annotation.Log;
import com.rewin.swhysc.framework.aspectj.lang.enums.BusinessType;
import com.rewin.swhysc.service.DownloadSwService;
import com.rewin.swhysc.service.NewsNoticeService;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看软件下载次数控制层
 */
@RestController
@RequestMapping("/swhyscmanage/softwareload")
public class DownloadSwController {
    private static final Logger log = LoggerFactory.getLogger(DownloadSwController.class);
    @Resource
    DownloadSwService DownloadSwService;

    /**
     * 查询：根据传入的条件，分页查询软件下载次数
     */
    @GetMapping("list")
    public AjaxResult getsowftwareBylist(DnSwDto DnSwDto) {
        Map<String, Object> map = new HashMap<>(4);
        if (DnSwDto.getSoftwareName() != null) {
            map.put("softwareName", DnSwDto.getSoftwareName());
        }
        Date beginTime = null;
        Date endTime = null;
        if (DnSwDto.getBeginTime() != null && DnSwDto.getEndTime() != null &&
                DnSwDto.getBeginTime() != "" && DnSwDto.getEndTime() != "") {

            beginTime = DateUtils.parseDate(DnSwDto.getBeginTime());
            endTime = DateUtils.parseDate(DnSwDto.getEndTime());

            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
        }
        PageInfo<DownloadSwVo> info = null;
        try {
            info = DownloadSwService.queryDownloadSwPageByMap(map, DnSwDto.getPageNum(), DnSwDto.getPageSize());
        } catch (Exception e) {
            log.error("查询失败", e);
            return AjaxResult.error("系统错误，请重试");
        }
        return AjaxResult.success("查询成功", info);
    }

    /**
     * 导出下载明细
     *
     * @param
     * @return
     */
    @GetMapping("/export")
    public AjaxResult export(Integer id) {
        ExcelUtil<DownloadSwVo> util = null;
        List<DownloadSwVo> downloadSw = null;
        try {
            downloadSw = DownloadSwService.getDownloadSwById(id);
            util = new ExcelUtil<DownloadSwVo>(DownloadSwVo.class);
        } catch (Exception e) {
            log.error("数据库查询导出失败", e);
            return AjaxResult.error("数据库查询导出失败，请重试");
        }
        return util.exportExcel(downloadSw, "软件下载数据");
    }

}
