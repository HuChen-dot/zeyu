package com.ruoyi.swhysc.mapper.dao;
import com.ruoyi.swhysc.bean.NewsNotice;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 新闻公告表数据访问层
 */
public interface NewsNoticeMapper {
	/**
	 * 根据id查询；返回单个对象
	 */
	 NewsNotice getNewsNoticeById(@Param(value = "id") Long id)throws Exception;
		
	/**
	 * 根据条件查询；返回多个对象
	 */
	 List<NewsNotice>	getNewsNoticeListByMap(Map<String, Object> param)throws Exception;

	/**
	 * 查询数量：根据传入的条件查询目标数量；返回查询的数量
	 */
	 Integer getNewsNoticeCountByMap(Map<String, Object> param)throws Exception;

	/**
	 * 添加：根据传入的参数添加信息；返回影响的行数
	 */
	 Integer insertNewsNotice(NewsNotice newsNotice)throws Exception;

	/**
	 * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
	 */
	 Integer updateNewsNotice(NewsNotice newsNotice)throws Exception;

	/**
	 *删除： 根据map删除对象；返回影响的行数
	 */
	 Integer deleteNewsNoticeByMap(Map<String, Object> param)throws Exception;

}
