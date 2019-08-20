package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Role;

public interface RoleService {
	
	public int add(Role role);
	
	public void delete(int id);
	
	public Role get(int id);
	
	public Role getByName(String name);
	
	public List<Role> getByCode(Map<String,Object> parameter);
	
	public int update(Role role);
	
	public List<Role> List();
	
	public int count();

	 // 列表，可分页
	public List<Role> listByPage(Map<String,Object> parameter);
	//模糊查询，可分组
	public List<Role> search(Map<String,Object> parameter);
	//模糊查询数量
	public int searchCount(Map<String,Object> parameter);
		
}
