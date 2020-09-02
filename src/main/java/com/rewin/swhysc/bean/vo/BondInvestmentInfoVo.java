package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: swhyscManageServer
 * @description:债券投资相关人员信息查询出参
 * @author: sinan@rewin.com.cn
 * @create: 2020/9/1 10:55
 **/
@Data
public class BondInvestmentInfoVo {
    List<BondInvestmentInfo> bondInvestmentInfos;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}
