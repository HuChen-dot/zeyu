package com.rewin.swhysc.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: swhyscManageServer
 * @description:营销人员信息
 * @author: sinan@rewin.com.cn
 * @create: 2020/8/27 14:58
 **/
@Data
public class MarketerInfoVo {
    List<MarketerInfo> marketerInfos;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
}
