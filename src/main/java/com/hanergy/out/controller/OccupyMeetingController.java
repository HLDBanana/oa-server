package com.hanergy.out.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.entity.OaMeetingSet;
import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.IOaMeetingService;
import com.hanergy.out.service.IOaMeetingSetService;
import com.hanergy.out.service.IRedisService;
import com.hanergy.out.service.ISysUserService;
import com.hanergy.out.utils.DateUtils;
import com.hanergy.out.utils.IdentityService;
import com.hanergy.out.utils.ListUtils;
import com.hanergy.out.utils.R;
import com.hanergy.out.vo.MeetingParamVo;
import com.hanergy.out.vo.OaMeetingDetail;
import com.hanergy.out.vo.OaMeetingSetVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisInvalidSubscriptionException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName OccupyMeetingController
 * @Description
 * @Auto HANLIDONG
 * @Date 2019-7-18 14:05)
 */
@RestController
@RequestMapping("/v1/occupyMeeting")
@PropertySource("classpath:config/occupy.properties")
public class OccupyMeetingController {

    @Value("${meeitng.occupy.time}")
    private String time;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IOaMeetingSetService oaMeetingSetService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IOaMeetingService oaMeetingService;

    @RequestMapping("/occupyOrNot")
    public void occupyMeeting(@RequestBody MeetingParamVo meetingParamVo){

    }

    /*
     * redis存储数据格式：meetingid_2019-07-18 12:00:00:userid
     * @Author hld
     * @Description //判断会议室时段是否已被占用
     * @Date 15:32 2019-7-18
     * @Param beginTime：开始时间  endTime：结束时间, meetingId：会议室id
     * @return 已被占用返回false
     **/
    public boolean occupyFreeOrNot(String beginTime,String endTime,String meetingId){
        //获取时间段内所有的整点、半点
        List<String> times = DateUtils.timeBetweenDate(beginTime,endTime);
        if (times != null && times.size() > 0){
            for (int i = 0; i < times.size(); i++) {
                // 判断key是否存在
                Boolean exist =  redisService.hasKey(meetingId + "___" + times.get(i));
                //Boolean exist = redisService.hashExists(meetingId,times.get(i));
                if (exist) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * @Author hld
     * @Description //redis 缓存时间占用
     * @Date 17:30 2019-7-18
     * @Param []
     * @return void
     **/
    public void occupyRedisTime(String beginTime,String endTime,String meetingId,String userId){
        //获取时间段内所有的整点、半点
        List<String> times = DateUtils.timeBetweenDate(beginTime,endTime);
        if (times != null && times.size() > 0) {
            for (int i = 0; i < times.size(); i++) {
                //占用会议室时段
                redisService.stringSet(meetingId + "___" + times.get(i),userId);
                // 设置过期时间
                redisService.expire(meetingId + "___" + times.get(i),Long.parseLong(time));
            }
        }
    }

    // 获取redis缓存的时间，并整合时间段
    public List<OaMeetingDetail> getRedisCacheTime(Long userId,Integer type,String day){
        List<OaMeetingDetail> cacheTime = new ArrayList<>();
        Map<String,List<String>> map = new HashMap<>();
        List<OaMeetingSetVo> setVos = oaMeetingSetService.getPowerPartMeeting(userId,type);
        if (setVos != null && setVos.size() > 0){
            for (int i = 0; i < setVos.size(); i++) {
                String meetingId = setVos.get(i).getId();
                // 模糊匹配key
                Set<String> keys = redisService.keys(meetingId+"___"+day+"*");
                for(String key:keys){
                    // 获取key对应value
                    String value = redisService.stringGet(key);
                    // 只获取有过期时间的临时数据，无过期时间的数据是已预约成功的数据
                    Long ttl = redisService.ttlKeyExpire(key);
                    if (ttl > 0 || ttl == -1){
                        if (map.containsKey(value)){
                            List<String> times = map.get(value);
                            times.add(key);
                            map.put(value,times);
                        } else {
                            List<String> times = new ArrayList<>();
                            times.add(key);
                            map.put(value,times);
                        }
                    }
                }
            }
        }
        List<OaMeetingDetail> details = dataIntegration(map);
        if (details != null && details.size() > 0) {
            for (int i = 0; i < details.size(); i++) {
                OaMeetingDetail detail = details.get(i);
                detail.setDate(detail.getBeginTime().split(" ")[0]);
                detail.setBeginTime(detail.getBeginTime().split(" ")[1].substring(0,5));
                detail.setEndTime(detail.getEndTime().split(" ")[1].substring(0,5));
                detail.setState(3);
                SysUser user = sysUserService.getUserById(detail.getAppointmentsId());
                detail.setAppointmentsName(user.getName());
                detail.setAppointmentsPhone(user.getMobile());
                detail.setTopic("占用中，"+300+"s失效");
                // 获取会议室信息
                OaMeetingSet set = oaMeetingSetService.getById(detail.getMeetingId());
                detail.setMeetingName(set.getMeetingName());
                detail.setType(set.getType());
            }
        }
        return details;
    }

    /*
     * @Author hld
     * @Description //持久化redis 会议室时段
     * @Date 16:53 2019-7-22
     * @Param [paramVo]
     * @return com.hanergy.out.utils.R
     **/
    @PostMapping("/persistOccupyKey")
    public R persistOccupyKey(@RequestBody MeetingParamVo paramVo) {
        //将前端传递的时间处理成正常格式
        paramVo.setBeginTime(paramVo.getDay()+" "+paramVo.getBeginTime()+":00");
        paramVo.setEndTime(paramVo.getDay()+" "+paramVo.getEndTime()+":00");
        persistKey(paramVo.getBeginTime(),paramVo.getEndTime(),paramVo.getMeetingId());
        try {
            OaMeeting meeting = oaMeetingService.findMeetingById(Long.parseLong(paramVo.getMeetingId()));
            if (meeting != null) {
                persistKey(paramVo.getBeginTime(),paramVo.getEndTime(),meeting.getMeetingId());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (paramVo.getMeetingSetId() != null && !"".equals(paramVo.getMeetingSetId())) {
            persistKey(paramVo.getBeginTime(),paramVo.getEndTime(),paramVo.getMeetingSetId());
        }
        return R.ok();
    }
    /**
     * @Author hld
     * @Description //移除redis占位key
     * @Date 11:33 2019-7-22
     * @Param []
     * @return com.hanergy.out.utils.R
     **/
    @PostMapping("/removeOccupyKey")
    public R removeOccupyKey(@RequestBody MeetingParamVo paramVo) {
        //将前端传递的时间处理成正常格式
        paramVo.setBeginTime(paramVo.getDay()+" "+paramVo.getBeginTime()+":00");
        paramVo.setEndTime(paramVo.getDay()+" "+paramVo.getEndTime()+":00");

        removekey(paramVo.getBeginTime(),paramVo.getEndTime(),paramVo.getMeetingId());

        try {
            OaMeeting meeting = oaMeetingService.findMeetingById(Long.parseLong(paramVo.getMeetingId()));
            if (meeting != null) {
                removekey(paramVo.getBeginTime(),paramVo.getEndTime(),meeting.getMeetingId());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (paramVo.getMeetingSetId() != null && !"".equals(paramVo.getMeetingSetId().trim())) {
            removekey(paramVo.getBeginTime(),paramVo.getEndTime(),paramVo.getMeetingSetId());
        }
        return R.ok();
    }

    /**
     * @Author hld
     * @Description 取消预约 删除占位key
     * @Date 11:29 2019-7-22
     * @Param [beginTime, endTime, meetingId]
     * @return void
     **/
    public void removekey(String beginTime,String endTime,String meetingId){
        //获取时间段内所有的整点、半点
        List<String> times = DateUtils.timeBetweenDate(beginTime,endTime);
        if (times != null && times.size() > 0) {
            for (int i = 0; i < times.size(); i++) {

                redisService.remove(meetingId+"___"+times.get(i).substring(0,19));
            }
        }
    }

    /**
     * @Author hld
     * @Description 预约成功 取消过期时间
     * @Date 11:29 2019-7-22
     * @Param [beginTime, endTime, meetingId]
     * @return void
     **/
    public void persistKey(String beginTime,String endTime,String meetingId){
        //获取时间段内所有的整点、半点
        List<String> times = DateUtils.timeBetweenDate(beginTime,endTime);
        if (times != null && times.size() > 0) {
            for (int i = 0; i < times.size(); i++) {
                redisService.persist(meetingId+"___"+times.get(i));
            }
        }
    }


    /**
     * @Author hld
     * @Description //获取过期时间
     * @Date 10:37 2019-7-24
     * @Param   beginTime：开始时间
     *          endTime：结束时间
     *          meetingId：会议室id
     * @return 过期时间
     **/
    @PostMapping("/ttlTime")
    public R ttlTime(@RequestBody MeetingParamVo paramVo){
//            @RequestParam("beginTime")String beginTime,
//                     @RequestParam("day")String day,
//                     @RequestParam("endTime")String endTime,
//                     @RequestParam("meetingId")String meetingId){
        String beginTime = paramVo.getDay() + " " + paramVo.getBeginTime() + ":00";
        String endTime = paramVo.getDay() + " " + paramVo.getEndTime() + ":00";

        //获取时间段内所有的整点、半点
        List<String> times = DateUtils.timeBetweenDate(beginTime,endTime);
        Long min = (long)10000;
        if (times != null && times.size() > 0) {
            for (int i = 0; i < times.size(); i++) {
                Long ttl = redisService.ttlKeyExpire(paramVo.getMeetingId()+"___"+times.get(i));
                if (ttl < min) {
                    min = ttl;
                }
            }
        } else {
            min = (long)-1;
        }
        return R.ok(200,min);
    }

    // 合并占用会议室数据
    public List<OaMeetingDetail> dataIntegration(Map<String,List<String>> map){
        List<OaMeetingDetail> detailList = new ArrayList<>();
        for (String key:map.keySet()){
            List<String> values = map.get(key);
            Collections.sort(values);
            if (values !=null && values.size() > 0){
                String beginValue = values.get(0);
                //剩余时间
                Long beginttl = redisService.ttlKeyExpire(beginValue);
                String beginTime = beginValue.split("___")[1];
                String beginMeetingId = beginValue.split("___")[0];

                OaMeetingDetail detail = new OaMeetingDetail();
                detail.setBeginTime(beginTime);
                detail.setEndTime(DateUtils.dateToString(DateUtils.addDateMinutes(DateUtils.stringToDate(beginTime),30)));
                detail.setMeetingId(beginMeetingId);
                detail.setAppointmentsId(Long.parseLong(key));

                //计算时长
                int duration = 0;
                duration = (int) DateUtils.getMinutebetweenDate(detail.getBeginTime(),detail.getEndTime());
                detail.setDuration(duration);
                // 如果只有一个元素，直接添加到集合中去
                if (values.size() == 1){
                    detailList.add(detail);
                }
                for (int i = 1; i < values.size(); i++) {
                    Long endttl = redisService.ttlKeyExpire(values.get(i));
                    String endTime = values.get(i).split("___")[1];
                    String endMeetingId = values.get(i).split("___")[0];
                    //String meetingId = beginValue.split("_")[0];
                    if (beginMeetingId.compareTo(endMeetingId) == 0 &&
                            detail.getEndTime().compareTo(endTime) == 0 &&
                            ListUtils.absoluteDiff(beginttl,endttl)){ //0:meetingid  1:time
                        detail.setEndTime(DateUtils.dateToString(DateUtils.addDateMinutes(DateUtils.stringToDate(endTime),30)));
                        //计算时长
                        duration = (int) DateUtils.getMinutebetweenDate(detail.getBeginTime(),detail.getEndTime());
                        detail.setDuration(duration);
                    } else {
                        beginttl = endttl;
                        beginMeetingId = endMeetingId;
                        detailList.add(detail);
                        detail = new OaMeetingDetail();
                        detail.setBeginTime(endTime);
                        detail.setEndTime(DateUtils.dateToString(DateUtils.addDateMinutes(DateUtils.stringToDate(endTime),30)));
                        detail.setMeetingId(endMeetingId);
                        detail.setAppointmentsId(Long.parseLong(key));
                        //计算时长
                        duration = (int) DateUtils.getMinutebetweenDate(detail.getBeginTime(),detail.getEndTime());
                        detail.setDuration(duration);
                    }

                    if (i == values.size()-1){
                        detailList.add(detail);
                    }
                }
            }
        }
        return detailList;
    }

    public R persist(){
        return  null;
    }

}
