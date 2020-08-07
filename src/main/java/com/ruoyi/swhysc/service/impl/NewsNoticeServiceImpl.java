package com.ruoyi.swhysc.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.NewsNotice;
import com.ruoyi.swhysc.mapper.dao.NewsNoticeMapper;
import com.ruoyi.swhysc.service.NewsNoticeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * 新闻公告表业务层
 */
@Service
public class NewsNoticeServiceImpl implements NewsNoticeService {


@Resource
private NewsNoticeMapper newsNoticeMapper;

/**
* 根据id查询；返回单个对象
*/
public NewsNotice getNewsNoticeById(Long id)throws Exception{
return newsNoticeMapper.getNewsNoticeById(id);
}

/**
*根据条件查询；返回多个对象
*/
public List<NewsNotice>    getNewsNoticeListByMap(Map
<String,Object> param)throws Exception{
return newsNoticeMapper.getNewsNoticeListByMap(param);
}

/**
* 查询数量：根据传入的条件查询目标数量；返回查询的数量
*/
public Integer getNewsNoticeCountByMap(Map
<String,Object> param)throws Exception{
return newsNoticeMapper.getNewsNoticeCountByMap(param);
}

/**
* 添加：根据传入的参数添加信息；返回影响的行数
*/
public Integer AddNewsNotice(NewsNotice newsNotice)throws Exception{
return newsNoticeMapper.insertNewsNotice(newsNotice);
}

/**
* 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
*/
public Integer ModifyNewsNotice(NewsNotice newsNotice)throws Exception{
return newsNoticeMapper.updateNewsNotice(newsNotice);
}

/**
*删除： 根据map删除对象；返回影响的行数
*/
public Integer DeleteNewsNoticeById(Long id)throws Exception{
Map
<String, Object> map = new HashMap
        <String, Object>();
map.put("invid", id);
return newsNoticeMapper.deleteNewsNoticeByMap(map);
}

/**
*根据条件分页查询；返回分页查询后的多个对象
*/
public PageInfo<NewsNotice> queryNewsNoticePageByMap(Map
<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
//设置分页的起始页数和页面容量
PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
List<NewsNotice> newsNoticeList = newsNoticeMapper.getNewsNoticeListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
PageInfo<NewsNotice> info = new  PageInfo<NewsNotice>(newsNoticeList);
return info;
}

}
