package com.rewin.swhysc.bean.dto;

import lombok.Data;

/**
 * 首页分页查询列表，传入参数
 */
@Data
public class BondInvestmentListDto {
    //员工姓名
    private String staffName;
    //人员分类
    private String staffSort;

    //当前页码
    private Integer pageNum;
    //    页面容量
    private Integer pageSize;
}
