package com.rewin.swhysc.service;

import com.rewin.swhysc.bean.NewsNotice;
import com.rewin.swhysc.bean.dto.AddNewsDto;
import com.rewin.swhysc.bean.dto.VerifierDto;
import com.rewin.swhysc.bean.vo.ScNewsDetailsVo;
import com.rewin.swhysc.bean.vo.ScNewsVo;
import com.rewin.swhysc.bean.vo.UpdataNewsVo;
import com.rewin.swhysc.bean.vo.newsVo;
import com.rewin.swhysc.util.page.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 新闻公告表业务层
 */
public interface NewsNoticeService {

    /**
     * 根据id查询；返回单个对象
     */
    ScNewsDetailsVo getNewsdetailsById(Integer id) throws Exception;

    /**
     * 用户修改新闻内容前，通过新闻id，获取详细新闻信息返回前台
     */
    UpdataNewsVo getNewslistById(Integer id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<NewsNotice> getNewsNoticeListByMap(Map<String, Object> param) throws Exception;

    /**
     * 修改：审核人员审核后修改
     */
    Integer updateNewsNotice(NewsNotice NewsNotice) throws Exception;


    /**
     * 根据条件查询新闻公告列表
     */
    PageInfo<newsVo> getNewsListByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加新闻表，新闻内容表，和新闻附件表；返回影响的行数
     */
    Integer AddNewsNotice(AddNewsDto AddNewsDto) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改新闻表，新闻内容表，和新闻附件表；返回影响的行数
     */
    Integer ModifyNewsNotice(AddNewsDto AddNewsDto) throws Exception;

    /**
     * 删除：把新闻的状态修改为已删除
     */
    Integer DeleteNewsNoticeById(Integer id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<ScNewsVo> queryNewsNoticePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
