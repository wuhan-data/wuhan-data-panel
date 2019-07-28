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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.app.service.PlateInfoService;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.tools.StringToMap;

@Controller
@RequestMapping("")
public class AnalysisController {

	@Autowired
	AnalysisService analysisService;
	@Autowired
	PlateInfoService plateInfoService;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;

	@RequestMapping(value = "getAnalysisList", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getAnalysisList(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String token = "";
		Integer userId = 0;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}

		try {
			if (!token.equals("")) {
				String mapString = sessionSQLServiceApp.get(token).getSess_value();
				Map map = StringToMap.stringToMap(mapString);
				userId= Integer.valueOf((String) map.get("userId"));
			}
		} catch (Exception e) {
//			return this.apiReturn("-1", "无效的token令牌", data);
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

		Map<String, Object> analysisPlate = new HashMap<String, Object>();
		try {
			// 获取栏目下的版块信息
			analysisPlate = analysisService.initAnalysisPlate(indexId);
		} catch (Exception e) {
			return this.apiReturn("-1", "获取数据异常", data);
		}
		return this.apiReturn("0", "数据获取成功", analysisPlate);
	}

	@RequestMapping(value = "getAnalysisDetailByTime", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getAnalysisDetailByTime(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		String token = "";
		int indexId = 0;
		String startTime = "";
		String endTime = "";
		String freqName = "";
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			System.out.println(requestObject.toString());
			token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
			boolean hasIndexId = requestObject.containsKey("indexId");
			boolean hasStartTime = requestObject.containsKey("startTime");
			boolean hasEndTime = requestObject.containsKey("endTime");
			boolean hasFreqName = requestObject.containsKey("freqName");
			if (!hasIndexId) {
				return this.apiReturn("-1", "需要指定栏目id", data);
			}
			if (!hasStartTime) {
				return this.apiReturn("-1", "需要选择起始时间", data);
			}
			if (!hasEndTime) {
				return this.apiReturn("-1", "需要选择结束时间", data);
			}
			if (!hasFreqName) {
				return this.apiReturn("-1", "需要选择时间频度", data);
			}
			String indexIdString = requestObject.get("indexId").toString();
			indexId = Integer.parseInt(indexIdString);
			startTime = requestObject.get("startTime").toString();
			endTime = requestObject.get("endTime").toString();
			freqName = requestObject.get("freqName").toString();
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}

		Map<String, Object> analysisPlate = new HashMap<String, Object>();
//		try {
		// 获取栏目下的版块信息
		analysisPlate = analysisService.initAnalysisPlateByTime(indexId, startTime, endTime, freqName);
//		} catch (Exception e) {
//			return this.apiReturn("-1", "获取数据异常", data);
//		}
		return this.apiReturn("0", "数据获取成功", analysisPlate);
	}

	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("errCode", errCode);
		responseMap.put("errMsg", errMsg);
		responseMap.put("data", data);
		return JSON.toJSONString(responseMap, SerializerFeature.DisableCircularReferenceDetect);
	}

	// 下面的都是测试接口

	@RequestMapping(value = "getPlateById", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getPlateById(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		int indexId = 0;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
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
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取栏目下的版块信息
		List<AnalysisPlate> analysisPlate = analysisPlate = analysisService.getAnalysisPlate(indexId);
		result.put("plateList", analysisPlate);
		return this.apiReturn("0", "数据获取成功", result);
	}

	@RequestMapping(value = "getIndiById", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getIndiById(@RequestBody String resquestParams) {
		JSONObject requestObject = JSONObject.parseObject(resquestParams);
		int plateId = 0;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			boolean hasIndexId = requestObject.containsKey("plateId");
			if (!hasIndexId) {
				return this.apiReturn("-1", "需要指定版块id", data);
			}
			String plateIdString = requestObject.get("plateId").toString();
			plateId = Integer.parseInt(plateIdString);
		} catch (Exception e) {
			return this.apiReturn("-1", "参数获取异常", data);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		// 获取栏目下的版块信息
		List<AnalysisIndi> analysisPlate = analysisService.getAnalysisIndi(plateId);
		result.put("indiList", analysisPlate);
		return this.apiReturn("0", "数据获取成功", result);
	}

}
