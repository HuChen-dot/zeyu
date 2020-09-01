package com.rewin.swhysc.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author huanghuang@rewin.com.cn
 * @create 2018-07-16 1:01
 **/
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
