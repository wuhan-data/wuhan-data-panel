package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.HistoryView;
import com.wuhan_data.tools.DataSource;

public interface HistoryViewMapperApp {
	@DataSource(value="dataSource_mysql")
	public int add(HistoryView historyView);
	@DataSource(value="dataSource_mysql")
	public void delete(int id);
	@DataSource(value="dataSource_mysql")
	public HistoryView get(int id);
	@DataSource(value="dataSource_mysql")
	public int update(HistoryView historyView);
	@DataSource(value="dataSource_mysql")
	public List<HistoryView> list();
	@DataSource(value="dataSource_mysql")
	public int count();
	@DataSource(value="dataSource_mysql")
	public List<HistoryView> getByUid(int uid);

}
