package com.hanergy.out.service.impl;

import com.hanergy.out.entity.SysMenu;
import com.hanergy.out.dao.SysMenuMapper;
import com.hanergy.out.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-26
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public SysMenu getMenuByRoleIdAndMenuName(Long roleId, String name) {
        return this.baseMapper.getMenuByRoleIdAndMenuName(roleId,name);
    }
}
