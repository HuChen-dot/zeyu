package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.NewsAccessory;
import com.rewin.swhysc.bean.NewsContent;
import com.rewin.swhysc.bean.NewsNotice;
import com.rewin.swhysc.bean.SysDictType;
import com.rewin.swhysc.bean.dto.AddNewsDto;
import com.rewin.swhysc.bean.vo.ScNewsDetailsVo;
import com.rewin.swhysc.bean.vo.ScNewsVo;
import com.rewin.swhysc.bean.vo.UpdataNewsVo;
import com.rewin.swhysc.bean.vo.newsVo;
import com.rewin.swhysc.mapper.dao.NewsAccessoryMapper;
import com.rewin.swhysc.mapper.dao.NewsNoticeMapper;
import com.rewin.swhysc.mapper.dao.SysDictTypeMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.NewsNoticeService;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 新闻公告表业务层
 */
@Service
public class NewsNoticeServiceImpl implements NewsNoticeService {


    @Resource
    private NewsNoticeMapper newsNoticeMapper;

    @Resource
    NewsAccessoryMapper NewsAccessoryMapper;

    @Resource
    com.rewin.swhysc.mapper.dao.NewsContentMapper NewsContentMapper;

    @Resource
    SysDictTypeMapper SysDictTypeMapper;

    @Resource
    com.rewin.swhysc.security.service.TokenService TokenService;

    /**
     * 根据id查询；返回单个对象
     */
    public ScNewsDetailsVo getNewsdetailsById(Integer id) throws Exception {
        //查询新闻表
        NewsNotice newsNoticeById = newsNoticeMapper.getNewsNoticeById(id);
        //查询新闻内容表
        Map<String, Object> map = new HashMap<>(1);
        map.put("newsId", id);
        NewsContent NewsContent = NewsContentMapper.getNewsContentListByMap(map).get(0);
        //查询新闻附件表
        Map<String, Object> map1 = new HashMap<>(1);
        map1.put("newsId", id);
        NewsAccessory newsAccessory = NewsAccessoryMapper.getNewsAccessoryListByMap(map1).get(0);
        //初始化传输对象
        ScNewsDetailsVo ScNewsDetailsVo = new ScNewsDetailsVo();
        ScNewsDetailsVo.setNewsId(id);
        ScNewsDetailsVo.setUpdateTime(newsNoticeById.getUpdateTime());
        ScNewsDetailsVo.setAccessoryName(newsAccessory.getAccessoryName());
        ScNewsDetailsVo.setAccessoryPath(newsAccessory.getAccessoryPath());
        ScNewsDetailsVo.setAuthor(NewsContent.getAuthor());
        ScNewsDetailsVo.setNewsContent(NewsContent.getNewsContent());
        ScNewsDetailsVo.setNoticeTitle(NewsContent.getNoticeTitle());
        ScNewsDetailsVo.setSource(NewsContent.getSource());
        ScNewsDetailsVo.setType(NewsContent.getType());
        return ScNewsDetailsVo;
    }

    /**
     * 用户修改新闻内容前，通过新闻id，获取详细新闻信息返回前台
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UpdataNewsVo getNewslistById(Integer id) throws Exception {
        UpdataNewsVo UpdataNewsVo = new UpdataNewsVo();
        //查询新闻表
        NewsNotice newsNotice = newsNoticeMapper.getNewsNoticeById(id);
        //查询新闻内容表
        NewsContent newsContent = NewsContentMapper.getNewsContentBynewsId(id);
        //查询新闻附件表
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put("newsId", id);
        NewsAccessory newsAccessory = NewsAccessoryMapper.getNewsAccessoryListByMap(map).get(0);

        //复制封装参数
        UpdataNewsVo.setAccessoryName(newsAccessory.getAccessoryName());
        UpdataNewsVo.setAccessoryPath(newsAccessory.getAccessoryPath());
        UpdataNewsVo.setAuthor(newsContent.getAuthor());
        UpdataNewsVo.setId(newsNotice.getId());
        UpdataNewsVo.setNewsContent(newsContent.getNewsContent());
        UpdataNewsVo.setNoticeTitle(newsNotice.getNoticeTitle());
        UpdataNewsVo.setType(newsContent.getType());
        UpdataNewsVo.setSource(newsContent.getSource());
        UpdataNewsVo.setNoticeTypeId(newsNotice.getNoticeTypeId());

        return UpdataNewsVo;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<NewsNotice> getNewsNoticeListByMap(Map
                                                           <String, Object> param) throws Exception {
        return newsNoticeMapper.getNewsNoticeListByMap(param);
    }


    /**
     * 根据条件查询新闻公告列表
     */
    @Override
    public List<newsVo> getNewsListByMap(Map<String, Object> param) throws Exception {
        List<newsVo> listnews = new ArrayList<>();
        List<NewsNotice> newsNotices = newsNoticeMapper.queryNewsList(param);
        SysDictType sysDictTypeById = null;
        if ((Integer) param.get("noticeTypeId") != 0) {
            sysDictTypeById = SysDictTypeMapper.getSysDictTypeById((Integer) param.get("noticeTypeId"));
        }
        for (NewsNotice newsNotice : newsNotices) {
            newsVo newsVo = new newsVo();
            if ((Integer) param.get("noticeTypeId") == 0) {
                SysDictType sysDictTypeById1 = SysDictTypeMapper.getSysDictTypeById(newsNotice.getNoticeTypeId());
                //设置信息类型名称
                newsVo.setNoticeTypeName(sysDictTypeById1.getDictName());
            }
            //拷贝字段
            BeanUtils.copyProperties(newsNotice, newsVo);
            if ((Integer) param.get("noticeTypeId") != 0) {
                //设置信息类型名称
                newsVo.setNoticeTypeName(sysDictTypeById.getDictName());
            }
            listnews.add(newsVo);
        }

        return listnews;
    }


    /**
     * 添加：根据传入的参数添加新闻表，新闻内容表，和新闻附件表；返回影响的行数
     */
    //添加事务
    @Transactional
    public Integer AddNewsNotice(AddNewsDto AddNewsDto) throws Exception {
        //获取登录当前用户的信息
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        //添加新闻表
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setIsStick(AddNewsDto.getIsStick());
        newsNotice.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsNotice.setNoticeTypeId(AddNewsDto.getNoticeTypeId());
        //判断这次新增是否是存草稿
        if (AddNewsDto.getIsAdd() == 1) {
            newsNotice.setStatus("4");
        } else {
            newsNotice.setStatus("1");
        }
        newsNotice.setCreateTime(new Date());
        newsNotice.setCreator(loginUser.getUsername());
        newsNotice.setUpdateTime(new Date());
        newsNotice.setUpdater(loginUser.getUsername());
        newsNoticeMapper.insertNewsNotice(newsNotice);
        //添加新闻内容表
        NewsContent newsContent = new NewsContent();
        newsContent.setNewsId(newsNotice.getId());
        newsContent.setAuthor(AddNewsDto.getAuthor());
        newsContent.setNewsContent(AddNewsDto.getNewsContent());
        newsContent.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsContent.setSource(AddNewsDto.getSource());
        newsContent.setType(AddNewsDto.getType());
        NewsContentMapper.insertNewsContent(newsContent);
        //添加新闻附件表
        NewsAccessory newsAccessory = new NewsAccessory();
        newsAccessory.setNewsId(newsNotice.getId());
        newsAccessory.setAccessoryName(AddNewsDto.getAccessoryName());
        newsAccessory.setAccessoryPath(AddNewsDto.getAccessoryPath());
        Integer integer = NewsAccessoryMapper.insertNewsAccessory(newsAccessory);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改新闻表，新闻内容表，和新闻附件表；返回影响的行数
     */
    public Integer ModifyNewsNotice(AddNewsDto AddNewsDto) throws Exception {
        //获取登录当前用户的信息
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        //修改新闻表
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(AddNewsDto.getId());
        newsNotice.setIsStick(AddNewsDto.getIsStick());
        newsNotice.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsNotice.setNoticeTypeId(AddNewsDto.getNoticeTypeId());
        newsNotice.setStatus("4");
        newsNotice.setUpdateTime(new Date());
        newsNotice.setUpdater(loginUser.getUsername());
        newsNoticeMapper.updateNewsNotice(newsNotice);
        //修改新闻内容表
        NewsContent newsContent = new NewsContent();
        newsContent.setNewsId(newsNotice.getId());
        newsContent.setAuthor(AddNewsDto.getAuthor());
        newsContent.setNewsContent(AddNewsDto.getNewsContent());
        newsContent.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsContent.setSource(AddNewsDto.getSource());
        newsContent.setType(AddNewsDto.getType());
        NewsContentMapper.updateNewsContent(newsContent);
        //修改新闻附件表
        NewsAccessory newsAccessory = new NewsAccessory();
        newsAccessory.setNewsId(newsNotice.getId());
        newsAccessory.setAccessoryName(AddNewsDto.getAccessoryName());
        newsAccessory.setAccessoryPath(AddNewsDto.getAccessoryPath());
        Integer integer = NewsAccessoryMapper.updateNewsAccessory(newsAccessory);

        return integer;
    }

    /**
     * 删除：把新闻的状态修改为已删除
     */
    public Integer DeleteNewsNoticeById(Integer id) throws Exception {
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(id);
        newsNotice.setStatus("16");
        return newsNoticeMapper.updateNewsNotice(newsNotice);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<ScNewsVo> queryNewsNoticePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        List<ScNewsVo> list = new ArrayList<>();
        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(pageNo, pageSize);
        //正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<NewsNotice> newsNoticeList = newsNoticeMapper.getNewsNoticeListByMap(param);
        for (NewsNotice newsNotice : newsNoticeList) {
            ScNewsVo ScNewsVo = new ScNewsVo();
            BeanUtils.copyProperties(newsNotice, ScNewsVo);
            list.add(ScNewsVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<ScNewsVo> info = new PageInfo<ScNewsVo>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(list);
        return info;
    }

}
