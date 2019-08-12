package com.hanergy.out.service.impl;

import com.hanergy.out.entity.SysRole;
import com.hanergy.out.dao.SysRoleMapper;
import com.hanergy.out.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public List<SysRole> getRoleByJobnumberAndProject(String jobNumber, String projectTag) {

        return this.baseMapper.getRoleByJobnumberAndProject(jobNumber,projectTag);
    }
}
