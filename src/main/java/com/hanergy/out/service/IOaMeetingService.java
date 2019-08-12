package com.hanergy.out.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.vo.MeetingParamVo;
import com.hanergy.out.vo.ZoomMeetingVo;
import org.apache.ibatis.annotations.Param;

import java.security.acl.LastOwnerException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
public interface IOaMeetingService extends IService<OaMeeting> {

    /*
     * @Author hld
     * @Description 获取时间段内空闲的会议室
     * @Date 16:40 2019/5/7
     * @Param [paramVo] 会议室条件
     * @return java.util.List<com.hanergy.out.entity.OaMeeting>
     **/
     public List<OaMeeting> findOaMeeting(MeetingParamVo paramVo);

    /*
     * @Author hld
     * @Description 获取会议室当前时段是否空闲
     * @Date 16:40 2019/5/7
     * @Param [paramVo] 会议室条件
     * @return java.util.List<com.hanergy.out.entity.OaMeeting>
     **/
    public List<OaMeeting> findFreeOaMeeting(MeetingParamVo paramVo);
    /*
     * @Author hld
     * @Description 获取当前时段空闲的所有会议室
     * @Date 16:40 2019/5/7
     * @Param [paramVo] 会议室条件
     * @return java.util.List<com.hanergy.out.entity.OaMeeting>
     **/
    public List<OaMeeting> findVirtualOaMeetingWithoutId(MeetingParamVo paramVo);

    public List<OaMeeting> VirtualOaMeetingWithoutId(MeetingParamVo paramVo);

    public List<OaMeeting> VirOaMeetingWithoutId(MeetingParamVo paramVo);
    /*
     * @Author hld
     * @Description 根据会议室id获取空闲会议室详情
     * @Date 11:19 2019/5/8
     * @Param [id] 会议室id
     * @return com.hanergy.out.entity.OaMeeting
     **/
    public OaMeeting findMeetingById(Long id);

    /*
     * @Author hld
     * @Description 获取相同uuid的会议信息
     * @Date 11:19 2019/5/8
     * @Param [uuid] uuid
     * @return com.hanergy.out.entity.OaMeeting
     **/
    public List<OaMeeting> findMeetingByUuid(String uuid);

    /*
     * @Author hld
     * @Description 获取相同uuid的会议信息
     * @Date 11:19 2019/5/8
     * @Param [uuid] uuid
     * @return com.hanergy.out.entity.OaMeeting
     **/
    public List<OaMeeting> findAnotherMeetingByUuid(String uuid, Long meetingId);
    /*
     * @Author hld
     * @Description 根据会议室id删除会议室数据
     * @Date 11:19 2019/5/8
     * @Param [id] 会议室id
     * @return com.hanergy.out.entity.OaMeeting
     **/
    public int delMeetingById(Long id);

    /*
     * @Author hld
     * @Description 保存预约之后拆分的会议室信息
     * @Date 11:24 2019/5/8
     * @Param [oaMeeting] 会议室信息
     * @return int
     **/
    public int saveOaMeeting(OaMeeting oaMeeting);

    /*
     * @Author hld
     * @Description 更新会议室信息
     * @Date 11:24 2019/5/8
     * @Param [oaMeeting] 会议室信息
     * @return int
     **/
    public int updateOaMeeting(OaMeeting oaMeeting);

    /*
     * @Author hld
     * @Description 获取最新插入数据的id
     * @Date 14:43 2019/5/8
     * @Param []
     * @return int
     **/
    public Integer getMaxId();

    /*
     * @Author hld
     * @Description 更新虚拟会议室信息
     * @Date 10:12 2019/5/9
     * @Param [zoomMeeting] 虚拟会议室参会url id等信息
     * @return java.lang.Integer
     **/
    public Integer updateVirtualOaMeeting(ZoomMeetingVo zoomMeeting, Long maxId);


    /**
     * @Author hld
     * @Description 获取用户预约的所有会议列表
     * @Date 11:52 2019/5/9
     * @Param [userId] 预约人id
     * @return java.util.List<com.hanergy.out.entity.OaMeeting>
     **/
    public List<OaMeeting> getMeetingList(Long userId,Integer type,String date);

    /**
     * @Author hld
     * @Description 获取用户预约的所有会议列表
     * @Date 11:52 2019/5/9
     * @Param [userId] 预约人id
     * @return java.util.List<com.hanergy.out.entity.OaMeeting>
     **/
    public List<OaMeeting> getAttendMeetingList(Long userId,Integer type,String date);

    /**
     * @Author hld
     * @Description 获取所有已约会议信息
     * @Date 11:52 2019/5/9
     * @Param [userId] 预约人id
     * @return java.util.List<com.hanergy.out.entity.OaMeeting>
     **/
    public List<OaMeeting> getMeetingList(Integer type,String date);

    /**
     * @Author hld
     * @Description 获取结束时间=会议预约开始时间的空闲会议室数据，
     * @Date 14:43 2019/5/9
     * @Param [oaMeeting]
     * @return com.hanergy.out.entity.OaMeeting
     **/
    public OaMeeting getEndEqualBeginData(OaMeeting oaMeeting);

    /**
     * @Author hld
     * @Description 获取开始时间=会议预约结束时间的空闲会议室数据，
     * @Date 14:43 2019/5/9
     * @Param [oaMeeting]
     * @return com.hanergy.out.entity.OaMeeting
     **/
    public OaMeeting getBeginEqualEndData(OaMeeting oaMeeting);

    // 修改会议室状态
    public int updateMeetingState(Integer state, Long id);

    // 获取空闲生效虚拟会议室
    public List<OaMeeting> aliveFreeVirMeeting(String beginTime,String endTime);

    // 获取会议室日期数据
    public List<String> meetingDate(String meetingId);


}
