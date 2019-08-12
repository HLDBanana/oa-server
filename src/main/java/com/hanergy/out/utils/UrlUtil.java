package com.hanergy.out.utils;

/**
 * @ClassName UrlUtil
 * @Description zoom接口url地址
 * @Auto HANLIDONG
 * @Date 2019/5/7 13:34)
 */
public class UrlUtil {

    //zoom应用apikey和apiSecret
    public static final String apiKey = "k9C3n0KARi-oH9f4oqESAQ";
    public static final String apiSecret = "iRoNVe59xdHFu48tZdJG1CK9qjna0Au1MCLy";


    //获取用户列表
    public static final String userListUrl = "https://api.zoom.us/v2/users";

    //https://api.zoom.us/v2/users/UQEt3ag7TUKXeDcSbdCUUw/meetings
    //用户创建会议室和查询用户预约的会议室 需要拼接userid
    public static final String createMeeting = "https://api.zoom.us/v2/users/";

    public static final String selectMeeting = "https://api.zoom.us/v2/users/";

    //https://api.zoom.us/v2/meetings/{meetingId}
    //删除、更新会议室接口
    public static final String updateMeeting = "https://api.zoom.us/v2/meetings/";

}
