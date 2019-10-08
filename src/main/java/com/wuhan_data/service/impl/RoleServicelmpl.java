package com.wuhan_data.service.impl;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import org.apache.xmlbeans.impl.tool.PrettyPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.AnalysisMapper;
import com.wuhan_data.app.service.AnalysisService;
import com.wuhan_data.mapper.IndexManageMapper;
import com.wuhan_data.mapper.IndexSpecialMapper;
import com.wuhan_data.mapper.RoleMapper;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.indi_TF;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.tools.ThemeList;
import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndexSpecial;

@Service
public class RoleServicelmpl implements RoleService {
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	AnalysisMapper analysisMapper;
	
	@Autowired
	IndexSpecialMapper indexSpecialMapper;
	
	@Autowired
	IndexManageMapper indexManageMapper;
	
	@Override
	public int add(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.add(role);
	}
	
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		roleMapper.delete(id);
		
	}

	@Override
	public Role get(int id) {
		// TODO Auto-generated method stub
		return roleMapper.get(id);
	}
	@Override
	public Role getByName(String name) {
		return roleMapper.getByName(name);
	}

	@Override
	public int update(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.update(role);
	}

	@Override
	public java.util.List<Role> List() {
		// TODO Auto-generated method stub
		return roleMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return roleMapper.count();
	}

	@Override
	public java.util.List<Role> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.listByPage(parameter);
	}

	@Override
	public java.util.List<Role> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.searchCount(parameter);
	}

	@Override
	public java.util.List<Role> getByCode(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.getByCode(parameter);
	}

	@Override
	public java.util.List<ThemeList> getThemeLists() {
		// TODO Auto-generated method stub
		
		//要返回的结果
		List<ThemeList> resultList=new ArrayList<ThemeList>();
		//获得了一级栏目
		List<AnalysisTheme> allAnalysisThemes=analysisMapper.getAnalysisList();
		//
		for (int i=0;i< allAnalysisThemes.size();i++)
		{
			ThemeList aList=new ThemeList();
			AnalysisTheme bTheme=allAnalysisThemes.get(i);
			String level_one=bTheme.getListName();//一级栏目名称
			aList.setLevel_one(level_one);
			List<AnalysisTheme> level_twoInOneList=analysisMapper.getAnalysisSubList(bTheme.getListId());
			aList.setLevel_twoInOneList(level_twoInOneList);
			resultList.add(aList);
		}
		
		return resultList;
	}

	@Override
	public java.util.List<IndexSpecial> getIndexSpecials() {
		// TODO Auto-generated method stub
		
		return indexSpecialMapper.list();
	}

	@Override
	public java.util.List<IndexManage> getIndexManages() {
		// TODO Auto-generated method stub
		return indexManageMapper.list1();
	}




}
