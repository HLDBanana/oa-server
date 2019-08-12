package com.hanergy.out.vo;

import com.hanergy.out.entity.Document;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MeetingParamVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/7 16:31)
 */
public class MeetingParamVo {

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
     * 自动刷新
     */
    private String time;

    /*
     * 1:实体会议
     * 2：虚拟会议
     * 3：全部
     */
    private Integer type;
    // 用户id
    private Long userId;
    // 预约会议室数据id
    private Integer id;
    /*
     * 会议室基本信息id  外键关联
     */
    private String meetingId;
    /*
     * 虚拟会议id
     */
    private String virtualMeetingId;

    /*
     * 网络会议室id
     */
    private String meetingSetId;
    /*
     * 参会人数
     */
    private Integer number;
    /*
     * 会议主题
     */
    private String topic;

    /*
     * 主持人
     */
    private Long compere;

    // 主持人姓名
    private String compereName;

    // 1：所有的 2：我主持的 3：我参与的
    private Integer meetingType;
    /*
     * 参会人员id
     */
    private List<Long> userList;

    // 参会人员姓名
    private String userListOfName;
    /*
     * 抄送人员id
     */
    private List<Long> ccUserList;

    /*
     * 参会邮箱地址
     */
    private List<String> emailList;
    /*
     * 抄送人员邮箱地址
     */
    private List<String> ccEmailList;

    /*
     * 会议内容
     */
    private String content;

    /*
     * 附件url
     */
    private String attachUrl;

    //会议地址
    private String location;

    /*
     * 是否发送邮件 0：不发送 1：发送
     **/
    private Integer send;
    /*
     * outlook会议预约uuid
     */
    private String uuid;

    private Integer pageSize;

    private Integer pageNumber;

    private List<Document> documents = new ArrayList<>();

    private  String solveQuestion;

    private  Integer secret;

    private Integer userNumber;

    private  String note;

    private String outsider;

    private Integer minCapacity;

    private String meetingName;

    public String getOutsider() {
        return outsider;
    }

    public void setOutsider(String outsider) {
        this.outsider = outsider;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getMeetingSetId() {
        return meetingSetId;
    }

    public void setMeetingSetId(String meetingSetId) {
        this.meetingSetId = meetingSetId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Long> getUserList() {
        return userList;
    }

    public void setUserList(List<Long> userList) {
        this.userList = userList;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getVirtualMeetingId() {
        return virtualMeetingId;
    }

    public void setVirtualMeetingId(String virtualMeetingId) {
        this.virtualMeetingId = virtualMeetingId;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Long getCompere() {
        return compere;
    }

    public void setCompere(Long compere) {
        this.compere = compere;
    }

    public List<Long> getCcUserList() {
        return ccUserList;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCcUserList(List<Long> ccUserList) {
        this.ccUserList = ccUserList;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public List<String> getCcEmailList() {
        return ccEmailList;
    }

    public void setCcEmailList(List<String> ccEmailList) {
        this.ccEmailList = ccEmailList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMeetingType() {
        return meetingType;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setMeetingType(Integer meetingType) {
        this.meetingType = meetingType;
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

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
}
