package com.ruoyi.swhysc.util.enums;

import lombok.Getter;

@Getter
public enum StateCode {

    SUCCESS("200", "操作成功"),
    FAIL("500", "服务器内部错误"),
    PARAMETER("400", "参数错误"),
    SQL_FAIL("1003", "sql错误");


    private String code;
    private String codeName;

    StateCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }


}
