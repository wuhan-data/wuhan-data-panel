package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.ErrorLog;
import com.wuhan_data.tools.DataSource;


public interface ErrorLogMapper {
		@DataSource(value="dataSource_dm")
	    public int add(ErrorLog errorLog); 
		@DataSource(value="dataSource_dm")
	    public void delete(int id); 
		@DataSource(value="dataSource_dm")
	    public ErrorLog get(int id); 
		@DataSource(value="dataSource_dm")
	    public int update(ErrorLog errorLog);  
		@DataSource(value="dataSource_dm")
	    public List<ErrorLog> list();
		@DataSource(value="dataSource_dm")
	    public int count(); 
}
