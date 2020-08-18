package com.ruoyi.swhysc.controller.manage;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.swhysc.bean.Advertise;
import com.ruoyi.swhysc.bean.ConvertRate;
import com.ruoyi.swhysc.bean.dto.AddAdvertiseDto;
import com.ruoyi.swhysc.bean.dto.AddConvertRateDto;
import com.ruoyi.swhysc.bean.vo.AdvertiseVo;
import com.ruoyi.swhysc.bean.vo.ConvertRateVo;
import com.ruoyi.swhysc.mapper.dao.ConvertRateMapper;
import com.ruoyi.swhysc.security.LoginUser;
import com.ruoyi.swhysc.service.ConvertRateService;
import com.ruoyi.swhysc.util.AjaxResult;
import com.ruoyi.swhysc.util.ServletUtils;
import com.ruoyi.swhysc.util.file.ExcelReader;
import com.ruoyi.swhysc.util.file.FileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swhyscmanage/convertRate")
public class ConvertRateController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ConvertRateController.class);
    @Resource
    ConvertRateService convertRateService;

    @Resource
    ConvertRateMapper convertRateMapper;

    @Resource
    com.ruoyi.swhysc.security.service.TokenService TokenService;


    /**
     * 分页查询折算率信息列表
     */
    @GetMapping("list")
    public AjaxResult getConverRateList(Integer pageNum, Integer pageSize,String stockCode,String stockName,String updateDate) {
        PageInfo<ConvertRate> converRateList = null;
        try {
            System.out.println("---------------"+pageNum);
            System.out.println("---------------"+pageSize);
            if(pageNum == null){
                pageNum = 1;
            }
            if(pageSize == null){
                pageSize = 10;
            }
            converRateList = convertRateService.getConverRateList(pageNum, pageSize,stockCode,stockName,updateDate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", converRateList);
    }

    /**
     * 根据ID查询单条
     */
    @GetMapping("info/{id}")
    public AjaxResult getConverRate(@PathVariable Integer id) {
        ConvertRate converRate = null;
        try {
            Map<String, Object> map = new HashMap<>(1);
            map.put("ID", id);
            converRate = convertRateMapper.getConverRateInfo(map);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("查询成功", converRate);
    }

    /**
     *
     */
    @PostMapping("fileImport")
    public AjaxResult fileImport(MultipartFile[] file) {
        AjaxResult result =  this.impExcel(file[0]);
        return result;
    }

    /**
     *
     */
    @PostMapping("addConverRate")
    public AjaxResult addConverRate(AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());

        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setCreateUser(loginUser.getUsername());
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setCreateDate(new java.util.Date());
        convertRate.setUpdateDate(new java.util.Date());
        convertRate.setState("0");
        try {
            convertRateMapper.insertConverRate(convertRate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("添加成功");
    }

    /**
     *
     */
    @PutMapping("updateConverRate")
    public AjaxResult updateConverRate(AddConvertRateDto addConvertRateDto) {
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ConvertRate convertRate = new ConvertRate();
        BeanUtils.copyProperties(addConvertRateDto, convertRate);
        convertRate.setUpdateUser(loginUser.getUsername());
        convertRate.setUpdateDate(new java.util.Date());
        try {
            convertRateMapper.updateConvertRate(convertRate);
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("修改成功");
    }

    /**
     *
     */
    @PutMapping("delete")
    public AjaxResult deleteConverRateByID(String idStrings) {
        String[] ids = idStrings.split(",");
        for(int i=0;i<ids.length;i++){
            ConvertRate convertRate = new ConvertRate();
            int id = Integer.parseInt(ids[i]);
            convertRate.setId(id);
            convertRate.setState("1");
            try {
                convertRateMapper.updateConvertRate(convertRate);
            } catch (Exception e) {
                log.error("查询数据库出错", e);
                return AjaxResult.error("sql错误");
            }
        }
        return AjaxResult.success("删除成功");
    }

    /**
     *
     */
    @PutMapping("deleteAll")
    public AjaxResult deleteConverRateAll() {
        try {
            convertRateMapper.deleteConverRateAll();
        } catch (Exception e) {
            log.error("查询数据库出错", e);
            return AjaxResult.error("sql错误");
        }
        return AjaxResult.success("删除成功");
    }


    public AjaxResult impExcel(MultipartFile file){
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        ExcelReader er = new ExcelReader();
        int count =0;
        int error =0;
        int success = 0;

        List<ConvertRate> list_ConverRate = new ArrayList<ConvertRate>();
        String returnMsg = "";
        int index = 1;
        try {
            List<String[]> list = er.readExcel(file); //读取Excel数据内容
            count = list.size();

            for(int i=0;i<list.size();i++){
                String[] map = list.get(i);
                if(map[1]==null || "".equals(map[1])){
                    returnMsg += "第"+index+"行：【证券代码(必填)】列不能为空;";
                } else if(map[2]==null || "".equals(map[2])){
                    returnMsg += "第"+index+"行：【证券名称(必填)】列不能为空;";
                } else if(map[3]==null || "".equals(map[3])){
                    returnMsg += "第"+index+"行：【折算率(必填)】列不能为空;";
                } else if(map[4]==null || "".equals(map[4])){
                    returnMsg += "第"+index+"行：【交易所编号(必填)】列不能为空;";
                }else {
                    ConvertRate convertRate = new ConvertRate();
                    convertRate.setInfoType(2);
                    convertRate.setStockCode(map[1]);
                    convertRate.setStockName(map[2]);
                    convertRate.setRate(map[3]);
                    convertRate.setBourseCode(map[4]);
                    convertRate.setCreateUser(loginUser.getUsername());
                    convertRate.setUpdateUser(loginUser.getUsername());
                    convertRate.setCreateDate(new java.util.Date());
                    convertRate.setUpdateDate(new java.util.Date());
                    convertRate.setState("0");
                    list_ConverRate.add(convertRate);
                    index++;
                }
            }
            for (int j=0;j<list_ConverRate.size();j++){
                try {
                    convertRateMapper.insertConverRate(list_ConverRate.get(j));
                } catch (Exception e) {
                    log.error("查询数据库出错", e);
                    return AjaxResult.error("sql错误");
                }
            }

        } catch (Exception e) {
            log.error("批量导入信息异常", e.getMessage());
            return AjaxResult.error(returnMsg);
        }
        return AjaxResult.success("批量导入信息成功");
    }
}