package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;


import com.wuhan_data.pojo.Role;
import com.wuhan_data.tools.DataSource;

public interface RoleMapper {
//	 @DataSource(value="dataSource_mysql")
	public int add(Role role);
//	 @DataSource(value="dataSource_mysql")
	public void delete(int id);
//	 @DataSource(value="dataSource_mysql")
	public Role get(int id);
//	 @DataSource(value="dataSource_mysql")
	public int update(Role role);
//	 @DataSource(value="dataSource_mysql")
	public List<Role> list();
//	 @DataSource(value="dataSource_mysql")
	public int count();
	 //列表，可分页
//	@DataSource(value="dataSource_mysql")
	public List<Role> listByPage(Map<String,Object> parameter);
	 //模糊查询，可分页
//	@DataSource(value="dataSource_mysql")
	public List<Role> search(Map<String, Object> parameter);
	//模糊查询的数量
//	@DataSource(value="dataSource_mysql")
	public int searchCount(Map<String ,Object>parameter);

}
