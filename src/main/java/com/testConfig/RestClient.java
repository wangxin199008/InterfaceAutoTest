package com.testConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tools.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class RestClient {

    /**
     * 带json参数的post请求
     * @param url
     * @param parms
     * @return
     */
    public static JSONObject post(String url, String parms) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        post.addHeader("content-type", "application/json;charset=utf-8");
        post.setEntity(new StringEntity(parms, Charset.forName("utf-8")));
        HttpResponse response = null;
        String result = null;
        try {
            Log.info("开始发送post请求，请求的URL: " + url);
            Log.info("开始发送post请求，请求的参数: " + parms);
            response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
            System.out.println("请求不正确");
        }

        JSONObject responseObject = JSON.parseObject(result);
        //System.out.println("response: "+responseObject);
        return responseObject;
    }

    /**
     * GET请求
     * @param url
     * @return
     */
    public static JSONObject get(String url){
        //1.获得一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = null;
        JSONObject responseObject = null;
        try {
            Log.info("开始发送GET请求，请求URL：" +url);
            //执行get请求并获取相应
            response=httpClient.execute(get);
            result= EntityUtils.toString(response.getEntity());
            responseObject = JSON.parseObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseObject;

    }
}