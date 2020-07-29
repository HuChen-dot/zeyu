package com.ruoyi.swhysc.util;


import com.ruoyi.swhysc.bean.vo.ResultsSet;

public class ResultSetUtil {

    public static String Code = "200";// 该错误码为自定义，默认200表示操作成功；

    /***
     * 统一返回成功的DTO
     */
    public static ResultsSet returnSuccess() {
        ResultsSet dto = new ResultsSet();
        dto.setCode(Code);
        return dto;
    }

    /***
     * 统一返回成功的DTO 带数据
     */
    public static ResultsSet returnSuccess(String message, Object data) {
        ResultsSet dto = new ResultsSet();
        dto.setCode(Code);
        dto.setMsg(message);
        dto.setData(data);
        return dto;
    }

    /***
     * 统一返回成功的DTO 不带数据
     */
    public static ResultsSet returnSuccess(String message) {
        ResultsSet dto = new ResultsSet();
        dto.setCode(Code);
        dto.setMsg(message);
        return dto;
    }

    /***
     * 统一返回成功的DTO 带数据 没有消息
     */
    public static ResultsSet returnDataSuccess(Object data) {
        ResultsSet dto = new ResultsSet();
        dto.setCode(Code);
        dto.setData(data);
        return dto;
    }

    /***
     * 统一返回失败的DTO 不带数据
     */
    public static ResultsSet returnerror(String message, String errorCode) {
        ResultsSet dto = new ResultsSet();
        dto.setMsg(message);
        dto.setCode(errorCode);
        return dto;
    }
}
