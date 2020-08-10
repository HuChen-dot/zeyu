package com.rewin.swhysc.common.utils;

import com.mchange.v2.util.PropertiesUtils;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置文件类
 */
public class ConfigUtils {
    private ConfigUtils() {
    }

    private static Properties props;

    /**
     * 加载配置文件
     *
     * @param fileName
     */
    private static void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     *
     * @param key
     * @return
     */
    public static String get(String fileName, String key) {
        readProperties(fileName);
        return props.getProperty(key);
    }
}
