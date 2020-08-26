package com.rewin.swhysc.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author rewin
 */
@Component
@ConfigurationProperties(prefix = "swhy")
@Order(0)
public class RuoYiConfig {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;


    /**
     * 图片上传路径
     */
    public static String profile;

    /**
     * 总路径
     */
    public static String uploadPath;
    /**
     * 附件上传路径
     */
    public static String accessory;

    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public static String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {

        RuoYiConfig.accessory = accessory;
    }

    public void setProfile(String profile) {
        RuoYiConfig.profile = profile;
    }

    public static String getProfile() {
        return profile;
    }


    public void setUploadPath(String uploadPath) {
        RuoYiConfig.uploadPath = uploadPath;
    }

    public static String getUploadPath() {
        return uploadPath;
    }


    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
//        getProfile() + "/avatar"
        return null;
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {

        return "D:/download/";
    }


}