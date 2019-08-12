package com.hanergy.out.service;

import com.hanergy.out.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-26
 */
public interface ISysMenuService extends IService<SysMenu> {

    /*
     * @Author hld
     * @Description //通过角色id和菜单姓名获取菜单信息
     * @Date 17:27 2019-6-26
     * @Param [roleId, name]
     * @return com.hanergy.out.entity.SysMenu
     **/
    public SysMenu getMenuByRoleIdAndMenuName(Long roleId,String name);
}
