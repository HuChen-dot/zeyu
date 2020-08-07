package com.ruoyi.swhysc.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.NewsAccessory;
import com.ruoyi.swhysc.mapper.dao.NewsAccessoryMapper;
import com.ruoyi.swhysc.service.NewsAccessoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * 新闻附件表数据业务层
 */
@Service
public class NewsAccessoryServiceImpl implements NewsAccessoryService {


@Resource
private NewsAccessoryMapper newsAccessoryMapper;

/**
* 根据id查询；返回单个对象
*/
public NewsAccessory getNewsAccessoryById(Long id)throws Exception{
return newsAccessoryMapper.getNewsAccessoryById(id);
}

/**
*根据条件查询；返回多个对象
*/
public List<NewsAccessory>    getNewsAccessoryListByMap(Map
<String,Object> param)throws Exception{
return newsAccessoryMapper.getNewsAccessoryListByMap(param);
}

/**
* 查询数量：根据传入的条件查询目标数量；返回查询的数量
*/
public Integer getNewsAccessoryCountByMap(Map
<String,Object> param)throws Exception{
return newsAccessoryMapper.getNewsAccessoryCountByMap(param);
}

/**
* 添加：根据传入的参数添加信息；返回影响的行数
*/
public Integer AddNewsAccessory(NewsAccessory newsAccessory)throws Exception{
return newsAccessoryMapper.insertNewsAccessory(newsAccessory);
}

/**
* 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
*/
public Integer ModifyNewsAccessory(NewsAccessory newsAccessory)throws Exception{
return newsAccessoryMapper.updateNewsAccessory(newsAccessory);
}

/**
*删除： 根据map删除对象；返回影响的行数
*/
public Integer DeleteNewsAccessoryById(Long id)throws Exception{
Map
<String, Object> map = new HashMap
        <String, Object>();
map.put("invid", id);
return newsAccessoryMapper.deleteNewsAccessoryByMap(map);
}

/**
*根据条件分页查询；返回分页查询后的多个对象
*/
public PageInfo<NewsAccessory> queryNewsAccessoryPageByMap(Map
<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
//设置分页的起始页数和页面容量
PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
List<NewsAccessory> newsAccessoryList = newsAccessoryMapper.getNewsAccessoryListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
PageInfo<NewsAccessory> info = new  PageInfo<NewsAccessory>(newsAccessoryList);
return info;
}

}
