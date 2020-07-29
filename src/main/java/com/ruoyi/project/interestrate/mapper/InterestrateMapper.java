package com.ruoyi.project.interestrate.mapper;

import com.ruoyi.project.interestrate.domain.Interestrate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InterestrateMapper {
    /**
     * 根据id查询；返回单个对象
     */
    public Interestrate getInterestrateById(@Param(value = "id") Long id) throws Exception;

    /**
     * 根据条件查询；返回多个对象
     */
    public List<Interestrate> getInterestrateListByMap(Map<String, Object> param) throws Exception;

    /**
     * 查询数量：根据传入的条件查询目标数量；返回查询的数量
     */
    public Integer getInterestrateCountByMap(Map<String, Object> param) throws Exception;

    /**
     * 添加：根据传入的参数添加信息；返回影响的行数
     */
    public Integer insertInterestrate(Interestrate interestrate) throws Exception;

    /**
     * 根据id修改：根据传入的参数修改对应的数据库类；返回影响的行数
     */
    public Integer updateInterestrate(Interestrate interestrate) throws Exception;

    /**
     * 删除： 根据map删除对象；返回影响的行数
     */
    public Integer deleteInterestrateByMap(Map<String, Object> param) throws Exception;

}
