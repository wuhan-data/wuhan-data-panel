package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Version;
import com.wuhan_data.tools.DataSource;

public interface VersionMapperApp {
	@DataSource(value="dataSource_mysql")
	public int add(Version version);
	 @DataSource(value="dataSource_mysql")
	public void delete(int id);
	 @DataSource(value="dataSource_mysql")
	public Version get(int id);
	 @DataSource(value="dataSource_mysql")
	public int update(Version version);
	 @DataSource(value="dataSource_mysql")
	public List<Version> list();
	 @DataSource(value="dataSource_mysql")
	public int count();
	 //列表，可分页
	@DataSource(value="dataSource_mysql")
	public List<Version> listByPage(Map<String,Object> parameter);
	 //模糊查询，可分页
	@DataSource(value="dataSource_mysql")
	public List<Version> search(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_mysql")
	public int searchCount(Map<String ,Object>parameter);
	//检测更新
	@DataSource(value="dataSource_mysql")
	public List<Version> versionDetection(Map<String ,Object>parameter);
}
