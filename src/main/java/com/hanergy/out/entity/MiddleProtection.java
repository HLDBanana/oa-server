package com.hanergy.out.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 劳动保护  
2019年7月15日杨国栋创建
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-07-15
 */
@TableName("middle_protection")
@ApiModel(value="MiddleProtection对象", description="劳动保护2019年7月15日杨国栋创建")
public class MiddleProtection extends Model<MiddleProtection> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "劳保用品名称")
    private String name;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "岗位")
    private String post;

    @ApiModelProperty(value = "单位")
    private String company;

    @ApiModelProperty(value = "数量")
    private String number;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MiddleProtection{" +
        "id=" + id +
        ", name=" + name +
        ", date=" + date +
        ", post=" + post +
        ", company=" + company +
        ", number=" + number +
        ", userId=" + userId +
        ", userName=" + userName +
        ", remarks=" + remarks +
        "}";
    }
}
