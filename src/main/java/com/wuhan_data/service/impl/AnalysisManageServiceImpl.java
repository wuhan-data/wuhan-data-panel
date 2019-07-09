package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.AnalysisManageMapper;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.service.AnalysisManageService;

@Service
public class AnalysisManageServiceImpl implements AnalysisManageService{
	
	@Autowired
	AnalysisManageMapper analysisManageMapper;

	@Override
	public int add(AnalysisManage analysisManage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		analysisManageMapper.delete(id);
		
	}

	@Override
	public AnalysisManage get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(AnalysisManage analysisManage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AnalysisManage> list() {
		// TODO Auto-generated method stub
		return analysisManageMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AnalysisManage> parentList() {
		// TODO Auto-generated method stub
		return analysisManageMapper.parentList();
	}

	@Override
	public List<AnalysisManage> groupList(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return analysisManageMapper.groupList(parameter);
	}

	@Override
	public int countByGroup(String type_name) {
		// TODO Auto-generated method stub
		return analysisManageMapper.countByGroup(type_name);
	}

	@Override
	public List<AnalysisManage> search(Map<String,Object> parameter) {
		// TODO Auto-generated method stub
		return analysisManageMapper.search(parameter);
	}

	@Override
	 public int searchCount(Map<String,Object> parameter) {//模糊查询数量
		// TODO Auto-generated method stub
		return analysisManageMapper.searchCount(parameter);
	}

	@Override
	public List<AnalysisManage> searchGroupList(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return analysisManageMapper.searchGroupList(parameter);
	}

	@Override
	public int addTheme(AnalysisManage analysisManage) {
		// TODO Auto-generated method stub
		return analysisManageMapper.addTheme(analysisManage);
	}

	@Override
	public int editTheme(AnalysisManage analysisManage) {
		// TODO Auto-generated method stub
		return analysisManageMapper.editTheme(analysisManage);
	}

	@Override
	public int weight(String tname) {
		// TODO Auto-generated method stub
		return analysisManageMapper.weight(tname);
	}

	@Override
	public List<AnalysisManage> getOrderByTypename() {
		// TODO Auto-generated method stub
		return analysisManageMapper.getOrderByTypename();
	}

	@Override
	public int reOrderByTypename(AnalysisManage analysisManage) {
		// TODO Auto-generated method stub
		return analysisManageMapper.reOrderByTypename(analysisManage);
	}

	@Override
	public AnalysisManage getFirstWeight() {
		// TODO Auto-generated method stub
		return analysisManageMapper.getFirstWeight();
	}

	@Override
	public int updateShow(AnalysisManage analysisManage) {
		// TODO Auto-generated method stub
		return analysisManageMapper.updateShow(analysisManage);
	}


}
