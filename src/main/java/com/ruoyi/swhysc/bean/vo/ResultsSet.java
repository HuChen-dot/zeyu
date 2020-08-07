package com.ruoyi.swhysc.bean.vo;


import lombok.Getter;
import lombok.Setter;

/**
 * 后台响应前端的对象模板类
 */
@Getter
@Setter
public class ResultsSet<T> {
    private String Code;// 该错误码为自定义，一般200表示无错
    private String msg;// 对应的提示信息
    private T data;// 具体返回数据内容(pojo、自定义VO、其他)


}
