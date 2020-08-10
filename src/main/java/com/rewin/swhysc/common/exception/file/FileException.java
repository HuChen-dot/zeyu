package com.rewin.swhysc.common.exception.file;

import com.rewin.swhysc.common.exception.BaseException;

/**
 * 文件信息异常类
 *
 * @author rewin
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {

        super("file", code, args, null);
    }

}
