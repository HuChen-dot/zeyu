package com.ruoyi.swhysc.service.infobase;

import com.github.pagehelper.PageInfo;
import com.ruoyi.swhysc.bean.Infobase;
import com.ruoyi.swhysc.bean.dto.frontdto.front_InfoDto;
import com.ruoyi.swhysc.bean.vo.InfobaseVO;

import java.util.List;
import java.util.Map;

/**
 * Founder : 泽宇
 */
public interface InfobaseService {

    /**
     * 根据id查询；返回单个对象
     */
    Infobase getInfobaseById(Long id) throws Exception;

    /**
     * 根据频道id查询数据库内容
     * 并过滤出前端需要的字段
     */
    List<InfobaseVO> getInfobaseListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    Integer getInfobaseCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加Infobase表和Infocontent表信息；返回影响的行数
     */
    Integer AddInfobaseAndInfocontent(front_InfoDto InfoDto) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    Integer ModifyInfobase(Infobase infobase) throws Exception;

    /**
     * 删除： 根据id删除对象；返回影响的行数
     */
    Integer DeleteInfobaseById(Integer id) throws Exception;

    /**
     * 根据条件分页查询；返回分页查询后的多个对象
     */
    PageInfo<Infobase> queryInfobasePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
