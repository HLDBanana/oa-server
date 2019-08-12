package com.hanergy.out.controller;

import com.alibaba.fastjson.JSON;
import com.hanergy.out.entity.OaMeetingAttend;
import com.hanergy.out.service.IOaMeetingAttendService;
import com.hanergy.out.service.IRedisService;
import com.hanergy.out.utils.HttpClient;
import com.hanergy.out.utils.PinyinUtils;
import com.hanergy.out.utils.UrlUtil;
import com.msyun.plugins.zoomapi.utils.JWTBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Test
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/6 13:56)
 */
@RestController
@RequestMapping("/v1/test")
public class Test {
    Logger log = LoggerFactory.getLogger(OaMeetingController.class);

    @Autowired
    private IOaMeetingAttendService oaMeetingAttendService;

    @Autowired
    private IRedisService redisService;     //redis


    @RequestMapping("/getUserList")
    public  void  test(){
        String token = "";
        try {
            token = JWTBuilder.buildSignedJWTString(UrlUtil.apiKey, UrlUtil.apiSecret,10000000);
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String,String> param = new HashMap<>();
        param.put("status","active");
        param.put("page_size","30");
        param.put("page_number","1");

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);
        String connect = HttpClient.sendGetRequest(UrlUtil.userListUrl,header,param);
        JSONObject json = JSONObject.fromObject(connect);
        int a = json.getInt("total_records");
        JSONArray jsonArray = json.getJSONArray("users");
        String firstName = jsonArray.getJSONObject(0).getString("first_name");

        //String connect = httpClientUtils.get(UrlUtil.userListUrl,header,param);
    }


    @RequestMapping("/test")
    public  String  test1(){
        String a = "韩利东拼音测试";

        return PinyinUtils.convertHanzi2Pinyin(a,true);
    }

    @RequestMapping("/saveredis")
    public  void  saveredis(){
        OaMeetingAttend oaMeetingAttend = new OaMeetingAttend();
        oaMeetingAttend.setId((long)19);
        oaMeetingAttend.setType(0);
        redisService.lpush("outlook", JSON.toJSONString(oaMeetingAttend));
        //oaMeetingAttendService.updateMeetingAttend(oaMeetingAttend);
    }
    @RequestMapping("/redis")
    public  void  redis(){
       List<String> redisList = (List<String>) redisService.lrange("outlook",(long)0,(long)-1);
        for (int i = 0; i < redisList.size(); i++) {
            Object object = JSON.parseObject(redisList.get(i), OaMeetingAttend.class);
            if (object instanceof OaMeetingAttend) {
                OaMeetingAttend a = (OaMeetingAttend) object;
                log.info("读取缓存的对象"+object+"  OaMeetingAttend = "+a);
            }
        }
    }

    @RequestMapping("/token")
    public String getToken(){
        String token = null;
        try {
            token = JWTBuilder.buildSignedJWTString(UrlUtil.apiKey, UrlUtil.apiSecret,10000000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    @RequestMapping("/iekey")
    public String iekey(HttpServletRequest request){
        try {
            String key = request.getParameter("key");
            //解决get方式请求参数乱码
            //key = new String(key.getBytes("iso-8859-1"), "utf-8");
            return key;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
