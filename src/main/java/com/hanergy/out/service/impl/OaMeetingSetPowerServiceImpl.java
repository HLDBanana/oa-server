package com.hanergy.out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hanergy.out.entity.OaMeetingSetPower;
import com.hanergy.out.dao.OaMeetingSetPowerMapper;
import com.hanergy.out.service.IOaMeetingSetPowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-11
 */
@Service
public class OaMeetingSetPowerServiceImpl extends ServiceImpl<OaMeetingSetPowerMapper, OaMeetingSetPower> implements IOaMeetingSetPowerService {

    @Override
    public int saveOaPower(OaMeetingSetPower power) {

        return this.baseMapper.insert(power);
    }

    @Override
    public int delOaPowerBySetId(String oaMeetingSetId) {
        UpdateWrapper<OaMeetingSetPower> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("oa_meeting_set_id",oaMeetingSetId);

        return this.baseMapper.delete(updateWrapper);
    }
}
