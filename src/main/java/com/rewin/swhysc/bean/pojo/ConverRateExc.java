package com.rewin.swhysc.bean.pojo;

import com.rewin.swhysc.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 私募资产管理业务从业人员信息
 */
@Data
public class ConverRateExc {
    @Excel(name = "数据类型", type = Excel.Type.ALL)
    private String infotype;
    @Excel(name = "证券代码", type = Excel.Type.ALL)
    private String stockCode;
    @Excel(name = "证券名称", type = Excel.Type.ALL)
    private String stockName;
    @Excel(name = "折算率", type = Excel.Type.ALL)
    private String rate;
    @Excel(name = "交易所", type = Excel.Type.ALL)
    private String bourse;
    @Excel(name = "调整日期", type = Excel.Type.ALL)
    private Date trimDate;

}
