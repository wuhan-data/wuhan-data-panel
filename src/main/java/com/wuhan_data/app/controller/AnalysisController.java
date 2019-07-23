package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.app.service.InitColumnService;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.AnalysisTheme;

@Controller
@RequestMapping("")
public class AnalysisController {
	
	@Autowired
	AnalysisService analysisService;
	
	@RequestMapping(value = "getAnalysisList", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getAnalysisList(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String token = "";
//		String indexId = "";
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
//			boolean hasIndexId = requestObject.containsKey("indexId");
//			if (!hasIndexId) {
//				return this.apiReturn("-1", "需要指定栏目id", data);
//			}
		} catch (Exception e) {
			System.out.println("参数获取异常：" + e.getMessage());
		}
		
		// TODO 根据用户权限列表获取对应的analysis_list
		ArrayList<String> role_list = new ArrayList<String>();
		if (token != "") {
			// TODO 根据用户token获取对应的role_list
			role_list.add("analysis_zonghe");
		}
		
		ArrayList<Object> list = analysisService.getAnalysisList();
		data.put("list", list);
		
		return this.apiReturn("0", "成功获取数据", data);
	}

	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("errCode", errCode);
		responseMap.put("errMsg", errMsg);
		responseMap.put("data", data);
		return JSON.toJSONString(responseMap);
	}

}
