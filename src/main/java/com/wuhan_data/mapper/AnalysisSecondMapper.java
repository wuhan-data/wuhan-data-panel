package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisTheme;

public interface AnalysisSecondMapper {
	
	public int add(AnalysisTheme analysisTheme);
	
	public void delete(int id);
	
	public int update(AnalysisTheme analysisTheme);
	
	public List<AnalysisTheme> getlist(Map<String,Object> map);//根据栏目id查找所有指标
		
	public int updateShow(AnalysisTheme analysisTheme);  //管理显示与否
	
	public int updateWeight(Map<String,Object> map);
	
	public int getThemeId(Map<String,Object> map);
	
	public int getMaxWeight(int label_id);
}
