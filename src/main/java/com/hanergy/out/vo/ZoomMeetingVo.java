package com.hanergy.out.vo;

/**
 * @ClassName ZoomMeetingVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/9 10:02)
 */
public class ZoomMeetingVo {

    //开启虚拟会议链接
    private String startUrl;

    //参加会议url
    private String joinUrl;

    //虚拟会议室密码
    private String password;

    //预约zoom会议室id
    private int zoomId;

    public  ZoomMeetingVo(){}

    public ZoomMeetingVo(String startUrl, String joinUrl, String password, int zoomId) {
        this.startUrl = startUrl;
        this.joinUrl = joinUrl;
        this.password = password;
        this.zoomId = zoomId;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getZoomId() {
        return zoomId;
    }

    public void setZoomId(int zoomId) {
        this.zoomId = zoomId;
    }
}
