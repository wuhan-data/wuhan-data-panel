package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiAll;
import com.wuhan_data.tools.DataSource;

public interface DbToExcelMapper {
	@DataSource(value="dataSource_mysql")
	List<IndiAll> selectIndi(String para);
	@DataSource(value="dataSource_dm")
	List<IndexManage> getIndi(String keyword);
	@DataSource(value="dataSource_dm")
	List<String> getIndiSourceByIndiName(String indiName);
	@DataSource(value="dataSource_mysql")
	List<String> getIndiFreqCode(Map<String, String> indiNameSourceMap);
	@DataSource(value="dataSource_mysql")
	List<String> getIndiStartTime(Map<String, String> indiNameSourceFreqMap);
	@DataSource(value="dataSource_mysql")
	List<String> getIndiEndTime(Map<String, String> indiNameSourceFreqSTimeMap);
	@DataSource(value="dataSource_mysql")
	List<IndiAll> getSelectIndex(Map<String, String> indiConditionMap);
	@DataSource(value="dataSource_mysql")
	List<String> getIndiTimePoint(Map<String, String> indiNameSourceMap);

}
