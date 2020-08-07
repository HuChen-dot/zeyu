package com.ruoyi.swhysc.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 频道正文表实体类
 */
@Getter
@Setter
public class Infocontent implements Serializable {
    //信息ID.对应InfoBase表中的ID字段
    private Integer infoid;
    //信息的正文
    private String content;
    //　指明Content字段中所存内容的格式:;0 ― TXT;1 － HTML;2 － URL;3 － 附件;1 － 视图;2 － URL
    private Integer contenttype;
    //指明Content字段中所存内容的字节长度，-1表示未统计
    private Integer contentsize;


}
