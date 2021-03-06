package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.IndiCorrelative;

public interface ColPlateService {
	
	public int add(ColPlate colPlate);
	
	public void delete(int id);
	
	public int update(ColPlate colPlate);
	
	public List<ColPlate> getlist(Map map);//根据栏目id查找所有指标
	
	public int total(int cid);//查询数量
	
	public int updateShow(ColPlate colPlate);  //管理显示与否

}
