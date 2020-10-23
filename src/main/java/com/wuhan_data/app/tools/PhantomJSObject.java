package com.wuhan_data.app.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kim小根
 * @date 2020/10/21 16:15
 * <p>Description:</p>
 */
@Component
public class PhantomJSObject {
    /**
     * phantomjs路径
     */
    private String phantomJsPath;

    /**
     * 扩展功能JS路径
     */
    private String extendsJSPath;

    /**
     * echarts-convert服务器端口
     */
    private String port;

    /**
     * echarts-convert服务器url
     */
    private String url;
    /**
     * 空格
     */
    private static final String BLANK = " ";
    /**
     * 成功返回标识
     */
    private static final String SUCCESS_CODE = "0";
    /**
     * 接受参数返回标识
     */
    private static final String RECEIVE_CODE = "2";
    /**
     * 设置utf-8编码
     */
    private static final String ENCODING = "utf-8";

    public PhantomJSObject() {
    }

    public PhantomJSObject(String phantomJsPath, String extendsJSPath, String port, String url) {
        this.phantomJsPath = phantomJsPath;
        this.extendsJSPath = extendsJSPath;
        this.port = port;
        this.url = url;
        start();
    }

    /**
     * 启动echarts-conver服务端
     *
     * @return 返回子进程
     */

    public void start() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(phantomJsPath);
            sb.append(BLANK);
            sb.append(extendsJSPath);
            sb.append(" -s -p ").append(port);
            Runtime.getRuntime().exec(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void start() {
        String message = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(phantomJsPath);
            sb.append(BLANK);
            sb.append(extendsJSPath);
            sb.append(" -s -p ");
            sb.append(port);
            Runtime.getRuntime().exec(sb.toString());
            //InputStream input = process.getInputStream();
            //InputStream error = process.getErrorStream();
            *//*message = getReturnMessage(input);
            if (Objects.isNull(message)) {
                message = getReturnMessage(error);
                throw new RuntimeException();
            }*//*
        } catch (Exception e) {
            System.out.println(message);
        }
        //return process;
    }*/

    public void close() {
        HashMap<String, Object> pa = new HashMap<>();
        pa.put("exit", "true");
        phantomJS(pa);
    }

    /*private String getReturnMessage(InputStream input) {
        String result;
        try {
            result = String.valueOf(readInputStream(input));
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;
    }

    private byte[] readInputStream(InputStream inputStream) throws IOException {
        int len = 0, max = 1024;
        byte[] buffer = new byte[max];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer, len, len + max)) != -1) {
            bos.write(buffer, len, len + max);
        }
        bos.close();
        return bos.toByteArray();
    }*/

    /**
     * 根据参数发起请求，构造图片base64格式字符串
     *
     * @param params 参数map集合
     * @return base64字符串
     */
    public String phantomJS(Map<String, Object> params) {
        String base64 = "";
        String response = HttpUtil.post(url + ":" + port, params, ENCODING);
        //System.out.println("------------" + response);
        // 解析echartsConvert响应
        JSONObject responseJson = JSON.parseObject(response);
        String code = responseJson.getString("error_no");
        if (SUCCESS_CODE.equals(code)) {
            // base64 = responseJson.getString("data");
        } else if (RECEIVE_CODE.equals(code)) {
            base64 = responseJson.getString("imgdata");
        } else {// 未正常返回
            String string = responseJson.getString("error_info");
            throw new RuntimeException(string);
        }
        //
        return base64;
        /*String base64 = null;
        String response = HttpUtil.post(url + ":" + port, params, ENCODING);
        // 解析echarts-Convert响应
        JSONObject responseJson = JSON.parseObject(response);
        String code = responseJson.getString("error_no");
        // 如果echarts-Convert正常返回
        if(RECEIVE_CODE.equals(code)){ //此处获取base64
            base64 = responseJson.getString("imgdata");
        }else if (SUCCESS_CODE.equals(code)) {
            //成功返回，此处不做处理
        } else {// 未正常返回
            String string = responseJson.getString("error_info");
            throw new RuntimeException(string);
        }
        return base64;*/
    }

}
