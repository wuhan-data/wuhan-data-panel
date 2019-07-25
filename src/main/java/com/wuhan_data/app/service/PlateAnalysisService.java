package com.wuhan_data.app.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisPlate;

public interface PlateAnalysisService {
	//获取公共频度信息
	public List<String> getPublicFreq(List<AnalysisPlate> cpList);
	//获取公共时间段
	public List<String> getPublicTimeSpan(List<String> Freq,List<AnalysisPlate> cpList);
	//得到timeCondition
	public List<Map> getTimeCondition(List<String> Freq,List<String> timeSpan);
	//得到数据的主体部分
	public List getTotalList(List<AnalysisPlate> cpList,String startTime,String endTime,List<String> dataXais);
	//得到相关指标
	public List<Map> getRelatedData();
	//得到指标收藏信息
	public Map<String,Object> getBaseInfo();
	//将信息整合，返回param
	public Map<String,Object> returnParam();
	
	//*******************************************//
	//根据二级栏目id获得板块信息
	public List<AnalysisPlate> getAnalysisPlate(int themeId);
	

}
