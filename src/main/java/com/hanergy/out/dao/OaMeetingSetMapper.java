package com.hanergy.out.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanergy.out.entity.OaMeetingSet;
import com.hanergy.out.vo.OaMeetingSetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会议室设置
19年5月9日新增 Mapper 接口
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@Mapper
public interface OaMeetingSetMapper extends BaseMapper<OaMeetingSet> {

    public List<OaMeetingSetVo> allOaMeeting(
            @Param("start")Integer start,
            @Param("pageSize")Integer pageSize);


    public List<OaMeetingSetVo> getOaMeeting();

    public List<OaMeetingSetVo> getVirtualMeeting();

    public OaMeetingSetVo getOneOaMeeting(@Param("id") String id);

    /*
     * @Author hld
     * @Description //获取有权限的会议室信息
     * @Date 13:58 2019/6/13
     * @Param [userId, deptId]
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public List<OaMeetingSetVo> powerOaMeeting(
            @Param("userId")Long userId,
            @Param("deptId")String deptId,
            @Param("type")Integer type);

    /*
     * @Author hld
     * @Description //获取用户有权限或者用户和部门为空的数据
     * @Date 9:15 2019/6/24
     * @Param []
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public List<OaMeetingSetVo> getPowerPartMeeting(
            @Param("userId") Long userId,
            @Param("type") Integer type);


    public List<OaMeetingSetVo> deptOrUserIsNotNull(@Param("type") Integer type);

    /*
     * @Author hld
     * @Description //通过会议id获取会议室信息
     * @Date 18:28 2019/6/24
     * @Param [id]
     * @return com.hanergy.out.entity.OaMeetingSet
     **/
    public OaMeetingSet getMeetingSetByMeetingId(@Param("id")Long id);

}
