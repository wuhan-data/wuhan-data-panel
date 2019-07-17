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
	void indiCollect(Collect collect);
	@DataSource(value="dataSource_dm")
	String getIndiShowType(Map showMap);
	@DataSource(value="dataSource_dm")
	String getIndiCode(String appIndiName);

}
