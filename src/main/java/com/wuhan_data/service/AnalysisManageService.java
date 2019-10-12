package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisLabel;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.AnalysisType;
public interface AnalysisManageService {
	
	public int add(AnalysisManage analysisManage); 
	
	public int addLabel(AnalysisLabel analysisLabel); //添加一级栏目下的二极分类
	
	public int editLabel(AnalysisLabel analysisLabel); //修改一级栏目下的二极分类
	
	public int getMaxWeight(int type_id);//得到一级栏目下二级分类的最大权重+1 新添加二级分类时使用
	
	public int getLabelId(Map<String,Object> map); //更新二级分类权重时得到相应分类的id
    
    public int updateWeight(Map<String,Object> map);//更新二级分类权重
	
	public int weight(String tname);//得到一级标题的权重
	
	public List<AnalysisType> getOrderByTypename();//获得当前一级标题排序
	    
	public int reOrderByTypename(AnalysisType analysisType);//重置一级标题排序
    
    public void delete(int id); 
        
    public AnalysisManage get(int id); 
      
    public int update(AnalysisManage analysisManage);  
        
    public List<AnalysisManage> list();
    
    public List<AnalysisType> parentList();
    
    public List<AnalysisLabel> groupList(Map<String,Object> parameter);//通过一级标题分组查询
    
    public List<AnalysisManage> searchGroupList(Map<String,Object> parameter);//模糊分页查询
    
    public int count(); 
    
    public int countByGroup(int type_id); //分组查询数量
    
    public List<AnalysisManage> search(Map<String,Object> parameter); //模糊查询
    
    public int searchCount(Map<String,Object> parameter);//模糊查询数量
    
    public AnalysisType getFirstWeight();//得到权重第一的type name；
    public int updateShow(AnalysisLabel analysisLabel);  //管理显示与否
    
    public int getTypeId(String tname);


}
