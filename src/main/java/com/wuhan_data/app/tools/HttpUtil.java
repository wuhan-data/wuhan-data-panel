package com.wuhan_data.app.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpUtil {

    /**
     * 超时时间
     */
    private static final int TIME_OUT = 1000 * 10;

    public static String post(String url, Map<String, Object> params, String charset) {
        String responseEntity = "";
        // 创建CloseableHttpClient对象
        //CloseableHttpClient client = HttpClients.createDefault();
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(TIME_OUT).build();
        CloseableHttpClient client = HttpClients.custom().setDefaultSocketConfig(socketConfig).build();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 将参数添加到post请求中
        httpPost.setEntity(new StringEntity(JSON.toJSONString(params), charset));
        // 发送请求，获取结果（同步阻塞）
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 按指定编码转换结果实体为String类型
                responseEntity = EntityUtils.toString(entity, charset);
            }
            // 释放资源
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }
}
