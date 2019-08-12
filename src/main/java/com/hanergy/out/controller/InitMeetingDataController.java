package com.hanergy.out.controller;

import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.entity.OaMeetingSet;
import com.hanergy.out.service.IOaMeetingService;
import com.hanergy.out.service.IOaMeetingSetService;
import com.hanergy.out.utils.DateUtils;
import com.hanergy.out.utils.HttpClient;
import com.hanergy.out.utils.IdentityService;
import com.hanergy.out.utils.ZoomApi;
import com.hanergy.out.vo.ZoomUserVo;
import com.msyun.plugins.zoomapi.utils.JWTBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName InitMeetingDataController
 * @Description
 * @Auto HANLIDONG
 * @Date 2019-7-30 9:12)
 */
@RestController
@RequestMapping("/v1/initMeetingData")
public class InitMeetingDataController {

    private static Logger log = LoggerFactory.getLogger(ZoomApi.class);

    @Autowired
    private IOaMeetingSetService oaMeetingSetService;   // 会议室基本信息表

    @Autowired
    private IOaMeetingService oaMeetingService;     // 会议预约表基本信息

    @Autowired
    private IdentityService identityService;        //redis分布式主键


    @GetMapping("/init")
    public void initMeeting(){
        log.info("~~~~~~~~~~~~~~~~~~~~~开始~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // 获取所有的虚拟会议室
        List<OaMeetingSet> oaMeetingSets = oaMeetingSetService.getMeetingByType(2);
        //调用zoom接口获取所有用户列表
        List<ZoomUserVo> zoomUserVos = ZoomApi.getZoomUsers();
        //getZoomUsers();
        // 保存虚拟会议室信息
        List<OaMeetingSet> addList = saveVrtualMeeting(oaMeetingSets,zoomUserVos);
        log.info("~~~~~新增虚拟会议室：~~~~~"+addList.size());
        // 获取所有生效的会议室信息
        List<OaMeetingSet> allMeetingSets = oaMeetingSetService.getAllMeeting();
        //  遍历所有生效会议室，查询缺失哪天会议数据，插入会议数据
        for (int i = 0; i < allMeetingSets.size(); i++){
            List<String> days = hiatusDate(allMeetingSets.get(i));
            log.info("~~~~~相差天数：~~~~~"+days.size());
            for (int j = 0; j < days.size(); j++) {
                // 两个日期相差天数
                long count = DateUtils.getHourbetweenDate(
                        DateUtils.dateToString(new Date(),DateUtils.DATE_PATTERN),days.get(j));
                log.info("~~~~~开始插入：~~~~~"+ days.get(j));
                saveOaMeeting(allMeetingSets.get(i),new Long(count).intValue());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    // 获取数据缺失日期
    public List<String> hiatusDate(OaMeetingSet set){
        // 存放今天往后七天内的日期
        List<String> sevenDays = new ArrayList<>();
        for (int i = 0; i <= 7; i++){
            Date date = DateUtils.addDateDays(new Date(),i);
            String day = DateUtils.dateToString(date,DateUtils.DATE_PATTERN);
            sevenDays.add(day);
        }
        // 获取当前会议室在会议预约表中的数据日期集合
        List<String> days = oaMeetingService.meetingDate(set.getId());
        sevenDays.removeAll(days);
        return sevenDays;
    }

    // oameeting表插入初始数据
    public void saveOaMeeting(OaMeetingSet meetingSet,int days){
        // 获取当前时间30天之后的日期
        Date date = DateUtils.addDateDays(new Date(),days);
        String day = DateUtils.dateToString(date,DateUtils.DATE_PATTERN);
        String beginTime = day + " 00:00:00";
        String endTime = day + " 23:59:00";
        //Date day = DateUtils.stringToDate(DateUtils.dateToString(date),DateUtils.DATE_PATTERN);

        if (meetingSet != null ){
            log.info("开始插入"+day+"基础数据,数据量："+meetingSet.getCreDate());
            //for (int i = 0; i< allMeetingSets.size(); i++){
            //OaMeetingSet meetingSet = allMeetingSets.get(i);
            OaMeeting oaMeeting = new OaMeeting();
            // 从redis获取分布式主键
            Long id = identityService.buildOneId();
            log.info("获取实体会议室id："+id);
            oaMeeting.setId(id);
            oaMeeting.setDate(day);
            oaMeeting.setBeginTime(beginTime);
            oaMeeting.setDuration(1439);
            oaMeeting.setEndTime(endTime);
            oaMeeting.setState(0);
            oaMeeting.setMeetingId(meetingSet.getId());
            oaMeeting.setMeetingName(meetingSet.getMeetingName());
            oaMeeting.setMeetingCapacity(meetingSet.getMeetingCapacity());
            oaMeeting.setType(meetingSet.getType());
            oaMeetingService.saveOaMeeting(oaMeeting);
            //}
        }

    }

    // 保存虚拟会议室信息
    public List<OaMeetingSet> saveVrtualMeeting(List<OaMeetingSet> oaMeetingSets,List<ZoomUserVo> zoomUserVos){
        log.info("~~~~~~~~~~~~~~~~~~~~~开始保存虚拟会议室信息~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        List<OaMeetingSet> addList = new ArrayList<>();
        // 拼接视频会议室名称
        int total = oaMeetingSets.size();
        if (zoomUserVos != null && zoomUserVos.size() > 0) {
            for (int i = 0; i < zoomUserVos.size(); i++) {
                boolean flag = true;
                for (int j = 0; j < oaMeetingSets.size(); j++) {
                    // 判断当前会议室数据是否已存在数据库中
                    if (zoomUserVos.get(i).getId().equals(oaMeetingSets.get(j).getId())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ZoomUserVo userVo = zoomUserVos.get(i);
                    OaMeetingSet meetingSet = new OaMeetingSet();
                    meetingSet.setId(userVo.getId());
                    // zoom账号邮箱缓存到mysql数据库，方便后续管理zoom会议室
                    meetingSet.setEmail(userVo.getEmail());
                    if (total < 10) {
                        meetingSet.setMeetingName("视频会议室00" + total);
                    } else if (total >= 10 && total < 100){
                        meetingSet.setMeetingName("视频会议室0" + total);
                    } else {
                        meetingSet.setMeetingName("视频会议室" + total);
                    }
                    total++;
                    meetingSet.setMeetingCapacity(100);
                    meetingSet.setType(2);
                    // 新增网络会议室 默认状态是不可用
                    meetingSet.setState(0);
                    //meetingSet.setMeetingType("");
                    meetingSet.setCreDate(DateUtils.dateToString(new Date(),DateUtils.DATE_PATTERN));
                    addList.add(meetingSet);
                    log.info("新增视频会议室："+meetingSet);
                    oaMeetingSetService.saveVirtualMeeting(meetingSet);
                }
            }
        }
        return addList;
    }
}
