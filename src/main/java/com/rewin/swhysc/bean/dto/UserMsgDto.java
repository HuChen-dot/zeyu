package com.rewin.swhysc.bean.dto;

import lombok.Data;

/**
 * @program: swhyscManageServer
 * @description:用户留言
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/26 17:00
 **/
@Data
public class UserMsgDto {
    private String name;
    private String sex;
    private String mobile;
    private String telephone;
    private String msg;
    private String ip;
}
