package com.hanergy.out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hanergy.out.vo.OaMeetingSetVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * </p>
 * @author Du Ronghong
 * @since 2019-05-28
 */
@TableName("oa_meeting_set")
@ApiModel(value="OaMeetingSet对象", description="")
public class OaMeetingSet extends Model<OaMeetingSet> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "会议室类型")
    private String meetingType;

    @ApiModelProperty(value = "会议室名称")
    private String meetingName;

    @ApiModelProperty(value = "会议室容量")
    private Integer meetingCapacity;

    @ApiModelProperty(value = "最少参会人数")
    private Integer minCapacity;

    @ApiModelProperty(value = "成本/小时")
    private Double cost;

    @ApiModelProperty(value = "投影大屏")
    private Integer screen;

    @ApiModelProperty(value = "视频")
    private Integer video;

    @ApiModelProperty(value = "白板数量")
    private Integer board;

    @ApiModelProperty(value = "音响数量")
    private Integer sound;

    @ApiModelProperty(value = "dvd数量")
    private Integer dvd;

    @ApiModelProperty(value = "VGA电视数量")
    private Integer vgaTelevision;

    @ApiModelProperty(value = "投影仪")
    private Integer projector;

    @ApiModelProperty(value = "具体位置 例： A1 配楼  ")
    private String location;

    @ApiModelProperty(value = "0：失效 1：有效")
    private Integer state;

    @ApiModelProperty(value = "会议室类型：1实体会议室，2视频会议室")
    private Integer type;

    @ApiModelProperty(value = "作废：会议室主键/虚拟会议室userid")
    private String meetingId;

    @ApiModelProperty(value = "创建时间")
    private String creDate;

    @ApiModelProperty(value = "权限部门")
    private String deptPower;

    @ApiModelProperty(value = "权限人员")
    private String userPower;

    @ApiModelProperty(value = "zoom账号对应邮箱")
    private String email;

    @ApiModelProperty(value = "审批人id")
    private String approval;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
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

    public void setMeetingCapacity(Integer meetingCapacity) {
        this.meetingCapacity = meetingCapacity;
    }
    public Double getCost() {
        return cost;
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

    public String getCreDate() {
        return creDate;
    }

    public void setCreDate(String creDate) {
        this.creDate = creDate;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public OaMeetingSet(){}
    public OaMeetingSet(OaMeetingSetVo param) {
        this.meetingType = param.getMeetingType();
        this.meetingName = param.getMeetingName();
        this.meetingCapacity = param.getMeetingCapacity();
        this.minCapacity = param.getMinCapacity();
        this.screen = param.getScreen();
        this.video = param.getVideo();
        this.cost = param.getCost();
        this.board = param.getBoard();
        this.sound = param.getSound();
        this.dvd = param.getDvd();
        this.vgaTelevision = param.getVgaTelevision();
        this.projector = param.getProjector();
        this.location = param.getLocation();
        this.state = param.getState();
        this.creDate = param.getCreDate();
        String deptPower = "";
        if (param.getDepts() != null){
            for (int i = 0; i < param.getDepts().size(); i++) {
                if (i == 0){
                    deptPower = deptPower + param.getDepts().get(i);
                } else {
                    deptPower = deptPower + "," + param.getDepts().get(i);
                }
            }
        }
        this.deptPower = deptPower;

        String userPower = "";
        if (param.getUsers() != null){
            for (int i = 0; i < param.getUsers().size(); i++) {
                if (i == 0) {
                    userPower = userPower + param.getUsers().get(i);
                } else {
                    userPower = userPower + "," + param.getUsers().get(i);
                }
            }
        }
        this.userPower = userPower;
        String approval = "";
        if (param.getApprovals() != null){
            for (int i = 0; i < param.getApprovals().size(); i++) {
                if (i == 0) {
                    approval = approval + param.getApprovals().get(i);
                } else {
                    approval = approval + "," + param.getApprovals().get(i);
                }
            }
        }
        this.approval = approval;
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

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getVideo() {
        return video;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OaMeetingSet{" +
        "id=" + id +
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
