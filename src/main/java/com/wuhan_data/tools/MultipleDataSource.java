package com.wuhan_data.tools;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author linmeng
 * 自定义数据库切换类
 */
public class MultipleDataSource extends AbstractRoutingDataSource{
 
	
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return DbContextHolder.getDataSource();
	}
 
	
}

