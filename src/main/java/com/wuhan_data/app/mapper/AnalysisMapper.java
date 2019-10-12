package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisIndi;
import com.wuhan_data.pojo.AnalysisIndiTime;
import com.wuhan_data.pojo.AnalysisIndiValue;
import com.wuhan_data.pojo.AnalysisLabel;
import com.wuhan_data.pojo.AnalysisPlate;
import com.wuhan_data.pojo.AnalysisSearch;
import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.AnalysisType;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.tools.DataSource;

public interface AnalysisMapper {
	@DataSource(value = "dataSource_dm")
	public List<AnalysisType> getAnalysisTypeList(); // 查询经济分析一级栏目列表
	
	@DataSource(value = "dataSource_dm")
	public List<AnalysisLabel> getAnalysisLabelList(int typeId); // 查询经济分析标签分类列表

	@DataSource(value = "dataSource_dm")
	public List<AnalysisTheme> getAnalysisThemeList(int labelId); // 查询经济分析二级栏目列表
	
	@DataSource(value = "dataSource_dm")
	public List<AnalysisSearch> searchAnalysis(String keyword); // 根据关键词搜索二级栏目
	
	@DataSource(value = "dataSource_dm")
	public List<AnalysisPlate> getAnalysisPlate(int themeId); // 查询指定经济分析栏目下所有版块

	@DataSource(value = "dataSource_dm")
	public List<AnalysisIndi> getIndiByPid(int plateId); // 查询指定版块下的所有指标配置项

	@DataSource(value = "dataSource_dm")
	public List<AnalysisTheme> getThemeBaseInfo(Integer themeId);

	@DataSource(value = "dataSource_mysql")
	public List<String> getFreqnameByIndicode(String indiCode); // 根据指标code查询所有可取的频度数据

	@DataSource(value = "dataSource_mysql")
	public List<AnalysisIndiTime> getTimeByFreq(Map<String, Object> queryMap); // 根据指标code查询所有可取的起始结束时间

	@DataSource(value = "dataSource_mysql")
	public List<String> getTimeByFreqname(Map<String, Object> queryMap); // 根据频度信息获取可取的时间范围

	@DataSource(value = "dataSource_mysql")
	public List<AnalysisIndiValue> getIndiValue(Map<String, Object> queryMap); // 查询指标信息

}
