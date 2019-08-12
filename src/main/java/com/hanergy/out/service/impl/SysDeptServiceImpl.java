package com.hanergy.out.service.impl;

import com.hanergy.out.entity.SysDept;
import com.hanergy.out.dao.SysDeptMapper;
import com.hanergy.out.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-11
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {


    @Override
    public List<SysDept> getDeptByIds(List<String> ids) {

        return this.baseMapper.selectBatchIds(ids);
    }
}
