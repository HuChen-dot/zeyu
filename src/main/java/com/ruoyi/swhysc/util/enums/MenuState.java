package com.ruoyi.swhysc.util.enums;

import lombok.Getter;

@Getter
public enum MenuState {

    COME("1", "生效"),
    VACATIO("2", "待审核"),
    APPROVED("4", "审核通过"),
    Not_APPROVED("8", "审核不通过");


    private String code;
    private String codeName;

    MenuState(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
}
