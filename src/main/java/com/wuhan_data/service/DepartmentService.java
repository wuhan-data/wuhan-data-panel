package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.User;

public interface DepartmentService {
	
	public int add(Department department);
	
	public void delete(int id);
	
	public Department get(int id) ;
	
	public int update(Department department);
	
	public List<Department> list();
	
	public int count();
	
	 // 列表，可分页
	public List<Department> listByPage(Map<String,Object> parameter);
	//模糊查询，可分组
	public List<Department> search(Map<String,Object> parameter);
	//模糊查询数量
	public int searchCount(Map<String,Object> parameter);
	


}
