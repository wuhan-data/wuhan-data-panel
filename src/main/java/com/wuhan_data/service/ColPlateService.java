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
	
	public int getAddPid(int cid); //得到需要添加的板块的id值
	
	public String getAddCname(int cid); //得到需要添加的板块所属栏目的名称
	
	public int updateWeight(Map map); //更新权重
	
	public int getPid(Map map); //得到pid

}
