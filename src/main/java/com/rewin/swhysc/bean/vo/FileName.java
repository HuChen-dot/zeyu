package com.rewin.swhysc.bean.vo;

import lombok.Data;

/**
 * 存储文件原始名和文件随机名返回前端
 */
@Data
public class FileName {
    //原始名
    private String fileName;
    // 随机名
    private String randomName;
    //文件大小
    private long fileSize;
}
