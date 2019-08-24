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
		//获得所有菜单
		public List<MenuList> getAllMenu();
		
		public List<MenuList> getMenu(String role_name);
		 //精确查询
		public List<Menu> searchByRole(String[] parameter);
		
		public List<Menu> searchByRoleGroupByOne(String[] parameter);
		
		public List<Menu> searchByRoleAndLevel_one(Map<String, Object> parameter);
		//获取第一级目录
		public List<Menu> getLevelOne();
		//获取第一级目录对应的第二级目录
		public List<Menu> getLevelTwoByLevelOne(Map<String, Object> parameter);
}
