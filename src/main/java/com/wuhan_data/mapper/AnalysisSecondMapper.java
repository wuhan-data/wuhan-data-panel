package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.tools.DataSource;

public interface AnalysisSecondMapper {
	@DataSource(value="dataSource_dm")
	public int add(AnalysisTheme analysisTheme);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public int update(AnalysisTheme analysisTheme);
	@DataSource(value="dataSource_dm")
	public List<AnalysisTheme> getlist(Map<String,Object> map);//根据栏目id查找所有指标
	@DataSource(value="dataSource_dm")
	public int updateShow(AnalysisTheme analysisTheme);  //管理显示与否
	@DataSource(value="dataSource_dm")
	public int updateWeight(Map<String,Object> map);
	@DataSource(value="dataSource_dm")
	public int getThemeId(Map<String,Object> map);
	@DataSource(value="dataSource_dm")
	public int getMaxWeight(int label_id);
}
