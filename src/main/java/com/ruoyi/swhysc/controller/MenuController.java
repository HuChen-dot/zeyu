package com.ruoyi.swhysc.controller;

import com.ruoyi.swhysc.bean.vo.InitMenuVo;
import com.ruoyi.swhysc.bean.vo.ResultsSet;
import com.ruoyi.swhysc.config.RequestUrlconfig;
import com.ruoyi.swhysc.util.ResultSetUtil;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签表控制层
 */

@RestController
@RequestMapping(RequestUrlconfig.MENUCONTROLLER_URL)
public class MenuController {

    @Autowired
    com.ruoyi.swhysc.service.menu.SMenuService SMenuService;

    /**
     * 初始化首页标签的接口方法
     *
     * @return 返回所有分类好的标签
     */
    @RequestMapping("initmenu")
    public ResultsSet<List<InitMenuVo>> initmenu() {
        List<InitMenuVo> initMenuVo = null;
        try {
            initMenuVo = SMenuService.InitMenu();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSetUtil.returnerror("服务器内部错误", StateCode.FAIL.getCode());
        }
        return ResultSetUtil.returnSuccess("操作成功", initMenuVo);
    }
}
