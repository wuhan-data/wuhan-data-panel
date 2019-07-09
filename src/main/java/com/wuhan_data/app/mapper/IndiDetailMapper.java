package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.tools.DataSource;

public interface IndiDetailMapper {
	
	@DataSource(value="dataSource_mysql")
	List<String> getFreqCodeByIndiName(String appIndiName);
	@DataSource(value="dataSource_mysql")
	List<String> indiDateByFreqName(Map paraMap);

}
