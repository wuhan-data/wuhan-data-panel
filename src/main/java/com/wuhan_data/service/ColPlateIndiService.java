package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndexManage;

public interface ColPlateIndiService {
public int add(ColPlateIndi colPlateIndi);
	
	public void delete(int id);
	
	public int update(ColPlateIndi colPlateIndi);
	
	public List<ColPlateIndi> getlist(Map map);//根据栏目id查找所有指标
	
	public int total(int pid);//查询数量
	
	public int updateShow(ColPlateIndi colPlateIndi);  //管理显示与否

	/**
	 * 修改指标权值
	 * @param plateId 板块ID
	 * @param ids 指标ID集合
	 * @return 修改指标条数
	 */
	public int updateWeight(int plateId, String[] ids);
	
	public List<IndexManage> getAllIndi();//分组查询index_manage 得到indi_code与indi_name 用于前端select组件


	public IndexManage getIndiInfo(String indi_code);//根据indi_code得到指标信息 用于添加
	
	public String getPname(int pid);//根据pid查询pname 用于添加指标
	
	public List<ColPlateIndi> searchIndi(String content);//用于添加指标时对指标的搜索
	
	public ColPlateIndi getIdAndNew_name(String indi_old_name);
}
