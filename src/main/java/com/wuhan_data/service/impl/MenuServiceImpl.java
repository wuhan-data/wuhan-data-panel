package com.wuhan_data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.MenuMapper;
import com.wuhan_data.pojo.Menu;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.tools.MenuList;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuMapper menuMapper;
	
	private String role_name;
	private List<Menu> listByRole;
	private List<Menu> level_oneList;
	
	//@Override
	
	  public List<MenuList> getMenu(String role_name) {
		  // TODO Auto-generated method stub 
	  this.role_name=role_name; 
	  Map<String,Object> map = new HashMap<String, Object>(); 
	  System.out.println("role_name"+role_name);
	  map.put("role_name=", role_name);
	  String [] arrayStrings=role_name.split(",");
	  listByRole=menuMapper.searchByRole(arrayStrings);
	  
	  level_oneList=menuMapper.searchByRoleGroupByOne(arrayStrings);
	  
	  List<MenuList> menuLists=new ArrayList<MenuList>();
	  for(int i=0;i<level_oneList.size();i++)
	  {
		  MenuList menuList=new MenuList();
		  String level_one=level_oneList.get(i).getLevel_one();
		  menuList.setLevel_one(level_one);
		  List<Menu> menus=new ArrayList<Menu>();
		  for(int j=0;j<listByRole.size();j++)
		  {
			  if (level_one.equals(listByRole.get(j).getLevel_one())  )
				  menus.add(listByRole.get(j));			  
		  }
		  menuList.setLevel_twoInOneList(menus);
		  menuLists.add(menuList);
	  }
	  return menuLists;
	  }
	 
	
	@Override
	public int add(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.add(menu);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		menuMapper.delete(id);
	}

	@Override
	public Menu get(int id) {
		// TODO Auto-generated method stub
		return menuMapper.get(id);
	}

	@Override
	public int update(Menu menu) {
		// TODO Auto-generated method stub
		return menuMapper.update(menu);
	}

	@Override
	public List<Menu> list() {
		// TODO Auto-generated method stub
		return menuMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return menuMapper.count();
	}

	@Override
	public List<Menu> searchByRoleGroupByOne(String[] parameter) {
		// TODO Auto-generated method stub
		return menuMapper.searchByRoleGroupByOne(parameter);
	}

	@Override
	public List<Menu> searchByRoleAndLevel_one(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return menuMapper.searchByRoleAndLevel_one(parameter);
	}

	@Override
	public List<Menu> searchByRole(String[] parameter) {
		// TODO Auto-generated method stub
		return menuMapper.searchByRole(parameter);
	}



}
