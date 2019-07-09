package com.wuhan_data.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.IndiDetailService;

@Controller
@RequestMapping("")
public class IndiDetailAppController {
	@Autowired
	IndiDetailService indiDetailService;
	
	
	@RequestMapping(value="indiDetail",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String indiDetail() {
		
		//获得指标的年季度范围
		String appIndiName="地区生产总值";//应从app获得
		List<String> freqCodeList=indiDetailService.getFreqCodeByIndiName(appIndiName);
		Map map=new HashMap();
		map.put("freqCodeList", freqCodeList);
		
		String freqCode="SS";//用户选择获得频度代码和指标名字，获得指标的最小时间和最大时间
		Map ParaMap=new HashMap();
		ParaMap.put("freqCode", freqCode);
		ParaMap.put("appIndiName", appIndiName);
		List<String> indiDateList = indiDetailService.indiDateByFreqName(ParaMap);		
		String startTime=indiDateList.get(0).substring(0, 6);
		String endTime=indiDateList.get(indiDateList.size()-1).substring(0, 6);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		
		
		
		String  param= JSON.toJSONString(map);
		return param;
	}

}
