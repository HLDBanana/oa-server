package com.hanergy.out.utils;

import com.hanergy.out.vo.ZoomUserVo;
import com.msyun.plugins.zoomapi.utils.JWTBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ZoomApi
 * @Description
 * @Auto HANLIDONG
 * @Date 2019-7-30 9:13)
 */
public class ZoomApi {

    private static Logger log = LoggerFactory.getLogger(ZoomApi.class);
    //zoom应用apikey和apiSecret
    public static final String apiKey = "k9C3n0KARi-oH9f4oqESAQ";
    public static final String apiSecret = "iRoNVe59xdHFu48tZdJG1CK9qjna0Au1MCLy";
    //获取用户列表
    public static final String userListUrl = "https://api.zoom.us/v2/users?state=active&page_number=1&page_size=200";


    public static List<ZoomUserVo> getZoomUsers(){
        // zoom用户集合
        List<ZoomUserVo> zoomUsers = new ArrayList<>();
        //根据api key和api secret生成token
        String token = "";
        try {
            log.info("获取token:");
            token = JWTBuilder.buildSignedJWTString(apiKey, apiSecret,1000000);
            log.info("token:"+token);
        }catch (Exception e){
            e.printStackTrace();
        }
        //设置请求头信息
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);

        String result = HttpClient.sendGetRequest(userListUrl,header,null);

        JSONObject json = JSONObject.fromObject(result);
        //解析数据数据
        JSONArray jsonArray = json.getJSONArray("users");
        for(int i=0;i<jsonArray .size();i++){
            String id = jsonArray.getJSONObject(i).getString("id");
            String firstName = jsonArray.getJSONObject(i).getString("first_name");
            String lastName = jsonArray.getJSONObject(i).getString("last_name");
            String email = jsonArray.getJSONObject(i).getString("email");
            int type = jsonArray.getJSONObject(i).getInt("type");
            ZoomUserVo zoomUserVo = new ZoomUserVo(id,firstName,lastName,email,type);
            zoomUsers.add(zoomUserVo);
        }
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~zoom用户数量 = "+ zoomUsers.size() +"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        return zoomUsers;
    }

}
