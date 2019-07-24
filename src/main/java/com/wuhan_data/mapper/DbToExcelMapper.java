package com.wuhan_data.mapper;

import java.util.List;

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

}
