package com.ruoyi.swhysc.util;

import com.ruoyi.common.core.lang.UUID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ID生成器工具类
 *
 * @author ruoyi
 */
public class IdUtils {

    /**
     * 传递一个字符串，在指定一个长度，可以生成指定长度的加密字符串
     *
     * @param plainText
     * @param length
     * @return
     */
    public static String getMd5(String plainText, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {

        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {

        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }
}
