package com.hanergy.out.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-11
 */
@TableName("oa_meeting_set_power")
@ApiModel(value="OaMeetingSetPower对象", description="")
public class OaMeetingSetPower extends Model<OaMeetingSetPower> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "redis分布式主键")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "会议室id")
    private String oaMeetingSetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getOaMeetingSetId() {
        return oaMeetingSetId;
    }

    public void setOaMeetingSetId(String oaMeetingSetId) {
        this.oaMeetingSetId = oaMeetingSetId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OaMeetingSetPower{" +
        "id=" + id +
        ", deptId=" + deptId +
        ", userId=" + userId +
        ", oaMeetingSetId=" + oaMeetingSetId +
        "}";
    }
}
