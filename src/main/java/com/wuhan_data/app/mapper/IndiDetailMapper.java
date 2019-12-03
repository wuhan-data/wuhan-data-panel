package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.TPIndiValue;
import com.wuhan_data.tools.DataSource;

public interface IndiDetailMapper {
	
	@DataSource(value="dataSource_mysql")
	List<String> getFreqCodeByIndiName(Map fcMap);
	@DataSource(value="dataSource_mysql")
	List<String> indiDateByFreqName(Map paraMap);
	@DataSource(value="dataSource_mysql")
	List<TPIndiValue> getIndiValue(Map<String, Object> map);
	@DataSource(value="dataSource_dm")
	void indiCollect(Collect collect);
	@DataSource(value="dataSource_dm")
	String getIndiShowType(Map showMap);
	@DataSource(value="dataSource_mysql")
	String getIndiCode(Map paraMap);
	@DataSource(value="dataSource_mysql")
	String getIndexName(String indexCode);
	@DataSource(value="dataSource_dm")
	int getIsFavorite(Map favoriteMap);
	@DataSource(value="dataSource_dm")
	int getIndexStatus(Map indiNameAndSourceMap);
	@DataSource(value="dataSource_mysql")
	List<String> getFreqCodeByIndiNameG(Map fcMap);
	@DataSource(value="dataSource_mysql")
	List<String> indiDateByFreqNameG(Map paraMap);
	@DataSource(value="dataSource_mysql")
	List<String> getAreaNameListG(Map paraMap);
	@DataSource(value="dataSource_mysql")
	List<String> indiDateByFreqNameG1(Map parameterMap);
	@DataSource(value="dataSource_mysql")
	List<TPIndiValue> getIndiValueG(Map<String, Object> map);
	@DataSource(value="dataSource_mysql")
	String getIndexNameH(String indexCode);
	@DataSource(value="dataSource_mysql")
	List<String> getFreqCodeByIndiNameArea(Map fcMap);
	@DataSource(value="dataSource_mysql")
	List<String> indiDateByFreqNameArea(Map paraMap);
	@DataSource(value="dataSource_mysql")
	List<String> getIndiAreaList(Map paraMap);
	@DataSource(value="dataSource_mysql")
	List<String> indiDateByFreqNameDefault(Map parameterMap);
	@DataSource(value="dataSource_mysql")
	List<TPIndiValue> getIndiValueArea(Map defaultMap);
	@DataSource(value="dataSource_mysql")
	String getIndiCodeG(Map paraMap);

}
