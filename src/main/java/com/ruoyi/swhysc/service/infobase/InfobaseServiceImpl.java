package com.ruoyi.swhysc.service.infobase;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.swhysc.bean.Infobase;
import com.ruoyi.swhysc.bean.Infocontent;
import com.ruoyi.swhysc.bean.dto.frontdto.front_InfoDto;
import com.ruoyi.swhysc.bean.vo.InfobaseVO;
import com.ruoyi.swhysc.mapper.infobase.InfobaseMapper;
import com.ruoyi.swhysc.mapper.infocontent.InfocontentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class InfobaseServiceImpl implements InfobaseService {


    @Resource
    private InfobaseMapper infobaseMapper;

    @Resource
    private InfocontentMapper infocontentMapper;

    @Resource
    private SysUserMapper userMapper;

    /**
     * 根据id查询；返回单个对象
     */
    public Infobase getInfobaseById(Long id) throws Exception {
        return infobaseMapper.getInfobaseById(id);
    }

    /**
     * 根据频道id查询数据库内容
     * 并过滤出前端需要的字段
     */
    public List<InfobaseVO> getInfobaseListByMap(Map<String, Object> param) throws Exception {
        //查询数据库
        List<Infobase> infobaseListByMap = infobaseMapper.getInfobaseListByMap(param);
        //声明一个空集合
        List<InfobaseVO> list = new ArrayList<>();
        //开始遍历过滤
        for (Infobase infobase : infobaseListByMap) {
            InfobaseVO InfobaseVO = new InfobaseVO();
            BeanUtils.copyProperties(infobase, InfobaseVO);
            list.add(InfobaseVO);
        }
        return list;
    }

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getInfobaseCountByMap(Map
                                                 <String, Object> param) throws Exception {
        return infobaseMapper.getInfobaseCountByMap(param);
    }

    /**
     * 添加：根据传入的参数添加Infobase表和Infocontent表信息；返回影响的行数
     */
    @Transactional
    public Integer AddInfobaseAndInfocontent(front_InfoDto InfoDto) throws Exception {
        //声明两个存储对象的空对象
        Infobase infobase = new Infobase();
        Infocontent infocontent = new Infocontent();
        //先把各自对象需要的字段提取复制出来
        BeanUtils.copyProperties(InfoDto, infobase);
        BeanUtils.copyProperties(InfoDto, infocontent);

        //开始封装转换前台传递的数据
        infobase.setCreatetime(new Date());
        //记录当前信息所带附件的个数（这个不会写，先写个假数据）
        infobase.setAttachmentcount(3);
        infobase.setLastaccesstime(new Date());
        infobase.setOriginaltime(new Date());
        infobase.setExpiretime(new Date());
        //要根据用户的id，到用户表中查询用户的姓名和昵称
        SysUser sysUser = userMapper.selectUserById(Long.parseLong(infobase.getPosterid().toString()));
        infobase.setPoster(sysUser.getUserName());
        infobase.setAuthor(sysUser.getNickName());
        infobase.setAccesstimes(0);
        infobase.setRatetimes(0);
        infobase.setSumrate(0);
//        开始向数据库插入数据
        infobaseMapper.insertInfobase(infobase);
        //封装信息正文表数据
        infocontent.setInfoid(infobase.getId());
        //封装正文的格式；（这个不会先写假数据）
        infocontent.setContenttype(1);
        infocontent.setContentsize(infocontent.getContent().length());
        //  开始向数据库插入数据
        Integer integer = infocontentMapper.insertInfocontent(infocontent);
        return integer;
    }

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer ModifyInfobase(Infobase infobase) throws Exception {
        return infobaseMapper.updateInfobase(infobase);
    }

    /**
     * 删除： 根据map删除信息表和信息正文表；返回影响的行数
     */
    @Transactional
    public Integer DeleteInfobaseById(Integer id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("infoid", id);
        //删除正文表信息
        infocontentMapper.deleteInfocontentByMap(map1);
        //删除信息表信息
        return infobaseMapper.deleteInfobaseByMap(map);
    }

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    public PageInfo<Infobase> queryInfobasePageByMap(Map
                                                             <String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
//设置分页的起始页数和页面容量
        PageHelper.startPage(pageNo, pageSize);
//正常查询数据库，mybatis拦截器已经把原始sql拦截下来做好了分页
        List<Infobase> infobaseList = infobaseMapper.getInfobaseListByMap(param);
//把查询出来分页好的数据放进插件的分页对象中
        PageInfo<Infobase> info = new PageInfo<Infobase>(infobaseList);
        return info;
    }

}
