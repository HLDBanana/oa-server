package com.hanergy.out.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanergy.out.dao.OaMeetingAttendMapper;
import com.hanergy.out.entity.OaMeetingAttend;
import com.hanergy.out.service.IOaMeetingAttendService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
@Service
public class OaMeetingAttendServiceImpl extends ServiceImpl<OaMeetingAttendMapper, OaMeetingAttend> implements IOaMeetingAttendService {


    @Override
    public int saveMeetingAttendUser(OaMeetingAttend oaMeetingAttend) {

        //this.baseMapper.updateById(oaMeetingAttend);
        return this.baseMapper.insert(oaMeetingAttend);
    }

    @Override
    public int updateMeetingAttend(OaMeetingAttend oaMeetingAttend) {

       return this.baseMapper.updateById(oaMeetingAttend);
    }

    @Override
    public int delMeetingAttend(Long meetingId) {

        UpdateWrapper<OaMeetingAttend> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("oa_meeting_id",meetingId);
                     //.eq("type",type);
        return this.baseMapper.delete(updateWrapper);
    }

    @Override
    public List<Long> getMeetingAttendUser(Long meetingId,Integer type) {

        List<Long> userIds = new ArrayList<>();
        QueryWrapper<OaMeetingAttend> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("oa_meeting_id",meetingId)
                    .eq("type",type);
        List<OaMeetingAttend> meetingAttends = this.baseMapper.selectList(queryWrapper);
        if (meetingAttends != null && meetingAttends.size() > 0){
            for (OaMeetingAttend attend : meetingAttends){
                userIds.add(attend.getUserId());
            }
        }
        return userIds;
    }
}
