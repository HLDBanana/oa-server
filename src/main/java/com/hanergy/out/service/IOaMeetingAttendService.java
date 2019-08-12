package com.hanergy.out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanergy.out.entity.OaMeetingAttend;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
public interface IOaMeetingAttendService extends IService<OaMeetingAttend> {

    /**
     * @Author hld
     * @Description 插入参会人员信息
     * @Date 14:36 2019/5/8
     * @Param [oaMeetingAttend] 参会人员信息
     * @return int
     **/
    public int saveMeetingAttendUser(OaMeetingAttend oaMeetingAttend);

    public int updateMeetingAttend(OaMeetingAttend oaMeetingAttend);

    public int delMeetingAttend(Long meetingId);

    public List<Long> getMeetingAttendUser(Long meetingId, Integer type);

}
