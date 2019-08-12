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
 * 文件表
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-10
 */
@TableName("document")
@ApiModel(value="Document对象", description="文件表")
public class Document extends Model<Document> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "文件原始名称")
    private String name;

    @ApiModelProperty(value = "文件存放地址")
    private String url;

    @ApiModelProperty(value = "[业务]文件类型: 0：声明书")
    private Long type;

    @ApiModelProperty(value = "上传时间")
    private Date createtime;

    @ApiModelProperty(value = "业务id")
    private Long serviceId;

    @ApiModelProperty(value = "排序(根据业务需要自定义)")
    private Integer orderNum;

    @ApiModelProperty(value = "原始文件类型类似pdf,word")
    private String fileType;

    @ApiModelProperty(value = "上传用户id")
    private Long uploadUserId;

    @ApiModelProperty(value = "上传者")
    private String uploadUserName;

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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public Long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(Long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }
    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Document{" +
        "id=" + id +
        ", name=" + name +
        ", url=" + url +
        ", type=" + type +
        ", createtime=" + createtime +
        ", serviceId=" + serviceId +
        ", orderNum=" + orderNum +
        ", fileType=" + fileType +
        ", uploadUserId=" + uploadUserId +
        ", uploadUserName=" + uploadUserName +
        "}";
    }
}
