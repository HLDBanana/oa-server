package com.hanergy.out.utils;

import org.apache.http.client.methods.HttpPut;

/**
 * @ClassName HttpPatch
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/10 11:15)
 */
public class HttpPatch extends HttpPut {

    public HttpPatch(String url) {
        super(url);
    }
    @Override
    public String getMethod(){
        return "PATCH";
    }

}
