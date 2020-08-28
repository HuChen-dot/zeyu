package com.rewin.swhysc.util.file;

import com.rewin.swhysc.bean.vo.FileName;
import com.rewin.swhysc.common.exception.file.FileNameLengthLimitExceededException;
import com.rewin.swhysc.common.exception.file.FileSizeLimitExceededException;
import com.rewin.swhysc.common.exception.file.InvalidExtensionException;
import com.rewin.swhysc.config.RuoYiConfig;
import com.rewin.swhysc.util.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 */
@Component
public class FileUploadUtils {
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;


    /**
     * 总路径
     */
    private static String uploadPath;
    /**
     * 安装文件上传的地址
     */
    private static String software;
    /**
     * 图片上传的地址
     */
    private static String profile;
    /**
     * 附件上传的地址
     */
    private static String accessory;

    public static String getUploadPath() {
        return uploadPath;
    }

    @Value("${file.uploadPath:/opt/mager/upload/}")
    public void setUploadPath(String uploadPath) {
        FileUploadUtils.uploadPath = uploadPath;
    }


    //安装文件上传地址
    public static String getsoftware() {
        return uploadPath + software;
    }

    @Value("${file.software:software}")
    public static void setSoftware(String software) {
        FileUploadUtils.software = software;
    }

    /**
     * 图片和总路径上传地址
     *
     * @return
     */
    public static String getProfile() {
        return uploadPath + profile;
    }

    /**
     * 图片上传地址
     *
     * @return
     */
    public static String getProfiles() {
        return profile;
    }

    @Value("${file.profile:images}")
    public void setProfile(String profile) {
        FileUploadUtils.profile = profile;
    }

    /**
     * 附件和总路径上传地址
     *
     * @return
     */
    public static String getAccessory() {
        return uploadPath + accessory;
    }


    /**
     * 附件上传地址
     *
     * @return
     */
    public static String getAccessorys() {
        return accessory;
    }

    @Value("${file.accessory:accessory}")
    public void setAccessory(String accessory) {
        FileUploadUtils.accessory = accessory;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException {
        try {
            return upload(getProfile(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 存储路径
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 单文件上传
     *
     * @param baseDir          存储路径
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        assertAllowed(file, allowedExtension);
        String fileName = extractFilename(file);
        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        return fileName;
    }

    /**
     * 多文件上传
     *
     * @param filess 上传的文件
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    public static final FileName upload(MultipartFile[] filess)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        FileName fileName = new FileName();
        for (MultipartFile file : filess) {

            int fileNamelength = file.getOriginalFilename().length();
            if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            /* 获取原始文件名 */
            String name = file.getOriginalFilename();
            fileName.setFileSize(file.getSize());
            //文件后缀名
            String extension = getExtension(file);
            //初始化随机文件名变量
            String randomName = "";
            StringBuilder address = null;
            //判断是否是图片
            if (extension.equals("bmp") || extension.equals("gif") || extension.equals("jpg")
                    || extension.equals("jpeg") || extension.equals("png")) {
                address = new StringBuilder(getProfile());
                //生成随机文件名(随机文件名生成规则：时间戳+32位随机数+MD5混淆后取6位）
                randomName = Md5Utils.getMd5(System.currentTimeMillis() + IdUtils.simpleUUID(), 6) + "." + extension;
                //判断是否是安装文件
            } else if (extension.equals("exe")) {
                address = new StringBuilder(getsoftware());
                //生成随机文件名(随机文件名生成规则：时间戳+32位随机数+MD5混淆后取6位）
                randomName = Md5Utils.getMd5(System.currentTimeMillis() + IdUtils.simpleUUID(), 6) + "." + extension;

                //否则就是附件
            } else {
                address = new StringBuilder(getAccessory());
                //生成随机文件名并附件加上时间目录(随机文件名生成规则：时间戳+32位随机数+MD5混淆后取6位）
                randomName = extractFilename(file);
            }
            assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
            File desc = getAbsoluteFile(address.toString(), randomName);

            file.transferTo(desc);
            fileName.setFileName(name);
            fileName.setRandomName(randomName);

        }
        return fileName;
    }


    /**
     * 文件删除
     */
    public static final File removeFile(String imgName) {
        String path = "";
        //文件后缀名
        String extension = imgName.substring(imgName.lastIndexOf(".") + 1);
        StringBuilder address = new StringBuilder("");
        if (extension.equals("bmp") || extension.equals("gif") || extension.equals("jpg")
                || extension.equals("jpeg") || extension.equals("png")) {
            address = new StringBuilder(getProfile());
        } else {
            address = new StringBuilder(getAccessory());
        }
        path = address + File.separator + imgName;
        File file = new File(path);

        return file;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        String extension = getExtension(file);
        String fileName = DateUtils.datePath() + "/" + Md5Utils.getMd5(System.currentTimeMillis() + IdUtils.simpleUUID(), 6) + "." + extension;
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }


//    /**
//     * 编码文件名
//     */
//    private static final String encodingFilename(String fileName) {
//        fileName = fileName.replace("_", " ");
//        fileName = Md5Utils.hash(fileName + System.nanoTime() + counter++);
//        return fileName;
//    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }
}