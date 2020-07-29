package com.ruoyi.swhysc.service.channel;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.swhysc.bean.Channel;
import com.ruoyi.swhysc.bean.dto.frontdto.front_ChannelDto;
import com.ruoyi.swhysc.bean.dto.laterdto.later_ChannelDto;
import com.ruoyi.swhysc.bean.extension.ChannelDtoExtension;
import com.ruoyi.swhysc.bean.vo.ChannelVO;
import com.ruoyi.swhysc.exception.SqlException;
import com.ruoyi.swhysc.mapper.channel.ChannelMapper;
import com.ruoyi.swhysc.util.enums.StateCode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Resource
    com.ruoyi.swhysc.util.redis.RedisCache RedisCache;

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    private SysUserMapper userMapper;

    /**
     * @param listInit       存储分类后的结果
     * @param ChannelDtolist 父级标签集合
     * @param listChannelDto 总标签集合
     * @return
     */
    public static void classification(List<ChannelVO> listInit, List<later_ChannelDto> ChannelDtolist, List<later_ChannelDto> listChannelDto) {
        List<later_ChannelDto> listzi = null;
        ChannelVO ChannelVO = null;
        //遍历一级节点
        for (later_ChannelDto Channel : ChannelDtolist) {
            ChannelVO = new ChannelVO();
            ChannelDtoExtension ChannelDtoExtension = new ChannelDtoExtension();
            BeanUtils.copyProperties(Channel, ChannelDtoExtension);
            // IndexDtoExtension.setIndexDto(menu1);
            // 初始化临时子标签的集合容器
            listzi = new ArrayList();
            for (later_ChannelDto dto : listChannelDto) {
                if (dto.getParentid() == Channel.getId()) {
                    listzi.add(dto);
                }
            }
            // 父级标签下的所有子标签；如果为空则代表当前标签下没有子标签，直接反回
            if (listzi == null) {
                return;
            }
            // 避免一级标签没有子标签，一级标签直接存储
            if (Channel.getParentid() == 1 && listzi.size() == 0) {
                ChannelDtoExtension.setChildren(listzi);
                ChannelVO.setRoutes(ChannelDtoExtension);
                listInit.add(ChannelVO);
            }
            // 避免标签重复存储，没有子标签的直接进行下一次循环
            if (listzi.size() == 0) {
                continue;
            }
            ChannelDtoExtension.setChildren(listzi);
            ChannelVO.setRoutes(ChannelDtoExtension);
            listInit.add(ChannelVO);
            // 递归循环开始
            classification(listInit, listzi, listChannelDto);
        }
    }


    /**
     * 初始化频道信息
     *
     * @param far 是否使用缓存
     */
    @Override
    public List<ChannelVO> initChann(boolean far) throws Exception {
        List<Channel> parentChannel = null;
        List<ChannelVO> listInit = null;
        if (far) {
            //首先查询redis缓存中有没有数据，
            listInit = RedisCache.getCacheObject("channelvo");
            //如果有数据直接返回，
            if (listInit != null) {
                if (listInit.size() > 0) {
                    return listInit;
                }
            }
        }
        try {
            Map map = new HashMap();
            //设置参数
            map.put("status", 0);
            //查询出所有的标签
            parentChannel = channelMapper.getChannelListByMap(map);
            //初始化存储转换后对象的集合
            List<later_ChannelDto> parentChannelDto = new ArrayList<>();
            //把Menu转换到IndexDto，去掉多余的字段
            for (Channel Channel : parentChannel) {
                later_ChannelDto ChannelDto = new later_ChannelDto();
                BeanUtils.copyProperties(Channel, ChannelDto);
                parentChannelDto.add(ChannelDto);
            }
            //获得所有的父级标签集合
            List<later_ChannelDto> list = new ArrayList();
            //循环顶级标签的列表，查询顶级标签下的所有子列表
            for (later_ChannelDto ChannelDto : parentChannelDto) {
                if (ChannelDto.getParentid() == 1) {
                    list.add(ChannelDto);
                }
            }
            listInit = new ArrayList<>();
            //调用分类的方法
            this.classification(listInit, list, parentChannelDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlException(StateCode.SQL_FAIL.getCode());
        }

        //向redis中存储值
        if (listInit.size() > 0) {
            RedisCache.setCacheObject("channelvo", listInit);
        }
        return listInit;
    }

    /**
     * 根据id查询；返回单个对象
     */
    public Channel getChannelById(Long id) throws Exception {
        return channelMapper.getChannelById(id);
    }

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Channel> getChannelListByMap(Map
                                                     <String, Object> param) throws Exception {
        return channelMapper.getChannelListByMap(param);
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getChannelCountByMap(Map
                                                <String, Object> param) throws Exception {
        return channelMapper.getChannelCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer AddChannel(front_ChannelDto ChannelDto) throws Exception {
        Channel channel = new Channel();
        BeanUtils.copyProperties(ChannelDto, channel);
        channel.setModifytime(new Date());
        //要根据用户的id，到用户表中查询用户的姓名和昵称
        SysUser sysUser = userMapper.selectUserById(Long.parseLong(channel.getUserid().toString()));
        channel.setUsername(sysUser.getNickName());

        //额外加必须要加的两个字段，不能为空
        channel.setClassorder(1);
        channel.setViewurl("sdsfd");
        Integer integer = channelMapper.insertChannel(channel);
        RedisCache.deleteObject("channelvo");
        initChann(false);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyChannel(Channel channel) throws Exception {
        return channelMapper.updateChannel(channel);
    }

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */

    public Integer DeleteChannelById(Long id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return channelMapper.deleteChannelByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<Channel> queryChannelPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<Channel> channelList = channelMapper.getChannelListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Channel> info = new PageInfo<Channel>(channelList);
        return info;
    }

}
