package com.rewin.swhysc.controller.sc;

import com.rewin.swhysc.util.page.PageInfo;
import com.rewin.swhysc.bean.vo.BondBdVo;
import com.rewin.swhysc.bean.vo.ConvertRateVo;
import com.rewin.swhysc.bean.vo.InterestRateVo;
import com.rewin.swhysc.bean.vo.WarrantRatioVo;
import com.rewin.swhysc.service.BondBdService;
import com.rewin.swhysc.service.ConvertRateService;
import com.rewin.swhysc.service.InterestRateService;
import com.rewin.swhysc.service.WarrantRatioService;
import com.rewin.swhysc.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/swhysc/rzrq")
@RestController
public class ScRzrqController {
    private static final Logger log = LoggerFactory.getLogger(ScAdSpaceController.class);
    @Resource
    ConvertRateService convertRateService;
    @Resource
    BondBdService bondBdService;
    @Resource
    InterestRateService interestRateService;
    @Resource
    WarrantRatioService warrantRatioService;
    /**
     * 官网使用折算率查询
     */
    @GetMapping("converRate/queryList")
    public AjaxResult queryConverRateList(Integer pageNum, Integer pageSize) {
        PageInfo<ConvertRateVo> convertRatePageInfo = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            //设置分页的起始页数和页面容量
            convertRatePageInfo = convertRateService.getConverRateList(pageNum,pageSize,null,null,null);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", convertRatePageInfo);
    }

    /**
     * 官网使用标的查询
     */
    @GetMapping("bondBd/queryList")
    public AjaxResult queryBondBdList(Integer pageNum, Integer pageSize) {
        PageInfo<BondBdVo> bondBdPageInfo = null;
        try {
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            //设置分页的起始页数和页面容量
            bondBdPageInfo = bondBdService.getBondBdList(pageNum, pageSize,null,null,null);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", bondBdPageInfo);
    }

    /**
     * 官网使用利率费率查询
     */
    @GetMapping("interestRate/queryList")
    public AjaxResult queryInterestRateList() {
        List<InterestRateVo> interestRateList = null;
        try {
            interestRateList = interestRateService.getInterestRateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", interestRateList);
    }

    /**
     * 官网使用维持担保比例查询
     */
    @GetMapping("warrantRatio/queryList")
    public AjaxResult queryWarrantRatioList() {
        List<WarrantRatioVo> warrantRatioList = null;
        try {
            warrantRatioList = warrantRatioService.getWarrantRatioList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", warrantRatioList);
    }
}
