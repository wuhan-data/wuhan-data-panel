package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.app.service.PlateInfoService;

@Controller
@RequestMapping("")
public class AnalysisController {

	@Autowired
	AnalysisService analysisService;
	@Autowired
	PlateInfoService plateInfoService;

	@RequestMapping(value = "getAnalysisList", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getAnalysisList(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String token = "";
		Integer userId = 0;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
			userId = 1;
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}

		// 获取经济分析栏目列表数据
		ArrayList<Object> analysisList = new ArrayList<Object>();
		
		
		try {
			analysisList = analysisService.getAnalysisList(userId);
		} catch (Exception e) {
			return this.apiReturn("-1", "获取数据异常", data);
		}
		data.put("list", analysisList);

		return this.apiReturn("0", "成功获取数据", data);
	}

	@RequestMapping(value = "getAnalysisDetail", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getAnalysisDetail(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String token = "";
		int indexId = 0;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			System.out.println(requestObject.toString());
			token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
			boolean hasIndexId = requestObject.containsKey("indexId");
			if (!hasIndexId) {
				return this.apiReturn("-1", "需要指定栏目id", data);
			}
			String indexIdString = requestObject.get("indexId").toString();
			indexId = Integer.parseInt(indexIdString);
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}

		// 获取栏目下的版块信息
		Map<String, Object> analysisPlate = analysisService.getAnalysisPlate(indexId);
		
		
		
		return this.apiReturn("0", "数据获取成功", analysisPlate);
	}

	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("errCode", errCode);
		responseMap.put("errMsg", errMsg);
		responseMap.put("data", data);
		return JSON.toJSONString(responseMap, SerializerFeature.DisableCircularReferenceDetect);
	}

}
