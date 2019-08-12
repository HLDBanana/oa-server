package com.hanergy.out.vo;

import com.hanergy.out.entity.Document;
import com.hanergy.out.entity.SysUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-07
 */
public class OaMeetingDetail {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String meetingId;

    private Integer meetingCapacity;

    private String meetingName;

    private Integer type;

    private String topic;

    private String content;

    private String date;

    private String beginTime;

    private String endTime;

    private Integer duration;

    private Integer location;

    private Long appointmentsId;

    private String appointmentsName;

    private String appointmentsPhone;

    private Integer state;

    private String uuid;

    private Long compere;

    private Integer secret;

    private  String solveQuestion;

    private Integer userNumber;

    private  String note;
    //主持人信息
    private SysUser compereUser;
    //参会人员信息
    private List<SysUser> userList = new ArrayList<>();
    // 抄送人
    private List<SysUser> ccUserList = new ArrayList<>();

    private String attachUrl;

    private String startUrl;

    private String joinUrl;

    private String password;

    private Integer zoomId;

    private String outsider;

    private List<Document> documents;

    public OaMeetingDetail(){}


    public String getOutsider() {
        return outsider;
    }

    public void setOutsider(String outsider) {
        this.outsider = outsider;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
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

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
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

    public SysUser getCompereUser() {
        return compereUser;
    }

    public void setCompereUser(SysUser compereUser) {
        this.compereUser = compereUser;
    }

    public List<SysUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUser> userList) {
        this.userList = userList;
    }

    public List<SysUser> getCcUserList() {
        return ccUserList;
    }

    public void setCcUserList(List<SysUser> ccUserList) {
        this.ccUserList = ccUserList;
    }

    protected Serializable pkVal() {
        return this.id;
    }

    public Integer getSecret() {
        return secret;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
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
