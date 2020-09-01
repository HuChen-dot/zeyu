package com.rewin.swhysc.common.constant;

import io.swagger.models.auth.In;

/**
 * @program: swhyscManageServer
 * @description:业务用常量
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/25 14:54
 **/
public class BusinessConstants {
    //软件类型-PC
    public static final Integer SOFTWARE_TYPE_PC = 111;
    //软件类型-手机
    public static final Integer SOFTWARE_TYPE_MOBILE = 112;

    //手机平台-IOS
    public static final Integer PLATFORM_IOS = 1;
    //手机平台-安卓
    public static final Integer PLATFORM_ANDROID = 2;

    //自营业务债券投资交易相关人员
    public static final String SELF_OPERATED_NUM = "0";
    public static final String SELF_OPERATED_CN = "自营业务债券投资交易相关人员";

    //资产管理业务债券投资交易相关人员
    public static final String ASSET_MANAGEMENT_NUM = "1";
    public static final String ASSET_MANAGEMENT_CN = "资产管理业务债券投资交易相关人员";

    //中后台核对专岗人员
    public static final String CHECK_STAFF_NUM = "2";
    public static final String CHECK_STAFF_CN = "中后台核对专岗人员";

    //离职人员公示
    public static final String WITHDRAWN_NUM = "3";
    public static final String WITHDRAWN_CN = "离职人员公示";

    //非现场开户人员
    public static final String OPEN_ACC_STAFF = "113";
    //私募资产管理业务从业人员
    public static final String PRIVATE_EQUITY_STAFF = "115";

}
