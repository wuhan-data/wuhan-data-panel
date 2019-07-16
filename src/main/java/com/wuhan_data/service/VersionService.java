package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Version;

public interface VersionService {

	public int add(Version version);
	
	public void delete(int id);
	
	public Version get(int id);
	
	public int update(Version version);
	
	public List<Version> List();
	
	public int count();

	 // 列表，可分页
	public List<Version> listByPage(Map<String,Object> parameter);
	//模糊查询，可分组
	public List<Version> search(Map<String,Object> parameter);
	//模糊查询数量
	public int searchCount(Map<String,Object> parameter);
}
