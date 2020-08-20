package com.rewin.swhysc.controller.manage;

import com.rewin.swhysc.util.file.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/sys/file")
public class FileLoads {

    /**
     * 文件下载
     */
    @GetMapping("load")
    public void file(String fileName) throws UnsupportedEncodingException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse responses = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(fileName);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        responses.setHeader("Content-type", type);
        // 设置编码
        String hehe = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        responses.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        try {
            FileUtils.download(fileName, responses);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
