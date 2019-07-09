package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Admin;

public interface AdminService {

	public int add(Admin admin);
	
	public void delete(int id);
	
	public Admin get(int id) ;
	
	public Admin getByName(String username);
	
	public int update(Admin admin);
	
	public List<Admin> list();
	
	public int count();
	
	 // 列表，可分页
	public List<Admin> listByPage(Map<String,Object> parameter);
	//模糊查询，可分组
	public List<Admin> search(Map<String,Object> parameter);
	//模糊查询数量
	public int searchCount(Map<String,Object> parameter);
	//管理员登录
	public int adminLogin(Map<String,Object> parameter);
	
}
