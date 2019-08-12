package com.hanergy.out.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanergy.out.dao.OaMeetingMapper;
import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.service.IOaMeetingService;
import com.hanergy.out.utils.DateUtils;
import com.hanergy.out.vo.MeetingParamVo;
import com.hanergy.out.vo.ZoomMeetingVo;
import org.springframework.stereotype.Service;

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
public class OaMeetingServiceImpl extends ServiceImpl<OaMeetingMapper, OaMeeting> implements IOaMeetingService {


    @Override
    public List<OaMeeting> findOaMeeting(MeetingParamVo paramVo) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        //  .ge("meeting_capacity",paramVo.getNumber())
        queryWrapper.eq("type",1)       //type=1 : 实体会议室
                .eq("state",0)          //状态为0 : 空闲
                .eq("meeting_id",paramVo.getMeetingId())
                .ge("end_time", DateUtils.stringToDate(paramVo.getEndTime(), DateUtils.DATE_TIME_PATTERN))
                .le("begin_time", DateUtils.stringToDate(paramVo.getBeginTime(), DateUtils.DATE_TIME_PATTERN))
                .orderByAsc("duration");

        return  this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<OaMeeting> findFreeOaMeeting(MeetingParamVo paramVo) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        // .ge("meeting_capacity",paramVo.getNumber())
        queryWrapper.eq("meeting_id",paramVo.getMeetingId())
                    .eq("type",paramVo.getType())
                    .eq("state",0)          //状态为0 : 空闲
                    .ge("end_time", DateUtils.stringToDate(paramVo.getEndTime(), DateUtils.DATE_TIME_PATTERN))
                    .le("begin_time", DateUtils.stringToDate(paramVo.getBeginTime(), DateUtils.DATE_TIME_PATTERN))
                    .orderByAsc("meeting_name","duration");
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<OaMeeting> findVirtualOaMeetingWithoutId(MeetingParamVo paramVo) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        // .ge("meeting_capacity",paramVo.getNumber())
        queryWrapper.eq("type",2)       //type=2 : 虚拟会议室
                    .eq("state",0)          //状态为0 : 空闲
                    .ge("end_time", DateUtils.stringToDate(paramVo.getDay() + " " + paramVo.getEndTime()+":00",
                            DateUtils.DATE_TIME_PATTERN))
                    .le("begin_time", DateUtils.stringToDate(paramVo.getDay() + " " + paramVo.getEndTime()+":00",
                            DateUtils.DATE_TIME_PATTERN))
                    .orderByAsc("duration");

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<OaMeeting> VirtualOaMeetingWithoutId(MeetingParamVo paramVo) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        // .ge("meeting_capacity",paramVo.getNumber())
        queryWrapper.eq("type",2)       //type=2 : 虚拟会议室
                .eq("state",0)          //状态为0 : 空闲
                .ge("end_time", DateUtils.stringToDate( paramVo.getEndTime(),
                        DateUtils.DATE_TIME_PATTERN))
                .le("begin_time", DateUtils.stringToDate(paramVo.getEndTime(),
                        DateUtils.DATE_TIME_PATTERN))
                .orderByAsc("meeting_name","duration");

        return this.baseMapper.selectList(queryWrapper);
    }
    @Override
    public List<OaMeeting> VirOaMeetingWithoutId(MeetingParamVo paramVo) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        // .ge("meeting_capacity",paramVo.getNumber())
        queryWrapper.eq("type",2)       //type=2 : 虚拟会议室
                .eq("state",0)          //状态为0 : 空闲
                .ge("end_time", DateUtils.stringToDate( paramVo.getEndTime(),
                        DateUtils.DATE_TIME_PATTERN))
                .le("begin_time", DateUtils.stringToDate(paramVo.getBeginTime(),
                        DateUtils.DATE_TIME_PATTERN))
                .orderByAsc("meeting_name","duration");

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public OaMeeting findMeetingById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public List<OaMeeting> findMeetingByUuid(String uuid) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid",uuid);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<OaMeeting> findAnotherMeetingByUuid(String uuid, Long meetingId) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid",uuid)
                    .ne("id",meetingId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public int delMeetingById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int saveOaMeeting(OaMeeting oaMeeting) {
        return this.baseMapper.insert(oaMeeting);
    }

    @Override
    public int updateOaMeeting(OaMeeting oaMeeting) {
        return this.baseMapper.updateById(oaMeeting);
    }

    @Override
    public Integer getMaxId() {

        return this.baseMapper.getMaxId();
    }

    @Override
    public Integer updateVirtualOaMeeting(ZoomMeetingVo zoomMeeting, Long maxId) {
        //通过id查出基本信息
        OaMeeting oaMeeting = this.baseMapper.selectById(maxId);
        //丰富zoom会议室信息
        oaMeeting.setStartUrl(zoomMeeting.getStartUrl());
        oaMeeting.setJoinUrl(zoomMeeting.getJoinUrl());
        oaMeeting.setZoomId(zoomMeeting.getZoomId());
        oaMeeting.setPassword(zoomMeeting.getPassword());
        //更新数据
        return this.baseMapper.updateById(oaMeeting);
    }

    @Override
    public List<OaMeeting> getMeetingList(Long userId, Integer type,String date) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appointments_id",userId)
                    .ne("state",0)
                    .eq("type",type)
                    .eq("date",DateUtils.stringToDate(date,DateUtils.DATE_PATTERN))
                    .orderByAsc("meeting_id","begin_time");
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<OaMeeting> getAttendMeetingList(Long userId, Integer type, String date) {

        return this.baseMapper.getAttendMeetings(userId,type,date);
    }

    @Override
    public List<OaMeeting> getMeetingList(Integer type,String date) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("state",0)
                    .eq("type",type)
                    .eq("date",DateUtils.stringToDate(date,DateUtils.DATE_PATTERN))
                    .orderByAsc("meeting_id","begin_time");
                    //.orderByDesc("meeting_id","begin_time";

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public OaMeeting getEndEqualBeginData(OaMeeting oaMeeting) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        //
        queryWrapper.eq("meeting_id",oaMeeting.getMeetingId())
                    .eq("end_time", DateUtils.stringToDate(oaMeeting.getBeginTime()))
                    .eq("state",0);         //0：空闲的会议室
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public OaMeeting getBeginEqualEndData(OaMeeting oaMeeting) {
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        //
        queryWrapper.eq("meeting_id",oaMeeting.getMeetingId())
                    .eq("begin_time", DateUtils.stringToDate(oaMeeting.getEndTime()))
                    .eq("state",0);         //0：空闲的会议室
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateMeetingState(Integer state, Long id) {

        return this.baseMapper.updateMeetingState(state,id);
    }

    @Override
    public List<OaMeeting> aliveFreeVirMeeting(String beginTime, String endTime) {

        return this.baseMapper.aliveFreeVirMeeting(beginTime,endTime);
    }

    @Override
    public List<String> meetingDate(String meetingId) {
        return this.baseMapper.meetingDate(meetingId);
    }
}
