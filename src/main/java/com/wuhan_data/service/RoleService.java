package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.tools.ThemeList;

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
	//获得经济分析的列表power_1
	public List<ThemeList> getThemeLists();
	//获得专题的列表power_2
	public List<IndexSpecial> getIndexSpecials();
	//获得搜索指标的列表power_3
	public List<IndexManage> getIndexManages();
	public List<String> getIndexManages2();
	//通过idList获取nameList
	public String getNameList(String idList);
		
}
