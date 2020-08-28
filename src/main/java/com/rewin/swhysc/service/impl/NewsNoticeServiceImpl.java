package com.rewin.swhysc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rewin.swhysc.bean.NewsAccessory;
import com.rewin.swhysc.bean.NewsContent;
import com.rewin.swhysc.bean.NewsNotice;
import com.rewin.swhysc.bean.SysDictType;
import com.rewin.swhysc.bean.dto.AddNewsDto;
import com.rewin.swhysc.bean.vo.*;
import com.rewin.swhysc.config.RuoYiConfig;
import com.rewin.swhysc.mapper.dao.NewsAccessoryMapper;
import com.rewin.swhysc.mapper.dao.NewsNoticeMapper;
import com.rewin.swhysc.mapper.dao.SysDictTypeMapper;
import com.rewin.swhysc.security.LoginUser;
import com.rewin.swhysc.service.NewsNoticeService;
import com.rewin.swhysc.util.DateUtils;
import com.rewin.swhysc.util.ServletUtils;
import com.rewin.swhysc.util.file.FileUploadUtils;
import com.rewin.swhysc.util.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        ScNewsDetailsVo.setUpdateTime(dateFormat.format(newsNoticeById.getUpdateTime()));
        String[] AccessoryName = newsAccessory.getAccessoryName().split(",");
        ScNewsDetailsVo.setAccessoryName(AccessoryName);
        String accessoryPath = newsAccessory.getAccessoryPath();
        //附件上传地址
        String accessory = FileUploadUtils.getAccessorys();
        List<String> listpath = new ArrayList<>();
        if (accessoryPath != null && accessoryPath != "") {
            String[] Path = accessoryPath.split(",");
            for (String s : Path) {
                s = accessory + "/" + s;
                listpath.add(s);
            }
        }
        ScNewsDetailsVo.setAccessoryPath(listpath);
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
        //附件上传地址
//        String accessory = RuoYiConfig.getAccessory();

        //复制封装参数
        String[] ccessoryName = newsAccessory.getAccessoryName().split(",");
        UpdataNewsVo.setAccessoryName(ccessoryName);
        String[] ccessoryPath = newsAccessory.getAccessoryPath().split(",");
        String[] AccessoryPath = new String[ccessoryPath.length];
        for (int i = 0; i < ccessoryPath.length; i++) {
            AccessoryPath[i] = FileUploadUtils.getAccessorys() + "/" + ccessoryPath[i];
        }
        UpdataNewsVo.setAccessoryPath(AccessoryPath);
        UpdataNewsVo.setAuthor(newsContent.getAuthor());
        if (newsNotice.getStatus().equals("1")) {
            UpdataNewsVo.setStatus("未提交");
        } else if (newsNotice.getStatus().equals("2")) {
            UpdataNewsVo.setStatus("已发布");
        } else if (newsNotice.getStatus().equals("4")) {
            UpdataNewsVo.setStatus("审核中");
        } else if (newsNotice.getStatus().equals("8")) {
            UpdataNewsVo.setStatus("已下架");
        } else if (newsNotice.getStatus().equals("16")) {
            UpdataNewsVo.setStatus("已删除");
        } else if (newsNotice.getStatus().equals("32")) {
            UpdataNewsVo.setStatus("驳回");
        }

        UpdataNewsVo.setIsStick(newsNotice.getIsStick());
        UpdataNewsVo.setCreateTime(DateUtils.dateTime(newsNotice.getCreateTime()));
        UpdataNewsVo.setOpinion(newsNotice.getOpinion());
        UpdataNewsVo.setId(newsNotice.getId());
        UpdataNewsVo.setVerifier(newsNotice.getVerifier());
        UpdataNewsVo.setCreator(newsNotice.getCreator());
        UpdataNewsVo.setFlow(newsNotice.getFlow());
        UpdataNewsVo.setNewsContent(newsContent.getNewsContent());
        UpdataNewsVo.setNoticeTitle(newsNotice.getNoticeTitle());
        UpdataNewsVo.setType(newsContent.getType());
        UpdataNewsVo.setSource(newsContent.getSource());
        UpdataNewsVo.setNoticeTypeId(newsNotice.getNoticeTypeId());
        if (newsNotice.getNoticeTypeId() == 11) {
            UpdataNewsVo.setNoticeTypeName("公司公告");
        }
        if (newsNotice.getNoticeTypeId() == 12) {
            UpdataNewsVo.setNoticeTypeName("公司新闻");
        }
        if (newsNotice.getNoticeTypeId() == 13) {
            UpdataNewsVo.setNoticeTypeName("业务通知");
        }

        return UpdataNewsVo;
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<NewsNotice> getNewsNoticeListByMap(Map<String, Object> param) throws Exception {
        return newsNoticeMapper.getNewsNoticeListByMap(param);
    }

    /**
     * 修改：审核人员审核后修改
     */
    @Override
    public Integer updateNewsNotice(NewsNotice NewsNotice) throws Exception {


        newsNoticeMapper.updateNewsNotice(NewsNotice);


        return null;
    }


    /**
     * 根据条件查询新闻公告列表
     */
    @Override
    public PageInfo<newsVo> getNewsListByMap(Map<String, Object> param) throws Exception {
        List<newsVo> listnews = new ArrayList<>();

        //设置分页的起始页数和页面容量
        Page<Object> objects = PageHelper.startPage(
                param.get("pageNum") == null ? 1 : (Integer) param.get("pageNum"),
                param.get("pageSize") == null ? 10 : (Integer) param.get("pageSize"));
        List<NewsNotice> newsNoticeslist = newsNoticeMapper.queryNewsList(param);

        for (NewsNotice newsNotice : newsNoticeslist) {
            SysDictType sysDictTypeById = SysDictTypeMapper.getSysDictTypeById(newsNotice.getNoticeTypeId());
            newsVo newsVo = new newsVo();
            //设置信息类型名称
            newsVo.setNoticeTypeName(sysDictTypeById.getDictName());
            //拷贝字段
            BeanUtils.copyProperties(newsNotice, newsVo);
            newsVo.setIsStick("否");
            if (newsNotice.getIsStick() == 1) {
                newsVo.setIsStick("是");
            }
            newsVo.setFlow("已办流程");


            if (newsNotice.getFlow() != null) {
                if (newsNotice.getFlow() == 2) {
                    newsVo.setFlow("待办流程");
                }
            }


            if (newsVo.getStatus().equals("1")) {
                newsVo.setStatus("未提交");
            } else if (newsVo.getStatus().equals("2")) {
                newsVo.setStatus("已发布");
            } else if (newsVo.getStatus().equals("4")) {
                newsVo.setStatus("审核中");
            } else if (newsVo.getStatus().equals("8")) {
                newsVo.setStatus("已下架");
            } else if (newsVo.getStatus().equals("16")) {
                newsVo.setStatus("已删除");
            } else if (newsVo.getStatus().equals("32")) {
                newsVo.setStatus("驳回");
            }
            newsVo.setUpdateTime(DateUtils.dateTime(newsNotice.getUpdateTime()));

            listnews.add(newsVo);
        }
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<newsVo> info = new PageInfo<newsVo>();
        info.setPages(objects.getPages());
        info.setPageNum(objects.getPageNum());
        info.setPageSize(objects.getPageSize());
        info.setTotal(objects.getTotal());
        info.setData(listnews);
        return info;
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
            newsNotice.setStatus("1");
        } else {
            newsNotice.setStatus("4");
        }
        newsNotice.setCreateTime(new Date());
        newsNotice.setCreator(loginUser.getUsername());
        newsNotice.setUpdateTime(new Date());
        newsNotice.setUpdater(loginUser.getUsername());
        newsNotice.setOpinion("");
        newsNotice.setAuditor(AddNewsDto.getAuthor() == null ? " " : AddNewsDto.getAuthor());
        newsNotice.setVerifier("");
        newsNotice.setFlow(2);


        newsNoticeMapper.insertNewsNotice(newsNotice);
        //添加新闻内容表
        NewsContent newsContent = new NewsContent();
        newsContent.setNewsId(newsNotice.getId());
        newsContent.setAuthor(AddNewsDto.getAuthor() == null ? " " : AddNewsDto.getAuthor());
        newsContent.setNewsContent(AddNewsDto.getNewsContent() == null ? "" : AddNewsDto.getNewsContent());
        newsContent.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsContent.setSource(AddNewsDto.getSource() == null ? " " : AddNewsDto.getSource());
        newsContent.setType(AddNewsDto.getType() == null ? " " : AddNewsDto.getType());

        NewsContentMapper.insertNewsContent(newsContent);
        //添加新闻附件表
        NewsAccessory newsAccessory = new NewsAccessory();
        newsAccessory.setNewsId(newsNotice.getId());
        newsAccessory.setAccessoryName(AddNewsDto.getAccessoryName().length() <= 0 ? " " : AddNewsDto.getAccessoryName());
        newsAccessory.setAccessoryPath(AddNewsDto.getAccessoryPath().length() <= 0 ? " " : AddNewsDto.getAccessoryPath());
        Integer integer = NewsAccessoryMapper.insertNewsAccessory(newsAccessory);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改新闻表，新闻内容表，和新闻附件表；返回影响的行数
     */
    @Transactional
    public Integer ModifyNewsNotice(AddNewsDto AddNewsDto) throws Exception {
        Integer integer = null;
        //获取登录当前用户的信息
        LoginUser loginUser = TokenService.getLoginUser(ServletUtils.getRequest());
        //修改新闻表
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(AddNewsDto.getId());
        newsNotice.setIsStick(AddNewsDto.getIsStick());
        newsNotice.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsNotice.setNoticeTypeId(AddNewsDto.getNoticeTypeId());
        //判断这次新增是否是存草稿
        if (AddNewsDto.getIsAdd() == 1) {
            newsNotice.setStatus("1");
        } else {
            newsNotice.setStatus("4");
        }
        newsNotice.setUpdateTime(new Date());
        newsNotice.setUpdater(loginUser.getUsername());
        newsNotice.setFlow(2);
        newsNoticeMapper.updateNewsNotice(newsNotice);
        //修改新闻内容表
        NewsContent newsContent = new NewsContent();
        newsContent.setNewsId(newsNotice.getId());
        newsContent.setAuthor(AddNewsDto.getAuthor());
        newsContent.setNewsContent(AddNewsDto.getNewsContent());
        newsContent.setNoticeTitle(AddNewsDto.getNoticeTitle());
        newsContent.setSource(AddNewsDto.getSource());
        newsContent.setType(AddNewsDto.getType());
        integer = NewsContentMapper.updateNewsContent(newsContent);
        //修改新闻附件表
        if (AddNewsDto.getAccessoryName() != null && AddNewsDto.getAccessoryPath() != null
                && AddNewsDto.getAccessoryName() != "" && AddNewsDto.getAccessoryPath() != "") {
            NewsAccessory newsAccessory = new NewsAccessory();
            newsAccessory.setNewsId(newsNotice.getId());
            newsAccessory.setAccessoryName(AddNewsDto.getAccessoryName());
            newsAccessory.setAccessoryPath(AddNewsDto.getAccessoryPath());
            integer = NewsAccessoryMapper.updateNewsAccessory(newsAccessory);
        }
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
     * 删除：把新闻的状态修改为已下架
     */
    public Integer DeleteNewsNotice(Integer id) throws Exception {
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(id);
        newsNotice.setStatus("8");
        return newsNoticeMapper.updateNewsNotice(newsNotice);
    }

    /**
     * 提交：把新闻的状态修改为审核中
     */
    public Integer sbtNewsNotice(Integer id) throws Exception {
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(id);
        newsNotice.setStatus("4");
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
            ScNewsVo.setUpdateTime(DateUtils.dateTime(newsNotice.getUpdateTime()));

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
