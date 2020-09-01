package com.rewin.swhysc.bean.pojo;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 私募资产管理业务从业人员信息
 */
@Data
public class BondbdExc {
    @Excel(name = "数据类型", type = Excel.Type.ALL)
    private String infotype;

    @Excel(name = "证券代码", type = Excel.Type.ALL)
    private String stockCode;

    @Excel(name = "证券名称", type = Excel.Type.ALL)
    private String stockName;

    @Excel(name = "融资比例", type = Excel.Type.ALL)
    private Integer rzRatio;

    @Excel(name = "融券比例", type = Excel.Type.ALL)
    private Integer rqRatio;

    @Excel(name = "是否融资(0:是;1:否)", type = Excel.Type.ALL)
    private Integer isRz;

    @Excel(name = "是否融券(0:是;1:否)", type = Excel.Type.ALL)
    private Integer isRq;

    @Excel(name = "交易所", type = Excel.Type.ALL)
    private String bourse;

    @Excel(name = "调整日期", type = Excel.Type.ALL)
    private Date trimDate;

}
