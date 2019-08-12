package com.hanergy.out.dao;

import com.hanergy.out.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-26
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    public List<SysRole> getRoleByJobnumberAndProject(
            @Param("jobNumber")String jobNumber,
            @Param("projectTag")String projectTag);

}
