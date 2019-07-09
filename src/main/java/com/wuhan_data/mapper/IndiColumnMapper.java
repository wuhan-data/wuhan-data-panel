package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.Page;


public interface IndiColumnMapper {
	
	public int add(IndiCorrelative indiCorrelative);
	
	public void delete(int id);
	
	public int update(IndiCorrelative indiCorrelative);
	
	public List<IndiCorrelative> getlist(Map map);//根据栏目id查找所有指标
	
	public int total(int cid);//查询数量
	
	public int updateShow(IndiCorrelative indiCorrelative);  //管理显示与否

}
