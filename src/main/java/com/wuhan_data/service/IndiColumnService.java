package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndiCorrelative;


public interface IndiColumnService {
	
public int add(IndiCorrelative indiCorrelative);
	
	public void delete(int id);
	
	public int update(IndiCorrelative indiCorrelative);
	
	public List<IndiCorrelative> getlist(Map map);//根据栏目id查找所有指标
	
	public int total(int cid);//查询数量

	public int updateShow(IndiCorrelative indiCorrelative);  //管理显示与否
}
