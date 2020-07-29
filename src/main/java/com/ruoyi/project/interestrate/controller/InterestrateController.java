package com.ruoyi.project.interestrate.controller;

import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.project.interestrate.domain.Interestrate;
import com.ruoyi.project.interestrate.service.InterestrateService;
import com.ruoyi.project.system.service.ISysNoticeService;
import com.ruoyi.swhysc.bean.vo.ResultsSet;
import com.ruoyi.swhysc.config.RequestUrlconfig;
import com.ruoyi.swhysc.util.ResultSetUtil;
import com.ruoyi.swhysc.util.enums.StateCode;
import com.ruoyi.swhysc.util.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 利率表控制层
 */
@RestController
@RequestMapping(RequestUrlconfig.INTERESTRATECONTROLLER_URL)
public class InterestrateController {
    @Resource
    InterestrateService InterestrateService;

    @Autowired
    private ISysNoticeService noticeService;


    /**
     * 根据id查看数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("show")
//    在SecurityConfig类里面设置权限
    public ResultsSet<Interestrate> showInterestrate(Long id) {
        System.out.println("进入方法");
        Interestrate interestrateById = null;
        try {
            interestrateById = InterestrateService.getInterestrateById(id);
        } catch (Exception e) {

            e.printStackTrace();
            return ResultSetUtil.returnerror("查询失败,系统错误", StateCode.FAIL.getCode().toString());
        }

        return ResultSetUtil.returnSuccess("查询成功", interestrateById);
    }

    /**
     * 根据id删除数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("delete")
//    在SecurityConfig类里面设置权限
    public ResultsSet daleInterestrateByid(Long id) {
        System.out.println("进入方法");
        try {
            InterestrateService.DeleteInterestrateById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("删除失败,系统错误", StateCode.FAIL.getCode());
        }

        return ResultSetUtil.returnSuccess("删除成功");
    }


    /**
     * 根据页面传递的数据修改数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("modify")
//    在SecurityConfig类里面设置权限
    public ResultsSet ModifyInterestrat(@RequestBody Interestrate interestrate) {
        System.out.println("进入方法");
        try {
            InterestrateService.ModifyInterestrate(interestrate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("修改失败,系统错误", StateCode.FAIL.getCode());
        }

        return ResultSetUtil.returnSuccess("修改成功");
    }

    /**
     * 根据页面传递的数据添加信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("insert")
//    在SecurityConfig类里面设置权限
    public ResultsSet insertInterestrat(@RequestBody Interestrate interestrate) {
        System.out.println("进入方法");
        String far = "false";
        Integer integer = null;
        try {
            integer = InterestrateService.AddInterestrate(interestrate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("添加失败,系统错误", StateCode.FAIL.getCode());
        }

        return ResultSetUtil.returnSuccess("添加成功");
    }


    @PostMapping("uplod")
    public List<String> add(@RequestParam(value = "a_file", required = false) MultipartFile[] partfile) throws IOException {
        System.out.println("公告");
        //文件上传路径
        String path = "D:" + File.separator + "f" + File.separator + "pmj";
        List<String> pass = null;
        try {
            pass = FileUploadUtils.upload(path, partfile);
        } catch (InvalidExtensionException e) {
            e.printStackTrace();
        }

        return pass;
    }


}
