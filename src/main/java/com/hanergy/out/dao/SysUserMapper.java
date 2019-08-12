package com.hanergy.out.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanergy.out.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    //通过会议id获取所有参会人员信息
    public List<SysUser> getMeetingAttendUser(@Param("meetingId") Long meetingId, @Param("type") Integer type);
}
