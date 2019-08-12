package com.hanergy.out.vo;

import com.hanergy.out.entity.SysUser;
import org.springframework.data.web.PageableArgumentResolver;

import java.util.List;

/**
 * @ClassName CXFVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/6/17 10:54)
 */
public class CXFVo {
    private String day;
    /*
     * 会议开始时间
     */
    private String beginTime;
    /*
     * 会议开始时间
     */
    private String endTime;
    /*
     * 1:实体会议
     * 2：虚拟会议
     * 3：全部
     */
    private Integer type;

    /*
     * 会议室基本信息id  外键关联
     */
    private String meetingId;
    /*
     * 会议主题
     */
    private String topic;


    /*
     * 会议内容
     */
    private String content;
    /*
     * 拟解决问题
     */
    private  String solveQuestion;
    /*
     * 是否涉密 1：涉密 2：非密
     */
    private  Integer secret;
    /*
     * 参会人数
     */
    private Integer userNumber;
    /*
     * 最少参会人数
     */
    private Integer minCapacity;
    /*
     * 外部参会人员
     */
    private String outsider;
    /*
     * 会议室名称
     */
    private String meetingName;

    private String fd_meetingclassify;
    /*
     * 用户id
     */
    private String userId;
    /*
     * 用户姓名
     */
    private String userName;
    //用户邮箱
    private String userEmail;

    // 主持人姓名
    private String compereName;
    // 参会人员姓名
    private String userListOfName;

    private String fd_meetingapprovals;


    public CXFVo(){}

    public CXFVo(MeetingParamVo paramVo){
        this.content = paramVo.getContent();
        this.topic = paramVo.getTopic();
        this.beginTime = paramVo.getBeginTime();
        this.endTime = paramVo.getEndTime();
        this.day = paramVo.getDay();
        this.meetingId = paramVo.getMeetingId();
        this.minCapacity = paramVo.getMinCapacity();
        this.outsider = paramVo.getOutsider();
        this.secret =paramVo.getSecret();
        this.userNumber = paramVo.getUserNumber();
        this.type = paramVo.getType();
        this.solveQuestion = paramVo.getSolveQuestion();
        this.meetingName = paramVo.getMeetingName();
    }

    public String getFd_meetingapprovals() {
        return fd_meetingapprovals;
    }

    public void setFd_meetingapprovals(String fd_meetingapprovals) {
        this.fd_meetingapprovals = fd_meetingapprovals;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCompereName() {
        return compereName;
    }

    public void setCompereName(String compereName) {
        this.compereName = compereName;
    }

    public String getUserListOfName() {
        return userListOfName;
    }

    public void setUserListOfName(String userListOfName) {
        this.userListOfName = userListOfName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSolveQuestion() {
        return solveQuestion;
    }

    public void setSolveQuestion(String solveQuestion) {
        this.solveQuestion = solveQuestion;
    }

    public Integer getSecret() {
        return secret;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFd_meetingclassify() {
        return fd_meetingclassify;
    }

    public void setFd_meetingclassify(String fd_meetingclassify) {
        this.fd_meetingclassify = fd_meetingclassify;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }
}
