package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.bean.vo.FileName;
import com.rewin.swhysc.common.exception.file.InvalidExtensionException;
import com.rewin.swhysc.util.AjaxResult;
import com.rewin.swhysc.util.file.FileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 文件上传控制层
 */
@RestController
@RequestMapping("swhyscmanage/file")
public class FilesUploadController {
    private static final Logger log = LoggerFactory.getLogger(FilesUploadController.class);

    /**
     * 上传图片
     */
    @PostMapping
    public AjaxResult uplodeImg(MultipartFile[] file) {
        System.err.println("文件上传开始***********************");
        FileName fileName = null;
        try {
            fileName = FileUploadUtils.upload(file);
        } catch (InvalidExtensionException e) {
            e.printStackTrace();
            log.error("文件上传失败", e);
        } catch (IOException e) {
            log.error("文件传输失败", e);
            return AjaxResult.error("上传失败");
        }
        return AjaxResult.success("上传成功", fileName);
    }

    /**
     * 根据图片名，删除图片
     */
    @DeleteMapping
    public AjaxResult delImg(String imgName) {
        File file = FileUploadUtils.removeFile(imgName);
        if (file.exists()) {
            file.delete();
            return AjaxResult.error("删除成功");
        } else {
            return AjaxResult.error("删除失败");
        }

    }
}
