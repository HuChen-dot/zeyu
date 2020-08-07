package com.ruoyi.swhysc.util.enums;

import lombok.Getter;

@Getter
public enum StateCode {

    SUCCESS("200", "操作成功"),
    CREATED("201", "对象创建成功"),
    ACCEPTED("202", "请求已经被接受"),
    NO_CONTENT("204", "操作已经执行成功，但是没有返回数据"),

    MOVED_PERM("301", "资源已被移除"),
    SEE_OTHER("303", "重定向"),
    NOT_MODIFIED("304", "资源没有被修改"),

    PARAMETER("400", "参数错误"),
    UNAUTHORIZED("401", "未授权"),
    FORBIDDEN("403", "访问受限，授权过期"),
    NOT_FOUND("404", "资源，服务未找到"),
    BAD_METHOD("405", "不允许的http方法"),
    CONFLICT("409", "资源冲突，或者资源被锁"),
    UNSUPPORTED_TYPE("415", "不支持的数据，媒体类型"),
    FAIL("500", "服务器内部错误"),
    SQL_FAIL("1003", "sql错误");


    private String code;
    private String codeName;

    StateCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }


}
