package com.hanergy.out.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hanergy.out.entity.SysUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * @Author hld
     * @Description 通过用户ids查询用户邮箱信息
     * @Date 14:45 2019/5/13
     * @Param [ids] id集合
     * @return java.util.List<com.hanergy.out.entity.SysUser>
     **/
    public List<String> getUserByIds(List<Long> ids);

    /**
     * @Author hld
     * @Description 通过用户ids查询用户信息
     * @Date 14:45 2019/5/13
     * @Param [ids] id集合
     * @return java.util.List<com.hanergy.out.entity.SysUser>
     **/
    public List<SysUser> getsysUserByIds(List<Long> ids);

    //通过会议id获取所有参会人员信息
    public List<SysUser> getMeetingAttendUser(Long meetingId, Integer type);

    /**
     * @Author hld
     * @Description 通过用户ids查询用户邮箱信息
     * @Date 14:45 2019/5/13
     * @Param [ids] id集合
     * @return java.util.List<com.hanergy.out.entity.SysUser>
     **/
    public SysUser getUserById(Long id);

    /**
     * @Author hld
     * @Description //通过员工编号或者员工邮箱获取用户信息
     * @Date 8:43 2019/6/21
     * @Param [jobNumber, eamil]
     * @return com.hanergy.out.entity.SysUser
     **/
    public SysUser getUserByJobnumberOrEamil(String jobNumber,String email);


}
