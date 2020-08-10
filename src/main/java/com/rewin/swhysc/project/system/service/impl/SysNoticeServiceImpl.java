package com.rewin.swhysc.project.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rewin.swhysc.project.system.domain.SysNotice;
import com.rewin.swhysc.project.system.mapper.SysNoticeMapper;
import com.rewin.swhysc.project.system.service.ISysNoticeService;
import com.rewin.swhysc.util.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author rewin
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

    @Resource
    private SysNoticeMapper noticeMapper;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {

        return noticeMapper.selectNoticeById(noticeId);
    }


    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */

    public List<SysNotice> selectNoticeList(SysNotice notice) {

        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 根据频道id，分页查询公告列表
     *
     * @param menuid 频道id
     * @return 公告集合
     */
    public PageInfo<SysNotice> selectNoticeListpage(Integer menuid, Integer pageNo, Integer pageSize) {
        SysNotice notice = new SysNotice();
        notice.setMenuid(menuid);
        //设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
        List<SysNotice> sysNotices = noticeMapper.selectNoticeList(notice);
        //把查询出来分页好的数据放进插件的分页对象中
        PageInfo<SysNotice> info = new PageInfo<SysNotice>(sysNotices);
        return info;
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice) {
        notice.setCreateTime(new Date());//新增时间
        notice.setCreateBy(SecurityUtils.getUsername());//新增人
        return noticeMapper.insertSysNotice(notice);
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice) {

        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId) {

        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(Long[] noticeIds) {

        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
