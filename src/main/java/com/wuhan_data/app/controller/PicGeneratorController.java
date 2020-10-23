package com.wuhan_data.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.tools.PhantomJSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author Kim小根
 * @date 2020/10/16 17:14
 * <p>Description:图片生成</p>
 */
@Controller
public class PicGeneratorController {

    @Autowired
    PhantomJSObject phantomJSObject;

    @RequestMapping(value = "generatorPic", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String generatorPic(@RequestBody String resquestParams) {
        List<String> result = new ArrayList<>();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("errCode", "0");
        responseMap.put("errMsg", "数据获取成功");

        JSONArray jsonArray = JSONArray.parseArray(resquestParams);

        try {
            //phantomJSObject.start();
            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                String type = obj.get("classType").toString();
                if ("echarts".equals(type)) {
                    JSONObject JSONobj = (JSONObject) obj.get("echartOption");
                    Map<String, Object> objMap = (Map) JSON.parse(JSONobj.toJSONString());
                    Map<String, Object> params = new HashMap<>();
                    params.put("opt", objMap);
                    params.put("reqMethod", "echarts");
                    //params.put("file", "D:/echarts1.png");
                    String base64 = phantomJSObject.phantomJS(params);
                    if (Objects.nonNull(base64)) {
                        result.add(base64);
                    }
                }
            }
        } catch (RuntimeException e) {
            responseMap.put("errCode", "1");
            responseMap.put("errMsg", "数据获取失败");
            System.out.println(e.getMessage());
        } /*finally {
            //phantomJSObject.close();
        }*/
        responseMap.put("data", result);
        return JSON.toJSONString(responseMap, SerializerFeature.DisableCircularReferenceDetect);
    }

}
