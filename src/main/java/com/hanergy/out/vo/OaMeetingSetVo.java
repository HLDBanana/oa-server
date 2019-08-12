package com.hanergy.out.vo;

import com.hanergy.out.entity.Document;
import com.hanergy.out.entity.SysDept;
import com.hanergy.out.entity.SysUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OaMeetingSetVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/28 13:41)
 */
public class OaMeetingSetVo {

    private String id;

    private String sysDeptId;

    private String sysDeptName;

    private String meetingName;

    private String meetingType;

    private Integer minCapacity;

    private Integer meetingCapacity;

    private Integer screen;

    private Integer video;

    private Double cost;

    private Integer board;

    private Integer sound;

    private Integer dvd;

    private Integer vgaTelevision;

    private Integer projector;

    private String location;

    private Integer state;

    private Integer type;

    private String meetingId;

    private String creDate;

    private Long userId;

    private String userName;

    private List<Long> users = new ArrayList<>();

    private List<Long> approvals = new ArrayList<>();

    private List<String> depts = new ArrayList<>();

    private Document document;

    private String image;

    private String deptPower;

    //审批人
    private String approval;

    //审批人信息
    private List<SysUser> approvalList = new ArrayList<>();

    //审批人
    private String eamil;
    // 部门详细信息
    private List<SysDept> deptList = new ArrayList<>();
    // 用户信息
    private String userPower;
    // 部门详细信息
    private List<SysUser> userList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSysDeptId() {
        return sysDeptId;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }

    public void setSysDeptId(String sysDeptId) {
        this.sysDeptId = sysDeptId;
    }
    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
    public Integer getMeetingCapacity() {
        return meetingCapacity;
    }

    public List<Long> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<Long> approvals) {
        this.approvals = approvals;
    }

    public void setMeetingCapacity(Integer meetingCapacity) {
        this.meetingCapacity = meetingCapacity;
    }
    public Double getCost() {
        return cost;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public List<SysUser> getApprovalList() {
        return approvalList;
    }

    public void setApprovalList(List<SysUser> approvalList) {
        this.approvalList = approvalList;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    public Integer getBoard() {
        return board;
    }

    public void setBoard(Integer board) {
        this.board = board;
    }
    public Integer getSound() {
        return sound;
    }

    public void setSound(Integer sound) {
        this.sound = sound;
    }
    public Integer getDvd() {
        return dvd;
    }

    public void setDvd(Integer dvd) {
        this.dvd = dvd;
    }
    public Integer getVgaTelevision() {
        return vgaTelevision;
    }

    public void setVgaTelevision(Integer vgaTelevision) {
        this.vgaTelevision = vgaTelevision;
    }
    public Integer getProjector() {
        return projector;
    }

    public void setProjector(Integer projector) {
        this.projector = projector;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getSysDeptName() {
        return sysDeptName;
    }

    public void setSysDeptName(String sysDeptName) {
        this.sysDeptName = sysDeptName;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public List<String> getDepts() {
        return depts;
    }

    public void setDepts(List<String> depts) {
        this.depts = depts;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getCreDate() {
        return creDate;
    }

    public void setCreDate(String creDate) {
        this.creDate = creDate;
    }

    public String getDeptPower() {
        return deptPower;
    }

    public void setDeptPower(String deptPower) {
        this.deptPower = deptPower;
    }

    public String getUserPower() {
        return userPower;
    }

    public void setUserPower(String userPower) {
        this.userPower = userPower;
    }

    public List<SysDept> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<SysDept> deptList) {
        this.deptList = deptList;
    }

    public List<SysUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUser> userList) {
        this.userList = userList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    protected Serializable pkVal() {
        return this.id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "OaMeetingSet{" +
                "id=" + id +
                ", sysDeptId=" + sysDeptId +
                ", meetingName=" + meetingName +
                ", meetingCapacity=" + meetingCapacity +
                ", cost=" + cost +
                ", board=" + board +
                ", sound=" + sound +
                ", dvd=" + dvd +
                ", vgaTelevision=" + vgaTelevision +
                ", projector=" + projector +
                ", location=" + location +
                ", state=" + state +
                ", type=" + type +
                ", meetingId=" + meetingId +
                "}";
    }
}
