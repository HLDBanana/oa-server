package com.hanergy.out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@TableName("sys_user")
@ApiModel(value="SysUser对象", description="")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "异动类型：0在职；1离职；2异动；")
    private Integer flag;

    @ApiModelProperty(value = "异动结束时间")
    private String ydjs;

    @ApiModelProperty(value = "异动开始时间")
    private String ydks;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "邮箱前缀")
    private String username;

    @ApiModelProperty(value = "工号")
    private String jobNumber;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "职位主键")
    private String positionId;

    @ApiModelProperty(value = "职位名称")
    private String position;

    @ApiModelProperty(value = "部门主键")
    private String departmentId;

    @ApiModelProperty(value = "部门名称")
    private String department;

    @ApiModelProperty(value = "部门全路径主键 ，“，”隔开")
    private String departmentTree;

    @ApiModelProperty(value = "部门全路径名称，“，”隔开")
    private String departmentTreeName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "工作地点")
    private String workPlace;

    @ApiModelProperty(value = "毕业学校(距离当前最近的学历)")
    private String graduateSchool;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "上级汇报人工号")
    private String manage;

    @ApiModelProperty(value = "上级汇报人姓名  杨国栋19年3月14日新增")
    private String manageName;

    @ApiModelProperty(value = "上级汇报人主键  杨国栋19年3月14日新增")
    private Long manageId;

    @ApiModelProperty(value = "公司座机")
    private String phone;

    @ApiModelProperty(value = "入职时间")
    private String bigda;

    @ApiModelProperty(value = "离职时间")
    private String endda;

    @ApiModelProperty(value = "SAP中人员信息的修改时间，此时间与“变动开始时间”和“变动结束时间”的区别？")
    private String sourceTime;

    @ApiModelProperty(value = "创建者ID")
    private Long createUserId;

    @ApiModelProperty(value = "并非创建时间而是从各业务库刷新到本库的时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "【作废】盐")
    private String salt;

    @ApiModelProperty(value = "【SF数据，将来必须替换】用户类型: 1招聘总监 2 招聘负责人")
    private Integer userType;

    @ApiModelProperty(value = "【SF数据，将来必须替换】用户id")
    private String sfUserId;

    @ApiModelProperty(value = "【作废】状态：0禁用；1正常")
    private Integer status;

    @ApiModelProperty(value = "[作废]异动类型")
    private String ydlx;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    public String getYdjs() {
        return ydjs;
    }

    public void setYdjs(String ydjs) {
        this.ydjs = ydjs;
    }
    public String getYdks() {
        return ydks;
    }

    public void setYdks(String ydks) {
        this.ydks = ydks;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDepartmentTree() {
        return departmentTree;
    }

    public void setDepartmentTree(String departmentTree) {
        this.departmentTree = departmentTree;
    }
    public String getDepartmentTreeName() {
        return departmentTreeName;
    }

    public void setDepartmentTreeName(String departmentTreeName) {
        this.departmentTreeName = departmentTreeName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    public String getManage() {
        return manage;
    }

    public void setManage(String manage) {
        this.manage = manage;
    }
    public String getManageName() {
        return manageName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getBigda() {
        return bigda;
    }

    public void setBigda(String bigda) {
        this.bigda = bigda;
    }
    public String getEndda() {
        return endda;
    }

    public void setEndda(String endda) {
        this.endda = endda;
    }
    public String getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(String sourceTime) {
        this.sourceTime = sourceTime;
    }
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    public String getSfUserId() {
        return sfUserId;
    }

    public void setSfUserId(String sfUserId) {
        this.sfUserId = sfUserId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getYdlx() {
        return ydlx;
    }

    public void setYdlx(String ydlx) {
        this.ydlx = ydlx;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        "userId=" + userId +
        ", name=" + name +
        ", flag=" + flag +
        ", ydjs=" + ydjs +
        ", ydks=" + ydks +
        ", email=" + email +
        ", username=" + username +
        ", jobNumber=" + jobNumber +
        ", mobile=" + mobile +
        ", idCard=" + idCard +
        ", positionId=" + positionId +
        ", position=" + position +
        ", departmentId=" + departmentId +
        ", department=" + department +
        ", departmentTree=" + departmentTree +
        ", departmentTreeName=" + departmentTreeName +
        ", password=" + password +
        ", workPlace=" + workPlace +
        ", graduateSchool=" + graduateSchool +
        ", major=" + major +
        ", manage=" + manage +
        ", manageName=" + manageName +
        ", manageId=" + manageId +
        ", phone=" + phone +
        ", bigda=" + bigda +
        ", endda=" + endda +
        ", sourceTime=" + sourceTime +
        ", createUserId=" + createUserId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", salt=" + salt +
        ", userType=" + userType +
        ", sfUserId=" + sfUserId +
        ", status=" + status +
        ", ydlx=" + ydlx +
        "}";
    }
}
