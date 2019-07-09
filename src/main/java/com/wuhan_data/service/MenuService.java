package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Menu;
import com.wuhan_data.tools.MenuList;

public interface MenuService {
	
		public int add(Menu menu);
	
		public void delete(int id);
		 
		public Menu get(int id);
		
		public int update(Menu menu);
		
		public List<Menu> list();
		 
		public int count() ;
		
		public List<MenuList> getMenu(String role_name);
		 //精确查询
		public List<Menu> searchByRole(Map<String, Object> parameter);
		
		public List<Menu> searchByRoleGroupByOne(Map<String, Object> parameter);
		
		public List<Menu> searchByRoleAndLevel_one(Map<String, Object> parameter);
}
