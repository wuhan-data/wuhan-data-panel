package com.wuhan_data.app.mapper;

import java.util.Date;
import java.util.List;

import com.wuhan_data.pojo.SessionSQL;
import com.wuhan_data.tools.DataSource;

public interface SessionSQLMapperApp {
	@DataSource(value="dataSource_mysql")
	public int add(SessionSQL sessionSQL);
	@DataSource(value="dataSource_mysql")
	public void delete(String sess_key);
	@DataSource(value="dataSource_mysql")
	public SessionSQL get(String sess_key);
	@DataSource(value="dataSource_mysql")
	public int update(SessionSQL sessionSQL);
	@DataSource(value="dataSource_mysql")
	public int deleteTimeoutToken(Date timeout);
}
