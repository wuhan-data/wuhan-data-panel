package com.wuhan_data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.tool.PrettyPrinter;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.AnalysisManageMapper;
import com.wuhan_data.mapper.AnalysisSecondMapper;
import com.wuhan_data.app.mapper.IndiSearchMapper;
import com.wuhan_data.mapper.IndexManageMapper;
import com.wuhan_data.mapper.IndexSpecialMapper;
import com.wuhan_data.mapper.RoleMapper;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.indi_TF;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.tools.ThemeList;
import com.wuhan_data.pojo.AnalysisLabel;
import com.wuhan_data.pojo.AnalysisTheme;
import com.wuhan_data.pojo.AnalysisType;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndexSpecial;

@Service
public class RoleServicelmpl implements RoleService {
	
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	IndiSearchMapper indiSearchMapper;
	
	@Autowired
	AnalysisManageMapper analysisManageMapper;
	@Autowired
	AnalysisSecondMapper analysisSecondMapper;
	
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
		System.out.println("主题");
		//获得了一级栏目
		List<AnalysisType> allAnalysisTypes=analysisManageMapper.parentList();//这是一级标题
		
		for (int i=0;i<allAnalysisTypes.size();i++)
		{	
			ThemeList aList=new ThemeList();
			AnalysisType analysisType=allAnalysisTypes.get(allAnalysisTypes.size()-i-1);
			//System.out.println("一级栏目"+analysisType.getType_name());
			aList.setLevel_one(analysisType.getType_name());//设置一级标题
			Map map=new HashMap();
			map.put("type_id",analysisType.getType_id());
			List<AnalysisLabel> analysisLabels=analysisManageMapper.groupList(map);//得到了二级标题
			//System.out.println("二级标题"+analysisLabels.get(1).getLabel_name());
			List<AnalysisTheme> aaList=new ArrayList<AnalysisTheme>();
			for (int j=0;j<analysisLabels.size();j++)
			{
				AnalysisLabel analysisLabel=analysisLabels.get(j);
				Map map2=new HashMap();
				map2.put("label_id", analysisLabel.getLabel_id());
				List<AnalysisTheme> analysisThemes=analysisSecondMapper.getlist(map2);//获得三级标题
				//System.out.println("三级标题"+analysisThemes.get(1).getTheme_name());
				for(int k=0;k<analysisThemes.size();k++)
				{
					aaList.add(analysisThemes.get(k));
				}
			}
			aList.setLevel_twoInOneList(aaList);//设置三级标题
			resultList.add(aList);
		}
		return resultList;
//		//
//		for (int i=0;i< allAnalysisThemes.size();i++)
//		{
//			ThemeList aList=new ThemeList();
//			AnalysisTheme bTheme=allAnalysisThemes.get(i);
//			String level_one=bTheme.getListName();//一级栏目名称
//			aList.setLevel_one(level_one);
//			List<AnalysisTheme> level_twoInOneList=analysisMapper.getAnalysisSubList(bTheme.getListId());
//			aList.setLevel_twoInOneList(level_twoInOneList);
//			resultList.add(aList);
//		}
	}

	@Override
	public java.util.List<IndexSpecial> getIndexSpecials() {
		// TODO Auto-generated method stub
		
		return indexSpecialMapper.list();
	}

	@Override
	public java.util.List<IndexManage> getIndexManages() {
		// TODO Auto-generated method stub
		return indiSearchMapper.searchIndiHPower();
	}

	@Override
	public java.util.List<IndexManage> getIndexManages2() {
		// TODO Auto-generated method stub
		//return indiSearchMapper.searchIndiHPower("%");
		return indiSearchMapper.searchIndiHPower();
	}

	@Override
	public String getNameList(String idList) {
		// TODO Auto-generated method stub
		String[] ids=idList.split("\\|");
		String[] names=new String[ids.length];
		for(int i=0;i<ids.length;i++)
		{
			Role role=roleMapper.get(Integer.valueOf(ids[i]));
			names[i]=role.getRole_name();
		}
		String result=StringUtils.join(names,"|");
		return result;
	}

	@Override
	public Map<String, java.util.List<String>> getDefaultRolePower() {
		// TODO Auto-generated method stub
		Map<String, List<String>> data = new HashMap<String, List<String>>();
		Role role=roleMapper.get(2);
		List<String> power_1String=new ArrayList<String>();
  		List<String> power_2String=new ArrayList<String>();
  		List<String> power_3String=new ArrayList<String>();
  		List<String> power_4String=new ArrayList<String>();
  		power_1String.add(role.getRole_power_1());
		power_2String.add(role.getRole_power_2());
		power_3String.add(role.getRole_power_3());	
		power_4String.add(role.getRole_power_4());	
		data.put("powerThemes", power_1String);
  		data.put("powerIndexSpecials", power_2String);
  		data.put("powerIndexManages", power_3String);
  		data.put("powerIndexManages2", power_4String);
		return data;	
	}




}
