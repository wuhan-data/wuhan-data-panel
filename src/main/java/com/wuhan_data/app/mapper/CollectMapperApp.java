package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.Collect;
import com.wuhan_data.tools.DataSource;

public interface CollectMapperApp {
	@DataSource(value="dataSource_mysql")
	public int add(Collect collect);
	@DataSource(value="dataSource_mysql")
	public void delete(int id);
	@DataSource(value="dataSource_mysql")
	public Collect get(int id);
	@DataSource(value="dataSource_mysql")
	public int update(Collect collect);
	@DataSource(value="dataSource_mysql")
	public List<Collect> list();
	@DataSource(value="dataSource_mysql")
	public int count();
	@DataSource(value="dataSource_mysql")
	public List<Collect> getByUid(int uid);

}
