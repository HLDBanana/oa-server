package com.hanergy.out.utils;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName HttpClient
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/7 13:54)
 */

public class HttpClient {

    /**
     * 向目的URL发送post请求
     *
     * @param url          目的url
     * @param headerParams 请求头参数
     * @param bodyParams   请求体参数
     * @return AdToutiaoJsonTokenData
     */
    public static String sendPostRequest(String url, Map<String, String> headerParams,
                                         Map<String, String> bodyParams) {
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //给请求头设置参数
        for (String key : headerParams.keySet()) {
            headers.add(key, headerParams.get(key));
        }
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 设置提交方式这里设置成application/json格式
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }


    /**
     * 向目的URL发送get请求
     *
     * @param url          目的url
     * @param bodyParams   请求体参数
     * @param headerParams 请求头参数
     * @return String
     */
    public static String sendGetRequest(String url, Map<String, String> headerParams,
                                        Map<String, String> bodyParams) {
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //给请求头设置参数
        for (String key : headerParams.keySet()) {
            headers.add(key, headerParams.get(key));
        }
        //application/json
        //headers.add("Content-Type","application/json");
        //设置请求方式 get/post
        HttpMethod method = HttpMethod.GET;
        // 设置提交方式这里设置成application/json格式
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }

    /**
     * 向目的URL发送delect请求
     *
     * @param url          目的url
     * @param headerParams 请求头参数
     * @param bodyParams   请求体参数
     * @return AdToutiaoJsonTokenData
     */
    public static String sendDelectRequest(String url, Map<String, String> headerParams,
                                           Map<String, String> bodyParams) {
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //给请求头设置参数
        for (String key : headerParams.keySet()) {
            headers.add(key, headerParams.get(key));
        }
        //设置请求发送方式
        HttpMethod method = HttpMethod.DELETE;
        // 设置提交方式这里设置成application/json格式
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }

    /**
     * 向目的URL发送patch请求
     * 由于httpclient不支持patch请求，所以需要反射方式获取连接对象，增加patch方式
     * @param url          目的url
     * @param headerParams 请求头参数
     * @param bodyParams   请求体参数
     * @return AdToutiaoJsonTokenData
     */
    public static String sendPatchRequest(String url, Map<String, String> headerParams,
                                          Map<String, String> bodyParams) {

        //httpclient不支持patch请求，反射方式获取连接对象，增加patch方式
        allowMethods("PATCH");
        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //给请求头设置参数
        for (String key : headerParams.keySet()) {
            headers.add(key, headerParams.get(key));
        }
        //headers.add("X-HTTP-Method-Override", "PATCH");
        //设置请求发送方式
        HttpMethod method = HttpMethod.PATCH;
        // 设置提交方式这里设置成application/json格式
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }
    //增加支持patch请求方式
    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}


    /**
     * 向目的URL发送patch请求
     * @param url       目的url
     * @param headerParams    请求头参数
     * @param bodyParams    请求体参数
     * @return  AdToutiaoJsonTokenData

    public static String sendPatchRequest1(String url, Map<String, String> headerParams,
                                          Map<String, String> bodyParams){
        JSONObject resultObj = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader("Content-type", "application/json");
        httpPatch.setHeader("Charset", HTTP.UTF_8);
        httpPatch.setHeader("Accept", "application/json");
        httpPatch.setHeader("Accept-Charset", HTTP.UTF_8);
        try {
            if (jsonParam != null){
                StringEntity entity = new StringEntity(jsonParam.toString(),HTTP.UTF_8);
                httpPatch.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPatch);
            resultObj = new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (ParseException | JSONException | IOException   e) {
            e.printStackTrace();
        }

        return resultObj;

    }
     */