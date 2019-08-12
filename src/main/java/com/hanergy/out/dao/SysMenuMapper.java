package com.hanergy.out.dao;

import com.hanergy.out.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.security.acl.LastOwnerException;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-26
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    public SysMenu getMenuByRoleIdAndMenuName(
            @Param("roleId")Long roleId,
            @Param("name")String name);

}
