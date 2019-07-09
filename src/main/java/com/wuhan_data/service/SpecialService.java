package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.SpecialDetail;


public interface SpecialService {
	
    public int add(SpecialDetail specialDetail); 
	
	public void delete(int id);
	
	public int update(SpecialDetail specialDetail);
	
	public List<SpecialDetail> getlist(Map map);//根据栏目id查找所有栏目
	
	public int total();//查询数量
	
	public int updateShow(SpecialDetail specialDetail);  //管理显示与否
	
	public int maxSpecialId();//查询最大special 用于添加
	
	
public List<SpecialDetail> getIndiList(Map map);//查找某专题下的所有指标（分页）

public String getSname(int special_id);
	
	public int deleteIndi(int id); //删除专题下的某一个指标
	
	public int addIndi(SpecialDetail specialDetail); //在专题下添加一个指标
	
	public int updateIndi(SpecialDetail specialDetail); //在专题下更改一个指标信息
	
	public int updateIndiShow(SpecialDetail specialDetail); //在专题下更改一个指标信息显示信息
	
	public int totalIndi(int special_id);//查询专题数量
}
