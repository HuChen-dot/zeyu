package com.ruoyi.swhysc.controller.manage;

import com.github.pagehelper.PageInfo;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.swhysc.bean.SysNotice;
import com.ruoyi.swhysc.security.LoginUser;
import com.ruoyi.swhysc.security.service.TokenService;
import com.ruoyi.swhysc.service.ISysNoticeService;
import com.ruoyi.swhysc.util.AjaxResult;
import com.ruoyi.swhysc.util.SecurityUtils;
import com.ruoyi.swhysc.util.ServletUtils;
import com.ruoyi.swhysc.util.StringUtils;
import com.ruoyi.swhysc.util.enums.StateCode;
import com.ruoyi.swhysc.util.file.FileUploadUtils;
import com.ruoyi.swhysc.util.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private TokenService tokenService;


    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice,
                          @RequestParam(value = "a_file", required = false) MultipartFile partfile) throws IOException {
        System.out.println("公告");
        //文件上传路径
        String path = "D:" + File.separator + "f" + File.separator + "pmj";
        String pass = FileUploadUtils.upload(path, partfile);

        notice.setCreateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }

    // *********************************************************************************

    /**
     * 根据菜单频道id，分页获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("listByid")
    public AjaxResult listByid(Integer id, Integer pageNo, Integer pageSize) {
        //根据Token,从缓存中获取用户信息,
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //判断
        if (StringUtils.isNull(loginUser)) {
            return AjaxResult.error(StateCode.FORBIDDEN.getCode(), "用户信息失效，请重新登录");
        }
        PageInfo<SysNotice> sysNoticePageInfo = noticeService.selectNoticeListpage(id, pageNo, pageSize);
        System.out.println(sysNoticePageInfo.toString());
        return AjaxResult.success("查询成功", sysNoticePageInfo);
    }

//    /**
//     * 新增通知公告
//     */
//    @PreAuthorize("@ss.hasPermi('system:notice:add')")
//    @Log(title = "通知公告", businessType = BusinessType.INSERT)
//    @PostMapping("add")
//    public ResultsSet add(@Validated @RequestBody SysNoticeVo noticevo) {
//        //根据Token,从缓存中获取用户信息,
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        //判断
//        if (StringUtils.isNull(loginUser)) {
//            return ResultSetUtil.returnerror("用户信息失效，请重新登录", StateCode.FORBIDDEN.getCode());
//        }
//        noticeService.insertNotice(noticevo);
//        return ResultSetUtil.returnSuccess("增加成功");
//    }
//
//    /**
//     * 上传图片
//     */
////    @PreAuthorize("@ss.hasPermi('system:notice:add')")
////    @Log(title = "图片上传", businessType = BusinessType.INSERT)
//    @PostMapping("uplodeImg")
//    public ResultsSet uplodeImg(@RequestParam(value = "a_file", required = false) MultipartFile[] partfile) {
//        //根据Token,从缓存中获取用户信息,
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        //判断
//        if (StringUtils.isNull(loginUser)) {
//            return ResultSetUtil.returnerror("用户信息失效，请重新登录", StateCode.FORBIDDEN.getCode());
//        }
//        String path = "D:" + File.separator + "f" + File.separator + "pmj";
//        List<String> pass = null;
//        try {
//            try {
//                pass = FileUploadUtils.upload(path, partfile);
//            } catch (InvalidExtensionException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResultSetUtil.returnerror("上传失败", StateCode.FAIL.getCode());
//        }
//        return ResultSetUtil.returnSuccess("上传成功", pass);
//    }
//
//    /**
//     * 根据图片名，删除图片
//     */
////    @PreAuthorize("@ss.hasPermi('system:notice:add')")
////    @Log(title = "图片上传", businessType = BusinessType.INSERT)
//    @PostMapping("delImg")
//    public ResultsSet delImg(String imgName) {
//        //根据Token,从缓存中获取用户信息,
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        //判断
//        if (StringUtils.isNull(loginUser)) {
//            return ResultSetUtil.returnerror("用户信息失效，请重新登录", StateCode.FORBIDDEN.getCode());
//        }
//        String path = "D:" + File.separator + "f" + File.separator + "pmj";
//        path = path + File.separator + imgName;
//        File file = new File(path);
//        if (file.exists()) {
//            file.delete();
//            return ResultSetUtil.returnSuccess("删除成功");
//        } else {
//            return ResultSetUtil.returnerror("删除失败", StateCode.NOT_FOUND.getCode());
//        }
//    }
//
//    /**
//     * 修改通知公告
//     */
//    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
//    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
//    @PutMapping("edit")
//    public ResultsSet edit(@Validated @RequestBody SysNoticeVo noticevo) {
//        //根据Token,从缓存中获取用户信息,
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        //判断
//        if (StringUtils.isNull(loginUser)) {
//            return ResultSetUtil.returnerror("用户信息失效，请重新登录", StateCode.FORBIDDEN.getCode());
//        }
//        noticeService.updateNotice(noticevo);
//        return ResultSetUtil.returnSuccess("修改成功");
//    }
//
//    /**
//     * 删除通知公告
//     */
//    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
//    @Log(title = "通知公告", businessType = BusinessType.DELETE)
//    @DeleteMapping("remove/{noticeIds}")
//    public ResultsSet remove(@PathVariable Long[] noticeIds) {
//        //根据Token,从缓存中获取用户信息,
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        //判断
//        if (StringUtils.isNull(loginUser)) {
//            return ResultSetUtil.returnerror("用户信息失效，请重新登录", StateCode.FORBIDDEN.getCode());
//        }
//        noticeService.deleteNoticeByIds(noticeIds);
//        return ResultSetUtil.returnSuccess("删除成功");
//    }
}
