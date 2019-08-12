package com.hanergy.out.controller;

import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.IRedisService;
import com.hanergy.out.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @ClassName LoginTest
 * @Description
 * @Auto HANLIDONG
 * @Date 2019-8-8 15:11)
 */
@RequestMapping("/v1/login")
public class LoginTest {
    @Autowired
    public IRedisService redisService;

    public R loginTest(@RequestParam("userName")String userName,
                          @RequestParam("pwd")String pwd) {
        String redisPwd = redisService.stringGet(userName);
        if (pwd.equals(redisPwd)){
            return R.ok();
        } else {
            return R.error();
        }
    }

    public R log(@CookieValue("token")String token,
                 @RequestBody SysUser user) {

        if (token == null && user == null ){
            return R.error();
        }
        if (token != null && !"".equals(token)){
            // 通过token从redis读取缓存信息
            Object userObj = redisService.hashGet("token",token);
            if (userObj != null){
                return R.ok();
            }
        } else {
            //将用户信息表存到redis中
            Object obj = redisService.stringGet(user.getUsername()+"_"+user.getPassword());
            if (obj != null){
                String uuid = UUID.randomUUID().toString();
                //redis存储token信息
                redisService.hashSet("token",token,user);
                return R.ok();
            } else {
                return R.error();
            }
        }
        return R.error();
    }


}
