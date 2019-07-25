package com.wuhan_data.app.service;

import java.util.ArrayList;
import java.util.List;

import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;

public interface AnalysisService {
	public ArrayList<Object> getAnalysisList(int userId); // 经济分析一级栏目列表接口

	public ArrayList<Object> getAnalysisSubList(int typeId, int userId); // 经济分析二级栏目列表接口

	public List<AnalysisTheme> getAnalysisRoleList(List<AnalysisTheme> subList, ArrayList<String> roleList);

	public ArrayList<Object> getAnalysisPlate(int themeId); // 查询经济分析栏目下所有版块
}
