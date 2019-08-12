package com.hanergy.out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hanergy.out.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
@TableName("oa_meeting")
@ApiModel(value="OaMeeting对象", description="")
public class OaMeeting extends Model<OaMeeting> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "会议室主键")
    private String meetingId;

    @ApiModelProperty(value = "会议室容量")
    private Integer meetingCapacity;

    @ApiModelProperty(value = "会议室名称")
    private String meetingName;

    @ApiModelProperty(value = "会议室类型：1实体会议室，2视频会议室")
    private Integer type;

    @ApiModelProperty(value = "会议主题")
    private String topic;

    @ApiModelProperty(value = "会议内容")
    private String content;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "持续时长")
    private Integer duration;

    @ApiModelProperty(value = "具体位置 例如：a栋1楼")
    private String location;

    @ApiModelProperty(value = "预约人主键")
    private Long appointmentsId;

    @ApiModelProperty(value = "预约人姓名")
    private String appointmentsName;

    @ApiModelProperty(value = "预约人电话")
    private String appointmentsPhone;

    @ApiModelProperty(value = "状态：1已约，2未约")
    private Integer state;

    @ApiModelProperty(value = "会议关联：实体与虚拟会议室的关联")
    private String uuid;

    @ApiModelProperty(value = "主持人id")
    private Long compere;

    @ApiModelProperty(value = "zoom会议开启链接")
    private String startUrl;

    @ApiModelProperty(value = "参加会议url")
    private String joinUrl;

    @ApiModelProperty(value = "虚拟会议室密码")
    private String password;

    @ApiModelProperty(value = "预约zoom会议室id")
    private Integer zoomId;

    @ApiModelProperty(value = "拟解决问题")
    private  String solveQuestion;

    @ApiModelProperty(value = "是否涉密 0：不涉密 1：涉密")
    private  Integer secret;

    @ApiModelProperty(value = "备注")
    private  String note;

    @ApiModelProperty(value = "参会人数")
    private  Integer userNumber;

    @ApiModelProperty(value = "外部人员")
    private String outsider;

    public OaMeeting(){}


    public OaMeeting(OaMeeting oaMeeting, String topic, String content, String beginTime, String endTime, Long appointmentsId, String appointmentsName, String appointmentsPhone, String uuid, Integer state, Long compere, String solveQuestion,Integer secret, String note,Integer userNumber,String outsider) {
        this.meetingId = oaMeeting.getMeetingId();
        this.meetingCapacity = oaMeeting.getMeetingCapacity();
        this.meetingName = oaMeeting.getMeetingName();
        this.type = oaMeeting.getType();
        this.date = oaMeeting.getDate();
        this.location = oaMeeting.getLocation();

        this.topic = topic;
        this.content = content;
        this.beginTime = beginTime;
        this.endTime = endTime;
        //计算时长
        this.duration = (int) DateUtils.getMinutebetweenDate(beginTime,endTime);
        this.appointmentsId = appointmentsId;
        this.appointmentsName = appointmentsName;
        this.appointmentsPhone = appointmentsPhone;
        this.uuid = uuid;
        this.state = state;
        this.compere = compere;
        this.secret = secret;
        this.note = note;
        this.solveQuestion = solveQuestion;
        this.userNumber = userNumber;
        this.outsider = outsider;
    }

    public OaMeeting(OaMeeting oaMeeting, String beginTime, String endTime, Integer state) {
        this.meetingId = oaMeeting.getMeetingId();
        this.meetingCapacity = oaMeeting.getMeetingCapacity();
        this.meetingName = oaMeeting.getMeetingName();
        this.type = oaMeeting.getType();
        this.date = oaMeeting.getDate();

        //计算时长
        this.duration = (int) DateUtils.getMinutebetweenDate(beginTime,endTime);
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    public Integer getMeetingCapacity() {
        return meetingCapacity;
    }

    public void setMeetingCapacity(Integer meetingCapacity) {
        this.meetingCapacity = meetingCapacity;
    }
    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public Long getAppointmentsId() {
        return appointmentsId;
    }

    public void setAppointmentsId(Long appointmentsId) {
        this.appointmentsId = appointmentsId;
    }
    public String getAppointmentsName() {
        return appointmentsName;
    }

    public void setAppointmentsName(String appointmentsName) {
        this.appointmentsName = appointmentsName;
    }
    public String getAppointmentsPhone() {
        return appointmentsPhone;
    }

    public void setAppointmentsPhone(String appointmentsPhone) {
        this.appointmentsPhone = appointmentsPhone;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getCompere() {
        return compere;
    }

    public void setCompere(Long compere) {
        this.compere = compere;
    }


    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getZoomId() {
        return zoomId;
    }

    public void setZoomId(Integer zoomId) {
        this.zoomId = zoomId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSolveQuestion() {
        return solveQuestion;
    }

    public void setSolveQuestion(String solveQuestion) {
        this.solveQuestion = solveQuestion;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public String getOutsider() {
        return outsider;
    }

    public void setOutsider(String outsider) {
        this.outsider = outsider;
    }

    public Integer getSecret() {
        return secret;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OaMeeting{" +
        "id=" + id +
        ", meetingId=" + meetingId +
        ", meetingCapacity=" + meetingCapacity +
        ", meetingName=" + meetingName +
        ", type=" + type +
        ", topic=" + topic +
        ", content=" + content +
        ", date=" + date +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", appointmentsId=" + appointmentsId +
        ", appointmentsName=" + appointmentsName +
        ", appointmentsPhone=" + appointmentsPhone +
        ", state=" + state +
        "}";
    }
}
