package com.wuhan_data.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;

public interface AnalysisService {
	public ArrayList<Object> getAnalysisList(int userId); // 经济分析一级栏目列表接口

	public ArrayList<Object> getAnalysisSubList(int typeId, int userId); // 经济分析二级栏目列表接口

	public List<AnalysisTheme> getAnalysisRoleList(List<AnalysisTheme> subList, ArrayList<String> roleList);

	public Map<String, Object> initAnalysisPlate(int themeId); // 查询经济分析栏目下所有版块

	public Map<String, Object> initAnalysisPlateByTime(int themeId, String startTime, String endTime, String freqName); // 根据时间查询经济分析栏目下版块数据

	public List<Object> getClassInfo(List<AnalysisPlate> analysisPlate, Map<String, Object> queryMap,
			List<String> xAxis, List<String> timeList);

	public List<AnalysisPlate> getAnalysisPlate(int themeId);

	public List<AnalysisIndi> getAnalysisIndi(int plateId);
}
