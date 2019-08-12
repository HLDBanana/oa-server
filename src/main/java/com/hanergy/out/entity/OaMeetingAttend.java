package com.hanergy.out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("oa_meeting_attend")
@ApiModel(value="OaMeetingAttend对象", description="")
public class OaMeetingAttend extends Model<OaMeetingAttend> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "会议室id")
    private Long oaMeetingId;

    @ApiModelProperty(value = "参会人员/抄送人员id")
    private Long userId;

    @ApiModelProperty(value = "1：参会人员 2：抄送人员")
    private Integer type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOaMeetingId() {
        return oaMeetingId;
    }

    public void setOaMeetingId(Long oaMeetingId) {
        this.oaMeetingId = oaMeetingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
