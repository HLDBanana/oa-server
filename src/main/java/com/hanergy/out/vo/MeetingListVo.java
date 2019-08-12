package com.hanergy.out.vo;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName MeetingListVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/30 10:23)
 */
public class MeetingListVo {
    // 用户id
    private Long userId;
    // 1：实体 2：虚拟
    private Integer type;
    // 1：所有的 2：我主持的 3：我参与的
    private Integer meetingType;
    // 日期 年月日
    private String day;
    // 开始时间
    private String beginTime;
    // 结束时间
    private String endTime;

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

    public Integer getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(Integer meetingType) {
        this.meetingType = meetingType;
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
}
