package com.rewin.swhysc.util;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 加载属性文件类
 *
 * @author 泽宇
 */
public class PropertiesUtil {

    static Properties properties;

    /**
     * 加载配置文件
     *
     * @param fileName
     */
    private static void readProperties(String fileName) {
        try {
            properties = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对应的值
     *
     * @param url 配置文件的路径
     * @param key 要查询的值
     * @return
     */
    public static String get(String url, String key) {
        readProperties(url);
        return properties.getProperty(key);

    }

}
