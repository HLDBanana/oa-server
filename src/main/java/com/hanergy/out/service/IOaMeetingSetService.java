package com.hanergy.out.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.entity.OaMeetingSet;
import com.hanergy.out.vo.OaMeetingSetVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;

/**
 * <p>
 * 会议室设置
19年5月9日新增 服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
public interface IOaMeetingSetService extends IService<OaMeetingSet> {

    /*
     * @Author hld
     * @Description //TODO
     * @Date 11:20 2019/5/28
     * @Param [oaMeetingSet]  保存会议室信息
     * @return int
     **/
    public int saveOaMeeting(OaMeetingSet oaMeetingSet);

    public int delOaMeeting(String id);

    public int updateOaMeeting(OaMeetingSet oaMeetingSet);
    /*
     * @Author hld
     * @Description //获取启用的实体会议室
     * @Date 9:50 2019/6/1
     * @Param []
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public List<OaMeetingSetVo> getOaMeeting();
    /*
     * @Author hld
     * @Description //获取所有的实体会议室
     * @Date 9:50 2019/6/1
     * @Param []
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public IPage<OaMeetingSetVo> getAllOaMeeting(Integer pageNumber, Integer pageSize);

    /*
     * @Author hld
     * @Description //获取有权限的会议列表
     * @Date 14:14 2019/6/13
     * @Param []
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public List<OaMeetingSetVo> getPowerOaMeeting(Long userId,String deptId,Integer type);

    public List<OaMeetingSetVo> getVirtualMeeting();

    public List<OaMeetingSet> getMeetingByType(Integer type);

    /**
     * @Author hld
     * @Description //获取昨天之前所有生效的会议室
     * @Date 14:37 2019/5/31
     * @Param []
     * @return java.util.List<com.hanergy.out.entity.OaMeetingSet>
     **/
    public List<OaMeetingSet> getAllMeeting();

    /*
     * 会议室id
     */
    public OaMeetingSetVo getOneOaMeeting(String id);

    /*
     * @Author hld
     * @Description //获取用户有权限或者用户和部门为空的数据
     * @Date 9:15 2019/6/24
     * @Param []
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public List<OaMeetingSetVo> getPowerPartMeeting(Long userId,Integer type);

    /*
     * @Author hld
     * @Description //获取用户/部门不为null的数据
     * @Date 9:32 2019/6/24
     * @Param []
     * @return java.util.List<com.hanergy.out.vo.OaMeetingSetVo>
     **/
    public List<OaMeetingSetVo> deptOrUserIsNotNull(Integer type);

    /*
     * @Author hld
     * @Description //通过会议id获取会议室信息
     * @Date 18:28 2019/6/24
     * @Param [id]
     * @return com.hanergy.out.entity.OaMeetingSet
     **/
    public OaMeetingSet getMeetingSetByMeetingId(Long id);

    /**
     * @Author hld
     * @Description //插入虚拟会议室基本信息
     * @Date 14:15 2019/5/31
     * @Param [oaMeetingSet]
     * @return int
     **/
    public int saveVirtualMeeting(OaMeetingSet oaMeetingSet);

}
