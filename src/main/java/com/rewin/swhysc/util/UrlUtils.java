package com.rewin.swhysc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 本类提供了对URL所指向的内容的加载操作
 *
 * @author 泽宇
 */
public class UrlUtils {
    /**
     * 异步获取url网址返回的数据内容
     *
     * @param urlStr
     * @return
     */
    public static String loadURL(String urlStr, String param) {
        try {
            String urlNameString = urlStr + "?" + param;
            // 通过参数构建一个url地址
            URL url = new URL(urlNameString);
            // 通过url地址打开一个连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // 通过连接获得一个输入流
            InputStream inputStream = urlConnection.getInputStream();
            // 把输入进来的数据经过ConvertToString方法全部转换后变成一个字符串，返回回去
            String responseStr = ConvertToString(inputStream);
            return responseStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用来把一个输入流中的数据，全部转换成字符串
     *
     * @param inputStream
     * @return
     * @throws UnsupportedEncodingException
     */

    private static String ConvertToString(InputStream inputStream) throws UnsupportedEncodingException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

}
