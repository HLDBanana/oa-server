package com.hanergy.out.controller;

import com.alibaba.fastjson.JSON;
import com.hanergy.out.CXF.Exception_Exception;
import com.hanergy.out.CXF.IKmReviewWebserviceService;
import com.hanergy.out.CXF.IKmReviewWebserviceServiceService;
import com.hanergy.out.CXF.KmReviewParamterForm;
import com.hanergy.out.entity.OaMeeting;
import com.hanergy.out.entity.OaMeetingSet;
import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.*;
import com.hanergy.out.utils.IdentityService;
import com.hanergy.out.utils.ListUtils;
import com.hanergy.out.utils.R;
import com.hanergy.out.vo.CXFVo;
import com.hanergy.out.vo.DocCreatorVo;

import com.hanergy.out.vo.MeetingParamVo;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.namespace.QName;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ApprovalController
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/6/17 8:57)
 */
@RestController
@RequestMapping("/v1/approval")
public class ApprovalController {

    Logger log = LoggerFactory.getLogger(ApprovalController.class);

    private static final QName SERVICE_NAME = new QName("http://webservice.review.km.kmss.landray.com/", "IKmReviewWebserviceServiceService");

    @Autowired
    private IOaMeetingService oaMeetingService;     // 会议信息表单

    @Autowired
    private IOaMeetingAttendService oaMeetingAttendService;     //参会人员

    @Autowired
    private OaMeetingController oaMeetingController;

    @Autowired
    private ISysUserService sysUserService;         //用户表

    @Autowired
    private IRedisService redisService;             //redis

    @Autowired
    private IdentityService identityService;        // redis分布式主键

    @Autowired
    private IDocumentService documentService;              //附件表

    @Autowired
    private IOaMeetingSetService oaMeetingSetService;       //会议室信息表

    @Autowired
    private OccupyMeetingController occupyMeetingController;


    @GetMapping("/approvalResult")
    public R getApprovalResult(
            @RequestParam("result")Integer result,
            @RequestParam("meetingId")Long meetingId) {
        // sendOutlook
        if (result == 1) {      //审批通过 会议状态修改为1
            Object obj = redisService.hashGet("prepareSendOutlook",meetingId.toString());
            if (obj != null){
                // 审批中缓存移动到发送邮件缓存
                redisService.hashRemove("prepareSendOutlook",meetingId.toString());
                redisService.lpush("sendOutlook",(String) obj);
            }
            oaMeetingService.updateMeetingState(1,meetingId);
        } else {
            Object obj = redisService.hashGet("prepareSendOutlook",meetingId.toString());
            if (obj != null){
                // 审批中缓存移动到发送邮件缓存
                redisService.hashRemove("prepareSendOutlook",meetingId.toString());
            }
            // 获取当前会议室基本信息
            OaMeeting oaMeeting = oaMeetingService.findMeetingById(meetingId);
            // 删除redis占位数据
            if (oaMeeting != null ) {
                occupyMeetingController.removekey(oaMeeting.getBeginTime(),oaMeeting.getEndTime(),oaMeeting.getMeetingId());
            }
            // 取消会议申请
            cancelMeeting(meetingId);

        }
        return R.ok();
    }

    // 初始化审批数据
    public KmReviewParamterForm initApprovalData(MeetingParamVo meetingParamVo){
        KmReviewParamterForm kpf = new KmReviewParamterForm();

        CXFVo cxfVo = new CXFVo(meetingParamVo);
        String idStr = meetingParamVo.getMeetingId();
        Long id = Long.parseLong(idStr);

        OaMeetingSet set = oaMeetingSetService.getMeetingSetByMeetingId(id);
        if (set != null){
            cxfVo.setFd_meetingclassify(set.getMeetingType());
            // 审批人
            if (set.getApproval() != null && !"".equals(set.getApproval().trim())){
                String[] approvals = set.getApproval().split(",");
                List<Long> approvalList = ListUtils.stringToLongList(approvals);
                List<SysUser> approvalUser = sysUserService.getsysUserByIds(approvalList);
                if (approvalUser != null && approvalUser.size() > 0) {
                    String approvalNames = "";
                    for (int i = 0; i < approvalUser.size(); i++) {
                        if (i == 0){
                            approvalNames = approvalUser.get(i).getUsername();
                        } else {
                            approvalNames = approvalNames + "," + approvalUser.get(i).getUsername();
                        }
                    }
                    cxfVo.setFd_meetingapprovals(approvalNames);
                }
            }
        }
        SysUser compereUser = sysUserService.getUserById(meetingParamVo.getCompere());
        if (compereUser != null){
            cxfVo.setCompereName(compereUser.getName());
        }
        List<SysUser> userList = sysUserService.getsysUserByIds(meetingParamVo.getUserList());
        if (userList != null && userList.size() > 0){
            String  names = "";
            for (int i = 0; i < userList.size(); i++) {
                if (i == 0){
                    names = userList.get(i).getName();
                } else {
                    names = names + "," + userList.get(i).getName();
                }
            }
            cxfVo.setUserListOfName(names);
        }
        SysUser user = sysUserService.getUserById(meetingParamVo.getUserId());
        if (user != null){
            cxfVo.setUserId(user.getJobNumber());
            cxfVo.setUserName(user.getName());
        }
        //kpf.setFormValues(cxfVo.toString());
        kpf.setFormValues(JSON.toJSONString(cxfVo));
        SysUser creatorUser = sysUserService.getUserById(meetingParamVo.getUserId());
        if (compereUser != null){
            kpf.setDocCreator(JSON.toJSONString( new DocCreatorVo(creatorUser.getJobNumber())));
        }
        kpf.setDocSubject(meetingParamVo.getTopic());
        //正式环境oa模板id
        kpf.setFdTemplateId("16bc18917c95a9fba6b4876420e849e2");
        //测试环境oa模板id
        //kpf.setFdTemplateId("16b62e126b083b06b4d1f064646b44a6");
        kpf.setDocContent(meetingParamVo.getContent());
        kpf.setDocStatus("20");
        kpf.setFlowParam(null);
        //cxf.setAttachmentForms(null);
        return kpf;
    }

    // 调用OA webservice 发送审批数据
    @RequestMapping("/approval")
    public R test(@RequestBody MeetingParamVo meetingParamVo){
        KmReviewParamterForm kpf = initApprovalData(meetingParamVo);
        URL wsdlURL = IKmReviewWebserviceServiceService.WSDL_LOCATION;
        IKmReviewWebserviceServiceService ss = new IKmReviewWebserviceServiceService(wsdlURL, SERVICE_NAME);
        IKmReviewWebserviceService port = ss.getIKmReviewWebserviceServicePort();

        {
            System.out.println("Invoking addReview...");
            try {
                String _addReview__return = port.addReview(kpf);
                System.out.println("addReview.result=" + _addReview__return);
                if (_addReview__return.trim() == null || "".equals(_addReview__return.trim())){
                    return R.error();
                }
            } catch (Exception_Exception e) {
                System.out.println("Expected exception: Exception has occurred.");
                System.out.println(e.toString());
                return R.error(e.toString());
            }
        }
        //System.exit(0);
        return  R.ok();
    }

    // 根据id查询缓存的会议数据
    public String findCacheMeetingById(Long meetingId){
        // 获取缓存的审批中的所有数据
        List<String> sendList = redisService.lrange("prepareSendOutlook",(long)0,(long)-1);
        if (sendList != null && sendList.size() > 0){
            for (int i = 0; i < sendList.size(); i++) {
                log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~发送数量"+sendList.size()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                Object object = JSON.parseObject(sendList.get(i), MeetingParamVo.class);
                if (object instanceof MeetingParamVo) {
                    MeetingParamVo paramVo = (MeetingParamVo) object;
                    // 如果返回的审批id等于虚拟/实体会议id，证明该条数据就是审批数据
                    if ((paramVo.getMeetingId() != null && paramVo.getMeetingId().equals(meetingId.toString())) ||
                            (paramVo.getVirtualMeetingId() != null && paramVo.getVirtualMeetingId().equals(meetingId.toString()))){
                        redisService.lrem("prepareSendOutlook",(long)0,sendList.get(i));
                        return sendList.get(i);
                    }
                    log.info("读取缓存的对象"+object+"  MeetingParamVo = "+paramVo);
                }
            }
        }
        return null;
    }


    // 取消会议预约
    public void cancelMeeting(Long oaMeetingId){
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

        if (oaMeeting.getType() == 2){     //虚拟会议室需要调用zoom接口取消会议预约
            //2、根据mysql查到的zoom_id 调用zoom接口删除预约的会议数据
            oaMeetingController.cancelZoomVirtualMeeting(oaMeeting);
        }
        //3、查询结束时间=预约会议开始时间的空闲会议室信息
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
        oaMeetingService.delMeetingById(oaMeetingId);
        //初始化合并之后的新会议室信息
        OaMeeting mergeMeeting = new OaMeeting(oaMeeting,oaMeeting.getBeginTime(),oaMeeting.getEndTime(),0);
        // 从redis获取分布式主键
        Long id = identityService.buildOneId();
        mergeMeeting.setId(id);
        //插入合并之后的新会议室信息
        oaMeetingService.saveOaMeeting(mergeMeeting);

        //5、删除参会人员信息
        oaMeetingAttendService.delMeetingAttend(oaMeetingId);
        // 删除附件信息
        documentService.delDocumentByServiceId(oaMeetingId);
    }


}
