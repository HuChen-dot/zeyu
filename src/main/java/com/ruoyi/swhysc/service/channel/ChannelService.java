package com.ruoyi.swhysc.service.channel;

import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Channel;
import com.ruoyi.swhysc.bean.dto.frontdto.front_ChannelDto;
import com.ruoyi.swhysc.bean.vo.ChannelVO;

import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface ChannelService {

    /**
     * 初始化频道信息
     */
    List<ChannelVO> initChann(boolean far) throws Exception;

    /**
     * 根据id查询；返回单个对象
     */
    Channel getChannelById(Long id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    List<Channel> getChannelListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getChannelCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    Integer AddChannel(front_ChannelDto ChannelDto) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyChannel(Channel channel) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteChannelById(Long id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<Channel> queryChannelPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
