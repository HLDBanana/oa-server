package com.hanergy.out.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanergy.out.entity.OaMeeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
@Mapper
public interface OaMeetingMapper extends BaseMapper<OaMeeting> {

    public Integer getMaxId();

    public List<OaMeeting> getAttendMeetings(@Param("userId")Long userId, @Param("type")Integer type,
                                            @Param("date")String date);

    // 修改会议室状态
    public int updateMeetingState(
            @Param("state")Integer state,
            @Param("id")Long id);

    // 获取空闲生效虚拟会议室
    public List<OaMeeting> aliveFreeVirMeeting(
            @Param("beginTime")String beginTime,
            @Param("endTime")String endTime);

    public List<String> meetingDate(@Param("meetingId")String meetingId);
}
