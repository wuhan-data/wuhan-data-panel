package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.DataSource;
public interface DepartmentMapper {
//	 @DataSource(value="dataSource_mysql")
	public int add(Department department);
//	 @DataSource(value="dataSource_mysql")
	public void delete(int id);
//	 @DataSource(value="dataSource_mysql")
	public Department get(int id);
//	 @DataSource(value="dataSource_mysql")
	public int update(Department department);
//	 @DataSource(value="dataSource_mysql")
	public List<Department> list();
//	 @DataSource(value="dataSource_mysql")
	public int count() ;
	 //列表，可分页
//	 @DataSource(value="dataSource_mysql")
	public List<Department> listByPage(Map<String,Object> parameter);
	 //模糊查询，可分页
//	@DataSource(value="dataSource_mysql")
	public List<Department> search(Map<String, Object> parameter);
	//模糊查询的数量
//	@DataSource(value="dataSource_mysql")
	public int searchCount(Map<String ,Object>parameter);

}
