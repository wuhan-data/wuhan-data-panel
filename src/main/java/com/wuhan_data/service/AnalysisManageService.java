package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisManage;
public interface AnalysisManageService {
	
	public int add(AnalysisManage analysisManage); 
	
	public int addTheme(AnalysisManage analysisManage); //添加一级栏目下的二极栏目
	
	public int editTheme(AnalysisManage analysisManage); //修改一级栏目下的二极栏目
	
	public int weight(String tname);//得到一级标题的权重
	
	public List<AnalysisManage> getOrderByTypename();//获得当前一级标题排序
	    
	public int reOrderByTypename(AnalysisManage analysisManage);//重置一级标题排序
    
    public void delete(int id); 
        
    public AnalysisManage get(int id); 
      
    public int update(AnalysisManage analysisManage);  
        
    public List<AnalysisManage> list();
    
    public List<AnalysisManage> parentList();
    
    public List<AnalysisManage> groupList(Map<String,Object> parameter);//通过一级标题分组查询
    
    public List<AnalysisManage> searchGroupList(Map<String,Object> parameter);//模糊分页查询
    
    public int count(); 
    
    public int countByGroup(String type_name); //分组查询数量
    
    public List<AnalysisManage> search(Map<String,Object> parameter); //模糊查询
    
    public int searchCount(Map<String,Object> parameter);//模糊查询数量
    
    public AnalysisManage getFirstWeight();//得到权重第一的type name；
    public int updateShow(AnalysisManage analysisManage);  //管理显示与否


}
