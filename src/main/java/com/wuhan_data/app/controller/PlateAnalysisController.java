package com.wuhan_data.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wuhan_data.app.service.PlateAnalysisService;
import com.wuhan_data.pojo.AnalysisPlate;

public class PlateAnalysisController {
	@Autowired
	PlateAnalysisService plateAnalysisService;
	
	@RequestMapping(value="plate",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String plate() {
		//此处待添加try catch语句获取从前端得到的数据，
		int themeId=91;//仅用于测试
		List<AnalysisPlate> cpList=plateAnalysisService.getAnalysisPlate(themeId);
		List<String> getPublicFreq(List<AnalysisPlate> cpList)
		
	}

}
