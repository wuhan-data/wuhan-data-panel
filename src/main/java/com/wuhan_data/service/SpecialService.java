package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.SpecialDetail;


public interface SpecialService {
	
    public int add(IndexSpecial indexSpecial); //添加一个专题
	
   	public void delete(int id); //删除一个专题
   	
   	public int update(IndexSpecial indexSpecial); //更新专题名称
   	
   	public List<IndexSpecial> getlist(Map map);//分页查找所有专题
   	
   	public int total();//查询专题数量
   	
   	public int updateShow(IndexSpecial indexSpecial);  //管理专题显示与否
   	
   	public String getSname(int id);//得到专题名称
   	
   	public int reOrderByTitle(IndexSpecial indexSpecial);//重置一级标题排序
   	
   	public int updateTitle(IndexSpecial indexSpecial); //更新专题名称
}
