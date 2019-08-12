package com.hanergy.out.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hanergy.out.entity.Document;
import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.entity.OaMeetingAttend;
import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.*;
import com.hanergy.out.utils.*;
import com.hanergy.out.vo.MeetingListVo;
import com.hanergy.out.vo.MeetingParamVo;
import com.hanergy.out.vo.OaMeetingDetail;
import com.hanergy.out.vo.ZoomMeetingVo;
import com.msyun.plugins.zoomapi.utils.JWTBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
@RestController
@RequestMapping("/v1/oaMeeting")
@Transactional
@Api(tags = "会议室预约")
public class OaMeetingController {

    Logger log = LoggerFactory.getLogger(OaMeetingController.class);

    @Autowired
    private IOaMeetingService oaMeetingService;     //会议室详情表

    @Autowired
    private IOaMeetingAttendService oaMeetingAttendService; //参会人员信息表

    @Autowired
    private ISysUserService sysUserService;         //用户表

    @Autowired
    private MailOutlook mailOutlook;                //邮件发送

    @Autowired
    private IRedisService redisService;             //redis

    @Autowired
    private IdentityService identityService;        // redis分布式主键

    @Autowired
    private IDocumentService documentService;       //附件表

    @Autowired
    private OccupyMeetingController occupyMeetingController;


    //@RequestBody可以接json数据，如果前端发来的数据是key：value类型 就会报错
    @ApiOperation(value="查询空闲会议室",notes="回去符合条件的空闲会议室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="paramVo",value="开始、结束时间、参会人数、会议类型",required=true,paramType="query")
    })
    @PostMapping("/findFreeMeeting")
    public R findFreeMeeting(@RequestBody MeetingParamVo paramVo/*, @RequestParam("pageSize")Integer pageSize,
                                @RequestParam("pageNumber") Integer pageNumber*/){
        //1、查询符合条件的实体会议室
        List<OaMeeting> oaMeetings = null;
        if (paramVo.getType() == 1){
            //1、查询符合条件的实体会议室
            oaMeetings = oaMeetingService.findOaMeeting(paramVo);
        }else{
        }
        return R.ok(200,oaMeetings);
    }

    //@RequestBody可以接json数据，如果前端发来的数据是key：value类型 就会报错
    @ApiOperation(value="当前时段虚拟会议室是否空闲",notes="当前时段虚拟会议室是否空闲")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="会议类型",required=true,paramType="query"),
            @ApiImplicitParam(name="meetingId",value="会议室id",required=true,paramType="query"),
            @ApiImplicitParam(name="day",value="开始时间 (yyyy-MM-dd)",required=true,paramType="query"),
            @ApiImplicitParam(name="beginTime",value="开始时间 (HH:mm)",required=true,paramType="query"),
            @ApiImplicitParam(name="endTime",value="结束时间 (HH:mm)",required=true,paramType="query")
    })
    @PostMapping("/meetingFreeOrNot")
    public R virtualMeetingFreeOrNot(@RequestBody MeetingParamVo paramVo){
        boolean flag = false;
        if(paramVo.getMeetingId() != null){
            if (paramVo.getType() == 3){    //实体+虚拟

                paramVo.setType(1);
                List<OaMeeting> virtualMeetings = oaMeetingService.findVirtualOaMeetingWithoutId(paramVo);
                paramVo.setBeginTime(paramVo.getDay()+" "+paramVo.getBeginTime()+":00");
                paramVo.setEndTime(paramVo.getDay()+" "+paramVo.getEndTime()+":00");
                List<OaMeeting> oaMeetings = oaMeetingService.findFreeOaMeeting(paramVo);
                //实体+虚拟会议室都空闲
                if (oaMeetings != null && oaMeetings.size() > 0 && virtualMeetings != null && virtualMeetings.size() > 0){
                    return R.ok(200,true);
                }
            } else {
                paramVo.setBeginTime(paramVo.getDay()+" "+paramVo.getBeginTime()+":00");
                paramVo.setEndTime(paramVo.getDay()+" "+paramVo.getEndTime()+":00");
                List<OaMeeting> oaMeetings = oaMeetingService.findFreeOaMeeting(paramVo);
                if (oaMeetings != null && oaMeetings.size() > 0){
                    return R.ok(200,true);
                }
            }
        } else {
            //2、查询符合条件的虚拟会议室
            List<OaMeeting> oaMeetings = oaMeetingService.findVirtualOaMeetingWithoutId(paramVo);
            if (oaMeetings != null && oaMeetings.size() > 0){
                return R.ok(200,true);
            }
        }
        return R.ok(200,false);
    }
//@RequestBody MeetingParamVo paramVo
    @ApiOperation(value="当前时段虚拟会议室是否空闲",notes="当前时段虚拟会议室是否空闲")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="会议类型",required=true,paramType="query"),
            @ApiImplicitParam(name="meetingId",value="会议室id",required=true,paramType="query"),
            @ApiImplicitParam(name="day",value="开始时间 (yyyy-MM-dd)",required=true,paramType="query"),
            @ApiImplicitParam(name="beginTime",value="开始时间 (HH:mm)",required=true,paramType="query"),
            @ApiImplicitParam(name="endTime",value="结束时间 (HH:mm)",required=true,paramType="query")
    })
    @GetMapping("/meetingFreeOrNot1")
    public R virtualMeetingFreeOrNot1(@RequestParam("beginTime") String beginTime,
                                      @RequestParam("endTime")String endTime,
                                      @RequestParam("day")String day,
                                      @RequestParam("meetingId") String meetingId,
                                      @RequestParam("send")Integer send,
                                      @RequestParam("type")Integer type,
                                      @RequestParam("userId")Long userId){
        boolean virtual = false;
        boolean real = false;
        MeetingParamVo paramVo = new MeetingParamVo();
        paramVo.setBeginTime(beginTime);
        paramVo.setEndTime(endTime);
        paramVo.setDay(day);
        paramVo.setMeetingId(meetingId);
        paramVo.setSend(send);
        paramVo.setType(type);
        paramVo.setUserId(userId);


        paramVo.setBeginTime(paramVo.getDay()+" "+paramVo.getBeginTime()+":00");
        paramVo.setEndTime(paramVo.getDay()+" "+paramVo.getEndTime()+":00");
        if (paramVo.getType() == 3){    //实体+虚拟
            OaMeeting virtualMeeting = null;
            Long virtualId = null;
            String virtualMeetingId = null;
            paramVo.setType(1);
            // 获取当前时段空闲的虚拟会议室列表 please do NOT reply
            List<OaMeeting> virtualMeetings = oaMeetingService.aliveFreeVirMeeting(
                    paramVo.getBeginTime(), paramVo.getEndTime());
            //List<OaMeeting> virtualMeetings = oaMeetingService.VirtualOaMeetingWithoutId(paramVo);
            //~~~~~~~~~~~ redis缓存占位
            if (virtualMeetings != null && virtualMeetings.size() > 0) {
                for (int i = 0; i < virtualMeetings.size(); i++) {
                    boolean occupy = occupyMeetingController.occupyFreeOrNot(paramVo.getBeginTime(),paramVo.getEndTime(),virtualMeetings.get(i).getMeetingId());
                    if (occupy){
                        //存储占用会议室id
                        virtualId = virtualMeetings.get(i).getId();
                        virtualMeeting = virtualMeetings.get(i);
                        virtualMeetingId = virtualMeetings.get(i).getMeetingId();
                        virtual = true;
                        break;
                    }
                }
            }
            real = occupyMeetingController.occupyFreeOrNot(paramVo.getBeginTime(),paramVo.getEndTime(),paramVo.getMeetingId());
            //实体+虚拟会议室都空闲
            if (virtual && real){
                //send=0:不占用  send=1：占用
                if (paramVo.getSend() == 1){
                    // 实体会议室占用
                    occupyMeetingController.occupyRedisTime(paramVo.getBeginTime(),paramVo.getEndTime(),
                            paramVo.getMeetingId(),paramVo.getUserId().toString());
                    // 网络会议室占用
                    occupyMeetingController.occupyRedisTime(paramVo.getBeginTime(),paramVo.getEndTime(),
                            virtualMeetingId,paramVo.getUserId().toString());
                }
                return R.ok(200,virtualMeeting);
            }
        } else {
            real = occupyMeetingController.occupyFreeOrNot(paramVo.getBeginTime(),paramVo.getEndTime(),paramVo.getMeetingId());
            //实体/虚拟会议室空闲
            if (real){
                //send=0:不占用  send=1：占用
                if (paramVo.getSend() == 1){
                    occupyMeetingController.occupyRedisTime(paramVo.getBeginTime(),paramVo.getEndTime()
                            ,paramVo.getMeetingId(),paramVo.getUserId().toString());
                }
                return R.ok(200,null);
            }
        }
        return R.error(500,"会议室已被占用");
    }




    @ApiOperation(value="会议室预约",notes="预约实体、虚拟会议室")
    @ApiImplicitParams({
            @ApiImplicitParam(name="meetingParamVo",value="实体、虚拟会议室id、开始、结束时间、参会人员ids、会议类型、会议主题、会议内容",required=true,paramType="query")
    })
    @PostMapping("/createMeeting")
    public R createMeeting(@RequestBody MeetingParamVo meetingParamVo){
        Map<String,Object> map = new HashMap<>();
        /*
         * 1、根据实体、虚拟会议室id对会议室进行拆分，然后改变会议室状态
         *  1）根据id查询会议室详细信息，
         *  2）代码拆分为多条数据，
         *  3）删除原数据，插入数据库拆分后的新数据
         * 2、插入参会人员信息
         * 3、调用zoom创建会议接口，创建会议
         * 4、对参会人员发送outlook邮件预约会议
         */
        //将前端传递的时间处理成正常格式
        meetingParamVo.setBeginTime(meetingParamVo.getDay()+" "+meetingParamVo.getBeginTime()+":00");
        meetingParamVo.setEndTime(meetingParamVo.getDay()+" "+meetingParamVo.getEndTime()+":00");
        Long maxid = null;

        SysUser compereUser = sysUserService.getUserById(meetingParamVo.getCompere());
        //会议内容，如果有虚拟会议需要拼接start_url、join_url等：
        String content = "*此邮件为系统自动触发，请不要回复本邮件。\\n\\n" +
                "您好!\\n" +
                "\\n" +
                compereUser.getName() +"诚挚地邀请您参加此次会议。请您根据以下会议信息准时参加会议：\\n" +
                "\\n" +
                "\uF077\t会议时间： (GMT+8, CHINA BEIJING) " + meetingParamVo.getBeginTime() + " - " + meetingParamVo.getEndTime() + "\\n\\n";
        String english = "*This email is generated by system, please do NOT reply. \\n\\n" +
                "You are sincerely invited to join this meeting by " +
                PinyinUtils.convertHanzi2Pinyin(compereUser.getName(),true) + ". Please follow below meeting information to join the meeting on time:\\n" +
                "\\n" +
                "\uF077\tMeeting Time: (GMT+8, CHINA BEIJING) " + meetingParamVo.getBeginTime() + " - " + meetingParamVo.getEndTime() + "\\n\\n";

        //主持人邮件内容
        String startContent = meetingParamVo.getContent();
        //会议地点
        String location = "";

        //生成uuid用来关联实体、虚拟会议室
        String uuid = UUID.randomUUID().toString();
        //预约实体会议室
        if (meetingParamVo.getType() == 1){
            // 获取当前会议室符合条件的数据id
            List<OaMeeting> oaMeetings = oaMeetingService.findOaMeeting(meetingParamVo);
            if (oaMeetings != null && oaMeetings.size() > 0){
                //修改实体会议室状态信息 实体会议室待审核状态：2
                maxid = splitMeetingData(meetingParamVo,oaMeetings.get(0).getId(),uuid,2);
                location = location + meetingParamVo.getLocation();
                if (maxid == null){
                    return R.error();
                }
                // 返回实体会议室id
                map.put("meetingId",maxid);
                //保存参会人员信息
                saveMeetingAttendUser(meetingParamVo.getUserList(),maxid,1);
                //保存抄送人员信息
                saveMeetingAttendUser(meetingParamVo.getCcUserList(),maxid,2);
                // 保存附件信息
                saveDocument(meetingParamVo.getDocuments(),maxid);
            }
        } else if (meetingParamVo.getType() == 2){  // 预约虚拟会议室
            List<OaMeeting> virtualOaMeetings = oaMeetingService.findFreeOaMeeting(meetingParamVo);
            if (virtualOaMeetings != null && virtualOaMeetings.size() > 0) {
                //修改虚拟会议室状态信息  会议室id 视频会议室不需要审批
                maxid = splitMeetingData(meetingParamVo,virtualOaMeetings.get(0).getId(),uuid,1);
                location = location + meetingParamVo.getLocation();
                if (maxid == null){
                    return R.error();
                }
                // 返回虚拟会议室id
                map.put("virtualMeetingId",maxid);
                //保存参会人员信息
                saveMeetingAttendUser(meetingParamVo.getUserList(),maxid,1);
                //保存抄送人员信息
                saveMeetingAttendUser(meetingParamVo.getCcUserList(),maxid,2);
                // 保存附件信息
                saveDocument(meetingParamVo.getDocuments(),maxid);
                //3、调用zoom接口创建虚拟会议
                /*
                 * 先从mysql数据库获取用户id（）meeting_id字段
                 * 然后拼接创建会议url地址，传递会议信息，创建会议。
                 */
                ZoomMeetingVo zoomMeeting =createZoomMeeting(maxid,meetingParamVo);
                // "\uF077\t参会链接：" + zoomMeeting.getJoinUrl().split("\\?")[0] + "\\n"+
                content = content + "\uF077\t参会链接： " + zoomMeeting.getJoinUrl() + " \\n"+
                        "\uF077\t会议ID： " + zoomMeeting.getZoomId() + " \\n"+
                        "\uF077\t参会密码： " + zoomMeeting.getPassword() + " \\n"+
                        "\\n" +
                        "如果您是第一次使用Zoom， 请通过以下链接安装ZOOM插件： https://www.zoom.cn/download/ \\n" +
                        "\\n" +
                        "*主持人控制权的参会链接将在会议开始前10分钟发送至主持人邮箱，请主持人注意查收。\\n";
//                content = content+"\\n\\n\\n虚拟会议参与地址：\\n"+zoomMeeting.getJoinUrl().split("\\?")[0]
//                        +"\\n参会密码："+zoomMeeting.getPassword();
                english = english + "\uF077\tMeeting Link: " + zoomMeeting.getJoinUrl() + "\\n"+
                        "\uF077\tzoomID: " + zoomMeeting.getZoomId() + " \\n"+
                        "\uF077\tpassword: " + zoomMeeting.getPassword() + " \\n"+
                        "\\n" +
                        "If this is your first time joining a Zoom Meeting, please click below link to install the Zoom plug-ins: \\n" + "https://www.zoom.cn/download/ \\n" +
                        "\\n" +
                        "*The meeting link with host control access will be sent out to the host 10 minutes before the meeting.\\n";
                startContent = content+"\\n虚拟会议开启地址： \\n"+zoomMeeting.getStartUrl();

                //更新虚拟会议室信息
                oaMeetingService.updateVirtualOaMeeting(zoomMeeting,maxid);
            }
        }else{
            // 获取当前会议室符合条件的数据id
            List<OaMeeting> oaMeetings = oaMeetingService.findOaMeeting(meetingParamVo);
            if (oaMeetings != null && oaMeetings.size() > 0) {
                //修改会议室状态信息
                maxid = splitMeetingData(meetingParamVo,oaMeetings.get(0).getId(),uuid,2);
                location = location + meetingParamVo.getLocation();
                if (maxid == null){
                    return R.error();
                }
                map.put("meetingId",maxid);
                //保存参会人员信息
                saveMeetingAttendUser(meetingParamVo.getUserList(),maxid,1);

                //保存抄送人员信息
                saveMeetingAttendUser(meetingParamVo.getCcUserList(),maxid,2);
                // 保存附件信息
                saveDocument(meetingParamVo.getDocuments(),maxid);
            }

            //////////////////
            // 获取当前时段空闲的虚拟会议室列表 please do NOT reply
//            List<OaMeeting> virtualOaMeetings = oaMeetingService.aliveFreeVirMeeting(
//                    meetingParamVo.getBeginTime(), meetingParamVo.getEndTime());
            OaMeeting virtualOaMeeting = oaMeetingService.findMeetingById(Long.parseLong(meetingParamVo.getVirtualMeetingId()));
            //List<OaMeeting> virtualOaMeetings = oaMeetingService.VirOaMeetingWithoutId(meetingParamVo);
            if (virtualOaMeeting != null ){
                //修改虚拟会议室状态信息  虚拟会议室从数据库中取出一个空闲的
                maxid = splitMeetingData(meetingParamVo,virtualOaMeeting.getId(),uuid,1);
                location = location +"&"+ meetingParamVo.getLocation();
                if (maxid == null){
                    return R.error();
                }
                map.put("virtualMeetingId",maxid);
                //保存参会人员信息
                saveMeetingAttendUser(meetingParamVo.getUserList(),maxid,1);

                //保存抄送人员信息
                saveMeetingAttendUser(meetingParamVo.getCcUserList(),maxid,2);
                // 保存附件信息
                saveDocument(meetingParamVo.getDocuments(),maxid);
                //3、调用zoom接口创建虚拟会议
                /*
                 * 先从mysql数据库获取用户id（）meeting_id字段
                 * 然后拼接创建会议url地址，传递会议信息，创建会议。
                 */
                ZoomMeetingVo zoomMeeting =createZoomMeeting(maxid,meetingParamVo);
                //\n,\r,\r\n
                content = content + "\uF077\t参会链接： " + zoomMeeting.getJoinUrl() + "\\n"+
                        "\uF077\t会议ID： " + zoomMeeting.getZoomId() + " \\n"+
                        "\uF077\t参会密码： " + zoomMeeting.getPassword() + " \\n"+
                        "\\n" +
                        "如果您是第一次使用Zoom， 请通过以下链接安装ZOOM插件： https://www.zoom.cn/download/ \\n" +
                        "\\n" +
                        "*主持人控制权的参会链接将在会议开始前10分钟发送至主持人邮箱，请主持人注意查收。\\n";
//                content = content+"\\n\\n\\n虚拟会议参与地址：\\n"+zoomMeeting.getJoinUrl().split("\\?")[0]
//                            +"\\n参会密码："+zoomMeeting.getPassword();
                english = english + "\uF077\tMeeting Link: " + zoomMeeting.getJoinUrl() + " \\n"+
                        "\uF077\tzoomID： " + zoomMeeting.getZoomId() + " \\n"+
                        "\uF077\tpassword： " + zoomMeeting.getPassword() + " \\n"+
                        "\\n" +
                        "If this is your first time joining a Zoom Meeting, please click below link to install the Zoom plug-ins:\\n"+"https://www.zoom.cn/download/ \\n" +
                        "\\n" +
                        "*The meeting link with host control access will be sent out to the host 10 minutes before the meeting.\\n";
                startContent = content+"\\n虚拟会议开启地址： \\n"+zoomMeeting.getStartUrl();
                //更新虚拟会议室信息
                oaMeetingService.updateVirtualOaMeeting(zoomMeeting,maxid);
            }
        }
        english = english + "*According to Hanergy policy, every attendee is required to join the meeting with his real name.\\n" +
                "*Confidential Meeting Requirements: Attendees are NOT allowed to do any form of meeting recording without permission. The meeting host should be responsiblefor the confidentiality management during the meeting.\\n" +
                "\\n" +
                "Thank you for your attention. \\n" +
                "\\n" +
                "Hanergy Meeting Room Reservation System \\n\\n\\n";
        content = content + "*按照保密办要求，请每一位参会人实名参会。\\n" +
                "*涉密会议保密管理要求：请会议申请人/主持人注意会议过程中的保密管理，涉密会议禁止参会人私自拍照、录音、录像或录屏。\\n" +
                "\\n" +
                "感谢您的配合！\\n" +
                "\\n" +
                "汉能会议室预约管理系统 \\n" +
                "___________________________________________________________________________________\\n" +
                "\\n" + english;
        meetingParamVo.setLocation(location);
        meetingParamVo.setContent(content);
        // 是否发送邮件
        if (meetingParamVo.getSend() == 1){
            // 给所有参会人发邮件
            cacheEmailToRedis(meetingParamVo,uuid,map);
        } else {
            // 只给虚拟会议室主持人发邮件
            cacheCompereEmailToRedis(meetingParamVo,uuid,map);
        }
        return  R.ok(map);
    }
    // 保存附件信息
    public void saveDocument(List<Document> documents,Long id){
        if (documents != null && documents.size() > 0){
            for (int i = 0; i < documents.size(); i++) {
                Document document = documents.get(i);
                document.setCreatetime(new Date());
                document.setServiceId(id);
                document.setId(identityService.buildOneId());
                if (document.getUploadUserId() != null){
                    SysUser user = sysUserService.getUserById(document.getUploadUserId());
                    if (user != null){
                        document.setUploadUserName(user.getName());
                    }
                }
                documentService.save(document);
            }
        }
    }
    // 缓存邮件数据到redis
    public void cacheEmailToRedis(MeetingParamVo meetingParamVo,String uuid,Map<String,Object> map){
        //4、给参会人员发送outlook邮件
        //根据用户id获取收件人邮箱信息
        List<String> userEamils = sysUserService.getUserByIds(meetingParamVo.getUserList());
        //meetingParamVo.setEmailList(userEamils);
        //据用户id获取抄送人邮箱信息
        List<String> ccUserEamils = sysUserService.getUserByIds(meetingParamVo.getCcUserList());
        meetingParamVo.setCcEmailList(ccUserEamils);

        //5、给主持人发送会议邮件 内容待start_url
        //meetingParamVo.setContent(startContent);
        List<Long> comperes = new ArrayList<>();
        comperes.add(meetingParamVo.getCompere());
        List<String> compereEmail = sysUserService.getUserByIds(comperes);
        // 发送人合集（参会人员+主持人）
        userEamils.addAll(compereEmail);
        meetingParamVo.setEmailList(userEamils);
        meetingParamVo.setUuid(uuid);
        if (map.get("virtualMeetingId") != null){
            meetingParamVo.setVirtualMeetingId(map.get("virtualMeetingId").toString());
            if (map.get("meetingId") == null) {
                redisService.lpush("sendOutlook",JSON.toJSONString(meetingParamVo));
            }
        }
        if (map.get("meetingId") != null){
            meetingParamVo.setMeetingId(map.get("meetingId").toString());
            redisService.hashSet("prepareSendOutlook",map.get("meetingId").toString(),JSON.toJSONString(meetingParamVo));
            //redisService.lpush("prepareSendOutlook",JSON.toJSONString(meetingParamVo));
        }
        //发送邮件
        //mailOutlook.send(meetingParamVo,uuid);
    }

    // 缓存主持人邮件数据到redis
    public void cacheCompereEmailToRedis(MeetingParamVo meetingParamVo,String uuid,Map<String,Object> map){
        //5、给主持人发送会议邮件 内容待start_url
        List<Long> comperes = new ArrayList<>();
        comperes.add(meetingParamVo.getCompere());
        List<String> compereEmail = sysUserService.getUserByIds(comperes);
        meetingParamVo.setEmailList(compereEmail);
        meetingParamVo.setUuid(uuid);
        if (map.get("virtualMeetingId") != null){
            meetingParamVo.setVirtualMeetingId(map.get("virtualMeetingId").toString());
            if (map.get("meetingId") == null) {
                redisService.lpush("sendOutlook",JSON.toJSONString(meetingParamVo));
            } else {
                meetingParamVo.setMeetingId(map.get("meetingId").toString());
                redisService.hashSet("prepareSendOutlook",map.get("meetingId").toString(),JSON.toJSONString(meetingParamVo));
            }
        }
    }



    //预约虚拟会议室,并获取预约会议室信息
    public ZoomMeetingVo createZoomMeeting(Long maxId, MeetingParamVo meetingParamVo){

        ZoomMeetingVo zoomMeeting = new ZoomMeetingVo();
        //根据api key和api secret生成token
        String token = "";
        try {
            token = JWTBuilder.buildSignedJWTString(UrlUtil.apiKey, UrlUtil.apiSecret,10000000);
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取会议详情
        OaMeeting nowMeeting = oaMeetingService.findMeetingById(maxId);
        //获取虚拟会议室用户id
        String zoomUserId = nowMeeting.getMeetingId();
        //根据用户id拼接完整创建zoom会议室url
        String createZoomMeetingUrl = UrlUtil.createMeeting+zoomUserId+"/meetings";
        //设置请求头信息
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);

        //设置请求体参数
        Map<String,String> body = formatData(meetingParamVo);

        String result = HttpClient.sendPostRequest(createZoomMeetingUrl,header,body);

        JSONObject json = JSONObject.fromObject(result);

        String startUrl = json.getString("start_url");
        zoomMeeting.setStartUrl(startUrl);
        String joinUrl = json.getString("join_url");
        zoomMeeting.setJoinUrl(joinUrl);
        //会议室密码
        String password = json.getString("password");
        zoomMeeting.setPassword(password);
        //会议室id
        int id = json.getInt("id");
        zoomMeeting.setZoomId(id);
        //waiting 等状态
        String status = json.getString("status");
        return zoomMeeting;
    }


    /*
        {
         "topic": "测试1",
        "type": "2",  1：即时会议 2：预定会议 3：无规则的重复会议  4：有规则的重复会议
        "start_time": "2019-05-07 12:00:00",
        "duration": "3",      持续时长
        "password": "123456"  会议密码
        "agenda":"会议说明"
        }
        填充请求体参数
    */
    public Map<String,String> formatData(MeetingParamVo meetingParamVo){
        Map<String,String> body = new HashMap<>();
        body.put("type","2");   //1：即时会议 2：预定会议 3：无规则的重复会议  4：有规则的重复会议
        body.put("timezone","Asia/Shanghai");
        if (meetingParamVo.getTopic() != null && !"".equals(meetingParamVo.getTopic())){
            //主题
            body.put("topic",meetingParamVo.getTopic());
        }
        if (meetingParamVo.getBeginTime() != null && !"".equals(meetingParamVo.getBeginTime())){
            //开始时间
            body.put("start_time", DateUtils.stringToString(meetingParamVo.getBeginTime(), DateUtils.DATE_T_PATTERN));
            if (meetingParamVo.getEndTime() != null && !"".equals(meetingParamVo.getEndTime())){
                long min = DateUtils.getMinutebetweenDate(meetingParamVo.getBeginTime(),meetingParamVo.getEndTime());
                //持续时长
                body.put("duration",""+min);
            }
        }
        if (meetingParamVo.getContent() != null && !"".equals(meetingParamVo.getContent())){
            //会议说明
            body.put("agenda",meetingParamVo.getContent());
        }
        // 生成一个六位数的随机密码
        String password = String.valueOf((int)((Math.random()*9+1)*100000));
        body.put("password",password);
        return body;
    }

    //保存参会人员信息
    public void saveMeetingAttendUser(List<Long> list,Long oaMeetingId,Integer type){
        if (list != null && list.size() > 0){
            for (Long userId : list) {
                Long id = identityService.buildOneId();
                OaMeetingAttend user = new OaMeetingAttend();
                user.setId(id);
                user.setOaMeetingId(oaMeetingId);
                user.setUserId(userId);
                user.setType(type);     //type 1:参会人员  2：抄送人员
                oaMeetingAttendService.saveMeetingAttendUser(user);
            }
        }
    }


    /*
     * @Author hld
     * @Description 拆分会议室数据
     * @Date 11:26 2019/5/8
     * @Param meetingParamVo:会议信息,
     * @Param meetingId:会议室id
     * @return void
     **/
    public Long splitMeetingData(MeetingParamVo meetingParamVo, Long meetingId, String uuid,Integer state){

        Long maxId = null;

        //获取会议详情
        OaMeeting meeting = oaMeetingService.findMeetingById(meetingId);
        meetingParamVo.setLocation(meeting.getMeetingName());
        if (meeting.getState().intValue() == 1){        //查到的会议已预订，说明前面搜索结果都是错的
            return null;
        }
        /*
         * 将会议室开始结束时间进行拆分，比如会议室8:00-12：00空闲 现在要预约 9:00-11:00
         * 将会议室拆分为 8:00-9:00空闲  9:00-11:00占用 11:00-12:00空闲
         */
        //日期分段
        Date beginTime = DateUtils.stringToDate(meetingParamVo.getBeginTime(), DateUtils.DATE_TIME_PATTERN);
        Date endTime = DateUtils.stringToDate(meetingParamVo.getEndTime(), DateUtils.DATE_TIME_PATTERN);
        List<Map<String, Date>> dateList = DateUtils.splitDate(DateUtils.stringToDate(meeting.getBeginTime()),
                DateUtils.stringToDate(meeting.getEndTime()),beginTime,endTime);

        for (Map<String,Date> map:dateList){
            // 从redis获取分布式主键
            Long id = identityService.buildOneId();
            //查看是否是预约的会议时间段
            if (beginTime.compareTo(map.get("beginTime")) == 0 &&
            endTime.compareTo(map.get("endTime")) == 0){
                SysUser user = sysUserService.getUserById(meetingParamVo.getUserId());
                //丰富会议预约信息  预约人信息暂时无法获取，后期在协商
                OaMeeting oaMeeting = new OaMeeting(meeting,meetingParamVo.getTopic(),meetingParamVo.getContent(),
                        DateUtils.dateToString(beginTime), DateUtils.dateToString(endTime),user.getUserId(),
                        user.getName(),user.getMobile(),uuid,state,meetingParamVo.getCompere(),
                        meetingParamVo.getSolveQuestion(),meetingParamVo.getSecret(),
                        meetingParamVo.getNote(),meetingParamVo.getUserNumber(),meetingParamVo.getOutsider());
                oaMeeting.setId(id);
                oaMeetingService.saveOaMeeting(oaMeeting);
                //获取最新插入的数据主键id
                maxId = id;

            }else {
                //空闲会议室信息
                OaMeeting oaMeeting = new OaMeeting(meeting, DateUtils.dateToString(map.get("beginTime"))
                        , DateUtils.dateToString(map.get("endTime")),0);
                oaMeeting.setId(id);
                oaMeetingService.saveOaMeeting(oaMeeting);
            }
        }
        //删除拆分前的会议室信息
        oaMeetingService.delMeetingById(meetingId);

        return maxId;
    }


    @ApiOperation(value="已预约会议列表",notes="查看已预约实体、虚拟会议列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",paramType="query"),
            @ApiImplicitParam(name="type",value="1：实体  2：虚拟 3：实体",required=true,paramType="query"),
            @ApiImplicitParam(name="meetingType",value="3:我参与的 2：我发起的 1：所有的",required=true,paramType="query"),
            @ApiImplicitParam(name="day",value="日期 年-月-日",required=true,paramType="query")
    })
    @PostMapping("/getMeetingList")
    public R getMeetingList(@RequestBody MeetingParamVo meetingListVo){
        List<OaMeeting> oaMeeting = new ArrayList<>();
        List<OaMeetingDetail> details = new ArrayList<>();
        if (meetingListVo.getType() == 3){
            meetingListVo.setType(1);
        }
        // 所有会议室
        if (meetingListVo.getMeetingType() == 1){ //所有的
            oaMeeting = oaMeetingService.getMeetingList(meetingListVo.getType(),meetingListVo.getDay());
        }else if (meetingListVo.getMeetingType() == 2){  //我发起的
            if (meetingListVo.getUserId() == null){
                return R.error("无法获取用户信息");
            }
            oaMeeting = oaMeetingService.getMeetingList(meetingListVo.getUserId(),meetingListVo.getType(),meetingListVo.getDay());
        } else {   //我参与的
            oaMeeting = oaMeetingService.getAttendMeetingList(meetingListVo.getUserId(),meetingListVo.getType(),meetingListVo.getDay());
        }
        details = meetingUserDetail(oaMeeting);
        List<OaMeetingDetail> occupys = null;
        if (meetingListVo.getMeetingType() == 1){
            // 获取被占用会议室信息
            occupys = occupyMeetingController.getRedisCacheTime(meetingListVo.getUserId(),
                    meetingListVo.getType(),meetingListVo.getDay());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("data",details);
        map.put("occupy",mergeCollection(details,occupys));
        return R.ok(map);
    }

    // 集合去掉时间重复的部分
    public List<OaMeetingDetail> mergeCollection(List<OaMeetingDetail> details,List<OaMeetingDetail> occupys){
        List<OaMeetingDetail> result = new ArrayList<>();
        if (occupys != null && details != null) {
            for (int i = 0; i < occupys.size(); i++) {
                OaMeetingDetail occupy = occupys.get(i);
                if (occupy.getEndTime() != null && "00:00".equals(occupy.getEndTime())){
                    occupy.setEndTime("23:59");
                }
                boolean flag = true;
                for (int j = 0;j < details.size(); j++) {
                    OaMeetingDetail detail = details.get(j);
                    if (occupy.getBeginTime().compareTo(detail.getBeginTime()) <= 0 &&
                    occupy.getEndTime().compareTo(detail.getEndTime()) >= 0 &&
                    occupy.getMeetingId().compareTo(detail.getMeetingId()) == 0){
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    result.add(occupy);
                }
            }
        }
        return result;
    }




    //获取参会人员信息
    public List<OaMeetingDetail> meetingUserDetail (List<OaMeeting> meetings){
        List<OaMeetingDetail> details = new ArrayList<>();
        for (OaMeeting meeting : meetings) {
            OaMeetingDetail detail = new OaMeetingDetail();
            BeanUtils.copyProperties(meeting,detail);
            //获取参会人员
            List<SysUser> userList = sysUserService.getMeetingAttendUser(meeting.getId(),1);
            detail.setUserList(userList);
            //获取抄送人员
            List<SysUser> ccUserList = sysUserService.getMeetingAttendUser(meeting.getId(),2);
            detail.setCcUserList(ccUserList);
            List<Document> documents = documentService.getDocumentByServiceId(meeting.getId());
            detail.setDocuments(documents);
            //获取主持人信息
            if (meeting.getCompere() != null) {
                SysUser user = sysUserService.getUserById(meeting.getCompere());
                detail.setCompereUser(user);
            }
            detail.setBeginTime(DateUtils.hourMinute(detail.getBeginTime()));
            detail.setEndTime(DateUtils.hourMinute(detail.getEndTime()));
            details.add(detail);
        }
        return details;
    }


    @ApiOperation(value="取消预约会议",notes="取消已预约实体、虚拟会议室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oaMeetingId",value="会议室id",required=true,paramType="query")

    })
    @GetMapping("/cancelMeeting")
    public R cancelMeeting(@RequestParam("meetingId") Long oaMeetingId){
        MeetingParamVo meetingParamVo = new MeetingParamVo();

        /*
         * 删除预约的会议室
         * 1、查询mysql获取预约详细信息
         * (2 )、根据mysql查到的zoom_id 调用zoom接口删除预约的会议数据
         * 3、根据会议开始时间获取结束时间=开始时间、开始时间=结束时间的数据，
         * 4、代码将这几段时间进行合并，插入合并后的数据，删除上面查询到的几条数据
         * 5、删除参会人员信息数据
         * 6、outlook发送邮件，会议取消提醒
         *
         * 其中虚拟会议室执行第二步，实体会议室跳过第二步
         */
        //1、查询mysql获取预约详细信息
        OaMeeting oaMeeting = oaMeetingService.findMeetingById(oaMeetingId);
        meetingParamVo.setBeginTime(oaMeeting.getBeginTime());
        meetingParamVo.setEndTime(oaMeeting.getEndTime());
        meetingParamVo.setLocation(oaMeeting.getMeetingName());
        meetingParamVo.setTopic(oaMeeting.getTopic());
        meetingParamVo.setUuid(oaMeeting.getUuid());
        //有两个相同uuid的数据时，不发送取消会议邮件
        int same = oaMeetingService.findMeetingByUuid(oaMeeting.getUuid()).size();

        if (oaMeeting.getType() == 2){     //虚拟会议室需要调用zoom接口取消会议预约
            //2、根据mysql查到的zoom_id 调用zoom接口删除预约的会议数据
            cancelZoomVirtualMeeting(oaMeeting);
        }

        //3、查询结束时间=预约会议开始时间的空闲会议室信息
        mergeMeeting(oaMeeting);

        if (same <= 1){     //只有一个会议室是这个uuid 发送取消会议邮件
            //6、outlook邮件提醒
            //获取参会人员id
            List<Long> userIds = oaMeetingAttendService.getMeetingAttendUser(oaMeetingId,1);
            List<String> userEamils = sysUserService.getUserByIds(userIds);
            meetingParamVo.setEmailList(userEamils);
            //获取抄送人员id
            List<Long> ccUserIds = oaMeetingAttendService.getMeetingAttendUser(oaMeetingId,1);
            List<String> ccUserEamils = sysUserService.getUserByIds(ccUserIds);
            meetingParamVo.setCcEmailList(ccUserEamils);
            //取消会议
            if (userEamils != null || ccUserEamils != null){
                redisService.lpush("cancelOutlook",JSON.toJSONString(meetingParamVo));
                //mailOutlook.cancel(meetingParamVo);
            }
        }
        //5、删除参会人员信息
        oaMeetingAttendService.delMeetingAttend(oaMeetingId);
        // 删除附件信息
        documentService.delDocumentByServiceId(oaMeetingId);
        return R.ok();
    }

    // 取消会议，合并会议室资源
    public void mergeMeeting(OaMeeting oaMeeting){
        // 获取前后相邻会议时间段，合并
        OaMeeting beforeMeeting = oaMeetingService.getEndEqualBeginData(oaMeeting);
        if (beforeMeeting != null){ //和前面那个会议室合并开始时间
            oaMeeting.setBeginTime(beforeMeeting.getBeginTime());
            //合并完会议室之后删除原纪录
            oaMeetingService.delMeetingById(beforeMeeting.getId());
        }
        //查询开始时间=预约会议结束时间的空闲会议室信息
        OaMeeting lastMeeting = oaMeetingService.getBeginEqualEndData(oaMeeting);
        if (lastMeeting != null){   //和后面那个会议室合并开始时间
            oaMeeting.setEndTime(lastMeeting.getEndTime());
            //合并完会议室之后删除原纪录
            oaMeetingService.delMeetingById(lastMeeting.getId());
        }
        //删除预约的会议室 mysql纪录
        if (oaMeeting.getId() != null){
            oaMeetingService.delMeetingById(oaMeeting.getId());
        }
        //初始化合并之后的新会议室信息
        OaMeeting mergeMeeting = new OaMeeting(oaMeeting,oaMeeting.getBeginTime(),oaMeeting.getEndTime(),0);
        // 从redis获取分布式主键
        Long id = identityService.buildOneId();
        mergeMeeting.setId(id);
        //插入合并之后的新会议室信息
        oaMeetingService.saveOaMeeting(mergeMeeting);
    }

    //调用zoom接口取消虚拟会议
    public void cancelZoomVirtualMeeting(OaMeeting oaMeeting){
        String meetingId = oaMeeting.getMeetingId();   //用户id
        int zoomId = oaMeeting.getZoomId();      //zoom房间id
        //拼接取消会议url
        String canceUrl = UrlUtil.updateMeeting+zoomId;
        //根据api key和api secret生成token
        String token = "";
        try {
            token = JWTBuilder.buildSignedJWTString(UrlUtil.apiKey, UrlUtil.apiSecret,10000000);
            //设置请求头信息
            Map<String,String> header = new HashMap<>();
            header.put("Authorization", "Bearer "+token);
            String result = HttpClient.sendDelectRequest(canceUrl,header,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        //JSONObject.fromObject(result);
    }


    //修改其中一个会议室自动修改另一个会议室信息
    @PostMapping("/updateMeeting")
    public R updateMeeting(@RequestBody MeetingParamVo meetingParamVo){
        Map<String,Object> map = new HashMap<>();
        //保存原始会议内容
        String contentf = meetingParamVo.getContent();
        // 获取主持人信息
        SysUser compereUser = sysUserService.getUserById(meetingParamVo.getCompere());
        // 参会人员邮件内容
        //会议内容，如果有虚拟会议需要拼接start_url、join_url等
        String content = "*此邮件为系统自动触发，请不要回复本邮件。\\n\\n" +
                "您好!\\n" +
                "\\n" +
                compereUser.getName() +"诚挚地邀请您参加此次会议。请您根据以下会议信息准时参加会议：\\n" +
                "\\n" +
                "\uF077\t会议时间: (GMT+8, CHINA BEIJING)" + meetingParamVo.getBeginTime() + "\\n\\n";
        String english = "*This email is generated by system, please do NOT reply.\\n\\n" +
                "You are sincerely invited to join this meeting by " +
                PinyinUtils.convertHanzi2Pinyin(compereUser.getName(),true) + ". Please follow below meeting information to join the meeting on time:\\n" +
                "\\n" +
                "\uF077\tMeeting Time: (GMT+8, CHINA BEIJING) " + meetingParamVo.getBeginTime() + " - " + meetingParamVo.getEndTime() + "\\n\\n";
        //String content = meetingParamVo.getContent();
        // 发起人邮件内容
        String startContent = meetingParamVo.getContent();
        //会议地址
        String location = "";

        //首先将前端传递的时间格式处理一下
        //meetingParamVo.setBeginTime(DateUtils.appendTime(meetingParamVo.getDay(),meetingParamVo.getBeginTime()));
        meetingParamVo.setBeginTime(meetingParamVo.getDay()+" "+meetingParamVo.getBeginTime()+":00");
        meetingParamVo.setEndTime(meetingParamVo.getDay()+" "+meetingParamVo.getEndTime()+":00");
        if (meetingParamVo.getMeetingId() != null && !"".equals(meetingParamVo.getMeetingId().trim())){     //修改实体会议室
            map.put("meetingId",meetingParamVo.getMeetingId());
            //获取实体会议室信息
            OaMeeting oaMeeting = oaMeetingService.findMeetingById(Long.valueOf(meetingParamVo.getMeetingId()));
            location = oaMeeting.getMeetingName();
            //获取实体会议室相同uuid的虚拟会议室信息
            List<OaMeeting> uuidMeeting = oaMeetingService.findAnotherMeetingByUuid(oaMeeting.getUuid(),
                    Long.valueOf(meetingParamVo.getMeetingId()));
            if (uuidMeeting != null && uuidMeeting.size() > 0){
                for (OaMeeting uuidMeet : uuidMeeting){
                    english = english + "\uF077\tMeeting Link: " + uuidMeet.getJoinUrl() + " \\n"+
                            "\\n" +
                            "If this is your first time joining a Zoom Meeting, please click below link to install the Zoom plug-ins:\\n"+"https://www.zoom.cn/download/ \\n" +
                            "\\n" +
                            "*The meeting link with host control access will be sent out to the host 10 minutes before the meeting.\\n";

                    content = content + "\uF077\t参会链接: " + uuidMeet.getJoinUrl() + "\\n"+
                            "\uF077\t会议ID: " + uuidMeet.getZoomId() + "\\n"+
                            "\uF077\t参会密码: " + uuidMeet.getPassword() + "\\n"+
                            "\\n" +
                            "如果您是第一次使用Zoom，请通过以下链接安装ZOOM插件: https://www.zoom.cn/download/\\n" +
                            "\\n" +
                            "*主持人控制权的参会链接将在会议开始前10分钟发送至主持人邮箱，请主持人注意查收。\\n" +
                            "___________________________________________________________________________________\\n" +
                            "\\n" + english;

//                    content = content + "\\n\\n\\n虚拟会议参与地址：\\n" + uuidMeet.getJoinUrl().split("\\?")[0]+
//                    "\\n会议密码："+uuidMeet.getPassword();
                    startContent = content + "\\n虚拟会议开启地址: \\n" + uuidMeet.getStartUrl();
                    location = location + "&" + uuidMeet.getMeetingName();
                    //修改虚拟会议信息
                    updateOaMeetingDetail(meetingParamVo,uuidMeet.getId(),uuidMeet.getState());
                    //调用zoom接口修改会议信息
                    updateZoomMeeting(meetingParamVo,uuidMeet.getId());
                    map.put("virtualMeetingId",uuidMeet.getId());
                }
            }
            //参会地址
            meetingParamVo.setLocation(location);
            // 根据人员变更重新发送邮件会议变更消息
            if (meetingParamVo.getSend() == 1){
                cacheReloadEmail(meetingParamVo,Long.valueOf(meetingParamVo.getMeetingId()),content);
                //reloadEmail(meetingParamVo,Long.valueOf(meetingParamVo.getMeetingId()),content,startContent);
            }
            meetingParamVo.setContent(contentf);
            //修改实体会议室信息
            updateOaMeetingDetail(meetingParamVo,Long.valueOf(meetingParamVo.getMeetingId()),oaMeeting.getState());
        } else {    //修改虚拟会议室
        //if (meetingParamVo.getVirtualMeetingId() != null){
            map.put("virtualMeetingId",meetingParamVo.getVirtualMeetingId());
            //获取虚拟会议室信息
            OaMeeting oaMeeting = oaMeetingService.findMeetingById(Long.valueOf(meetingParamVo.getVirtualMeetingId()));

            english = english + "*According to Hanergy policy, every attendee is required to join the meeting with his real name.\\n" +
                    "\\n" +
                    "Thank you for your attention. \\n" +
                    "\\n" +
                    "Hanergy Meeting Room Reservation System \\n\\n\\n";
            content = content + "\uF077\t参会链接: " + oaMeeting.getJoinUrl() + "\\n"+
                    "\uF077\t会议ID: " + oaMeeting.getZoomId() + "\\n"+
                    "\uF077\t参会密码: " + oaMeeting.getPassword() + "\\n"+
                    "\\n" +
                    "如果您是第一次使用Zoom， 请通过以下链接安装ZOOM插件: https://www.zoom.cn/download/\\n" +
                    "\\n" +
                    "*主持人控制权的参会链接将在会议开始前10分钟发送至主持人邮箱，请主持人注意查收。\\n" +
                    "___________________________________________________________________________________\\n" +
                    "\\n" + english;
            //content = content + "\\n\\n\\n虚拟会议参与地址：\\n" + oaMeeting.getJoinUrl();
            startContent = content + "\\n虚拟会议开启地址: \\n" + oaMeeting.getStartUrl();
            location = "&"+oaMeeting.getMeetingName();
            //获取虚拟会议室相同uuid的实体会议室信息
            List<OaMeeting> uuidMeeting = oaMeetingService.findAnotherMeetingByUuid(oaMeeting.getUuid(),
                    Long.valueOf(meetingParamVo.getVirtualMeetingId()));
            if (uuidMeeting != null && uuidMeeting.size() > 0){
                // 设置参会地址
                location = uuidMeeting.get(0).getMeetingName()+location;
                meetingParamVo.setLocation(location);
                //缓存邮件内容
                if (meetingParamVo.getSend() == 1) {
                    cacheReloadEmail(meetingParamVo,Long.valueOf(meetingParamVo.getVirtualMeetingId()),content);
                } else {
                    cacheReloadCompereEmailToRedis(meetingParamVo,oaMeeting.getUuid(),map);
                }
                // reloadEmail(meetingParamVo,Long.valueOf(meetingParamVo.getVirtualMeetingId()),content,startContent);
                meetingParamVo.setContent(contentf);
                for (OaMeeting uuidMeet : uuidMeeting){
                    //修改实体会议信息
                    updateOaMeetingDetail(meetingParamVo,uuidMeet.getId(),uuidMeet.getState());
                    map.put("meetingId",uuidMeet.getId());
                }
            }
            //缓存邮件内容
            if (meetingParamVo.getSend() == 1) {
                cacheReloadEmail(meetingParamVo,Long.valueOf(meetingParamVo.getVirtualMeetingId()),content);
            } else {
                cacheReloadCompereEmailToRedis(meetingParamVo,oaMeeting.getUuid(),map);
            }
            meetingParamVo.setContent(contentf);
            //修改虚拟会议室
            updateOaMeetingDetail(meetingParamVo,Long.valueOf(meetingParamVo.getVirtualMeetingId()),oaMeeting.getState());
            //调用zoom接口修改会议信息
            updateZoomMeeting(meetingParamVo,Long.valueOf(meetingParamVo.getVirtualMeetingId()));
        }
        return R.ok(map);
    }

    // 缓存主持人邮件数据到redis
    public void cacheReloadCompereEmailToRedis(MeetingParamVo meetingParamVo,String uuid,Map<String,Object> map){
        //5、给主持人发送会议邮件 内容待start_url
        List<Long> comperes = new ArrayList<>();
        comperes.add(meetingParamVo.getCompere());
        List<String> compereEmail = sysUserService.getUserByIds(comperes);
        meetingParamVo.setEmailList(compereEmail);
        meetingParamVo.setUuid(uuid);
        if (map.get("virtualMeetingId") != null){
            meetingParamVo.setVirtualMeetingId(map.get("virtualMeetingId").toString());
            if (map.get("meetingId") == null) {
                redisService.lpush("updateOutlook",JSON.toJSONString(meetingParamVo));
            } else {
                meetingParamVo.setMeetingId(map.get("meetingId").toString());
                redisService.hashSet("updateOutlook",map.get("meetingId").toString(),JSON.toJSONString(meetingParamVo));
            }
        }
    }

    //预约、取消、修改邮件数据缓存到redis
    public void cacheReloadEmail(MeetingParamVo meetingParamVo, Long meetingId, String content){
        // 会议主持人集合 主持人变动才会用到它
        List<Long> comperes = new ArrayList<>();
        //获取会议信息
        OaMeeting oaMeeting = oaMeetingService.findMeetingById(meetingId);
        meetingParamVo.setUuid(oaMeeting.getUuid());
        meetingParamVo.setLocation(oaMeeting.getMeetingName());

        if (meetingParamVo.getBeginTime() == null || "".equals(meetingParamVo.getBeginTime())){
            meetingParamVo.setBeginTime(oaMeeting.getBeginTime());
        }
        if (meetingParamVo.getEndTime() == null || "".equals(meetingParamVo.getEndTime())){
            meetingParamVo.setEndTime(oaMeeting.getEndTime());
        }
        //获取原参会人员列表
        List<Long> userIds = oaMeetingAttendService.getMeetingAttendUser(meetingId,1);
        //获取原抄送人员列表
        List<Long> ccUserIds = oaMeetingAttendService.getMeetingAttendUser(meetingId,2);
        //获取修改后的参会人员列表
        List<Long> newUserIds = new ArrayList<>();
        newUserIds.addAll(meetingParamVo.getUserList());
        //List<Long> newUserIds = meetingParamVo.getUserList();
        //获取修改后的抄送人员列表
        List<Long> ccNewUserIds = new ArrayList<>();
        ccNewUserIds.addAll(meetingParamVo.getCcUserList());
        //List<Long> ccNewUserIds = meetingParamVo.getCcUserList();
        // 原会议主持人id
        Long compere = oaMeeting.getCompere();
        // 修改后的会议主持人id
        Long newCompere = meetingParamVo.getCompere();

        //获取差集（取消参会人员列表）,取消邮件
        List<Long> canceList = ListUtils.chaji(userIds,newUserIds);
        // 如果主持人变动 需要分别发送取消和邀请邮件，将取消邮件人员信息带入参会人员取消邮件当中
        if (compere != null && newCompere == null){     //取消主持人
            canceList.add(compere);  //取消人员信息集合
        }else if (compere != null && newCompere != null){   //更换主持人
            if (compere.intValue() != newCompere.intValue()){
                comperes.add(meetingParamVo.getCompere());
                List<String> compereEmail = sysUserService.getUserByIds(comperes);
                meetingParamVo.setEmailList(compereEmail);
                meetingParamVo.setContent(content);    // 主持人邮件内容
                // 因为主持人邮件内容和参会人员不一致 所以要单独发送邮件
                redisService.lpush("sendOutlook",JSON.toJSONString(meetingParamVo));
                //mailOutlook.send(meetingParamVo,meetingParamVo.getUuid());
                canceList.add(compere);  //取消人员信息集合
            }
        }else if (compere == null &&newCompere != null){       //新增主持人
            comperes.add(meetingParamVo.getCompere());
            List<String> compereEmail = sysUserService.getUserByIds(comperes);
            meetingParamVo.setEmailList(compereEmail);
            meetingParamVo.setContent(content);    // 主持人邮件内容
            // 因为主持人邮件内容和参会人员不一致 所以要单独发送邮件
            redisService.lpush("sendOutlook",JSON.toJSONString(meetingParamVo));
            //mailOutlook.send(meetingParamVo,meetingParamVo.getUuid());
        }

        meetingParamVo.setContent(content);     //普通人员邮件内容
        if (canceList != null && canceList.size() > 0){
            meetingParamVo.setEmailList(sysUserService.getUserByIds(canceList));
        }
        //获取差集（取消抄送人员列表）,取消邮件
        List<Long> ccCanceList = ListUtils.chaji(ccUserIds,ccNewUserIds);
        if (ccCanceList != null && ccCanceList.size() > 0){
            meetingParamVo.setCcEmailList(sysUserService.getUserByIds(ccCanceList));
        }
        // 如果抄送/参会人员有删减发送取消邮件
        if ((ccCanceList != null && ccCanceList.size() > 0) || (canceList != null && canceList.size() > 0)){
            //取消邮件
            redisService.lpush("cancelOutlook",JSON.toJSONString(meetingParamVo));
            //mailOutlook.cancel(meetingParamVo);
        }

        //交集
        userIds.retainAll(newUserIds);
        ccUserIds.retainAll(ccNewUserIds);
        if (userIds != null && userIds.size() > 0){
            meetingParamVo.setEmailList(sysUserService.getUserByIds(userIds));
        }
        if (ccUserIds != null && ccUserIds.size() > 0){
            meetingParamVo.setCcEmailList(sysUserService.getUserByIds(ccUserIds));
        }
        // 更新邮件
        if ((userIds != null && userIds.size() > 0) || (ccUserIds != null && ccUserIds.size() > 0)){
            redisService.lpush("updateOutlook",JSON.toJSONString(meetingParamVo));
            //mailOutlook.update(meetingParamVo);
        }

        newUserIds.removeAll(userIds);
        ccNewUserIds.removeAll(ccUserIds);
        if (newUserIds != null && newUserIds.size() > 0){
            meetingParamVo.setEmailList(sysUserService.getUserByIds(newUserIds));
        }
        if (ccNewUserIds != null && ccNewUserIds.size() > 0){
            meetingParamVo.setCcEmailList(sysUserService.getUserByIds(ccNewUserIds));
        }
        // 如果有新增加参会/抄送人员，发送会议邀请
        if ((newUserIds != null && newUserIds.size() > 0)||(ccNewUserIds != null && ccNewUserIds.size() > 0)){
            //新增邮件
            redisService.lpush("sendOutlook",JSON.toJSONString(meetingParamVo));
            //mailOutlook.send(meetingParamVo,oaMeeting.getUuid());
        }

    }

    //修改会议信息
    public void updateOaMeetingDetail(MeetingParamVo meetingParamVo, Long meetingId,Integer state){
        OaMeeting oaMeeting = new OaMeeting();
        oaMeeting.setId(meetingId);
        oaMeeting.setTopic(meetingParamVo.getTopic());
        oaMeeting.setContent(meetingParamVo.getContent());
        oaMeeting.setCompere(meetingParamVo.getCompere());
        oaMeeting.setNote(meetingParamVo.getNote());
        oaMeeting.setSecret(meetingParamVo.getSecret());
        oaMeeting.setSolveQuestion(meetingParamVo.getSolveQuestion());
        oaMeeting.setState(state);
        oaMeeting.setOutsider(meetingParamVo.getOutsider());
        oaMeeting.setUserNumber(meetingParamVo.getUserNumber());
        //更新会议信息
        oaMeetingService.updateById(oaMeeting);
        //删除原先参会、抄送人员
        oaMeetingAttendService.delMeetingAttend(meetingId);
        // 删除附件信息
        documentService.delDocumentByServiceId(meetingId);
        // 保存新的附件信息
        saveDocument(meetingParamVo.getDocuments(),meetingId);
        //新增修改之后的参会人员
        saveMeetingAttendUser(meetingParamVo.getUserList(),meetingId,1);
        //新增修改之后的抄送人员
        saveMeetingAttendUser(meetingParamVo.getCcUserList(),meetingId,2);
    }


    //修改zoom预约会议信息
    public void updateZoomMeeting(MeetingParamVo meetingParamVo, Long meetingId){
        //根据api key和api secret生成token
        String token = "";
        try {
            token = JWTBuilder.buildSignedJWTString(UrlUtil.apiKey, UrlUtil.apiSecret,10000000);
        }catch (Exception e){
            e.printStackTrace();
        }
        //从mysql获取zoom会议房间id
        OaMeeting oaMeeting = oaMeetingService.findMeetingById(meetingId);
        Integer zoomId = oaMeeting.getZoomId();
        //拼接zoom更新api接口地址
        String updateUrl = UrlUtil.updateMeeting+zoomId;

        //设置请求头信息
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);

        //设置请求体参数
        Map<String,String> body = formatData(meetingParamVo);
        //调用更新接口获取返回结果
        String result = HttpClient.sendPatchRequest(updateUrl,header,body);
    }


    @GetMapping("/releaseMeeting")
    public R releaseMeeting(@RequestParam("meetingId") Long meetingId) {
        /*
          方案2：
         * 1、更新原有会议室结束时间
         * 2、判断结束时间后面是否是空闲会议室，是合并时间更新，
         * 否：插入新数据
         */
        // 间隔
        int duration = 0;
        //查询mysql获取预约详细信息
        OaMeeting oaMeeting = oaMeetingService.findMeetingById(meetingId);
        // 原有结束时间
        String endTime = oaMeeting.getEndTime();
        // 获取下一个半点时间
        Date halfDate = DateUtils.nextHalfHour(new Date());
        // 释放redis占用会议室
        occupyMeetingController.removekey(DateUtils.dateToString(halfDate),endTime,oaMeeting.getMeetingId());
        oaMeeting.setEndTime(DateUtils.dateToString(halfDate));
        duration = (int) DateUtils.getMinutebetweenDate(oaMeeting.getBeginTime(),oaMeeting.getEndTime());
        oaMeeting.setDuration(duration);
        //1、更新原有会议室
        oaMeetingService.updateById(oaMeeting);
        //释放空闲会议室资源时间
        oaMeeting.setBeginTime(oaMeeting.getEndTime());
        oaMeeting.setEndTime(endTime);
        //计算时长
        duration = (int) DateUtils.getMinutebetweenDate(oaMeeting.getBeginTime(),endTime);
        oaMeeting.setDuration(duration);
        oaMeeting.setId(null);
        //2、查询结束时间=预约会议开始时间的空闲会议室信息
        mergeMeeting(oaMeeting);

        return R.ok();
    }

}
