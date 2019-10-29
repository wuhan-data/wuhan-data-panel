package com.wuhan_data.mapper;

import com.wuhan_data.tools.DataSource;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.UserOpLog;
public interface UserOpLogMapper {
	@DataSource(value="dataSource_dm")
	public int add(UserOpLog userOpLog);
	@DataSource(value="dataSource_dm")
	public int delete(int id);
	@DataSource(value="dataSource_dm")
	public UserOpLog get(int id);
	@DataSource(value="dataSource_dm")
	public int update(UserOpLog userOpLog);
	@DataSource(value="dataSource_dm")
	public List<UserOpLog> list();
	@DataSource(value="dataSource_dm")
	public int count();
	@DataSource(value="dataSource_dm")
	public List<UserOpLog> listByPage(Map<String, Object> parameter);
	//模糊查询，可分页
	@DataSource(value="dataSource_dm")
	public List<UserOpLog> search(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_dm")
	public int searchCount(Map<String, Object>parameter);
	

}
