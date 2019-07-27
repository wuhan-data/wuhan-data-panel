package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisIndiValue;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisTheme;

public interface AnalysisMapper {
	public List<AnalysisTheme> getAnalysisList(); // 查询经济分析一级栏目列表

	public List<AnalysisTheme> getAnalysisSubList(int typeId); // 查询经济分析二级栏目列表

	public List<AnalysisPlate> getAnalysisPlate(int themeId); // 查询指定经济分析栏目下所有版块

	public List<AnalysisIndi> getIndiByPid(int plateId); // 查询指定版块下的所有指标配置项

	public List<String> getFreqnameByIndicode(String indiCode); // 根据指标code查询所有可取的频度数据

	public List<String> getTimeByFreqname(Map<String, Object> queryMap); // 根据频度信息获取可取的时间范围

	public List<AnalysisIndiValue> getIndiValue(Map<String, Object> queryMap); // 查询指标信息
}
