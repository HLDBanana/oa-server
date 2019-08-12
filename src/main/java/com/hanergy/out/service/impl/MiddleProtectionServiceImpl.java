package com.hanergy.out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hanergy.out.entity.MiddleProtection;
import com.hanergy.out.dao.MiddleProtectionMapper;
import com.hanergy.out.service.IMiddleProtectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 劳动保护  
2019年7月15日杨国栋创建 服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-07-15
 */
@Service
public class MiddleProtectionServiceImpl extends ServiceImpl<MiddleProtectionMapper, MiddleProtection> implements IMiddleProtectionService {

    @Override
    public IPage<MiddleProtection> getMiddleProtection(Integer pageSize, Integer current) {
        Page<MiddleProtection> page = new Page<>(current,pageSize);
        QueryWrapper<MiddleProtection> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        IPage<MiddleProtection> proPage = this.baseMapper.selectPage(page,queryWrapper);
        return proPage;
    }
}
