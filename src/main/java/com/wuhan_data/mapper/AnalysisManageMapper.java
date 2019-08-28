package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.tools.DataSource;



public interface AnalysisManageMapper {
		@DataSource(value="dataSource_dm")
	    public int add(AnalysisManage analysisManage); 
		@DataSource(value="dataSource_dm")
	    public int addTheme(AnalysisManage analysisManage); //添加一级栏目下的二极栏目
		@DataSource(value="dataSource_dm")
	    public int editTheme(AnalysisManage analysisManage); //添加一级栏目下的二极栏目
		@DataSource(value="dataSource_dm")
	    public int weight(String tname);//得到一级标题的权重
		@DataSource(value="dataSource_dm")
	    public void delete(int id); 
		@DataSource(value="dataSource_dm")
	    public AnalysisManage get(int id); 
		@DataSource(value="dataSource_dm")
	    public int update(AnalysisManage analysisManage);  
		@DataSource(value="dataSource_dm") 
	    public List<AnalysisManage> list();
		@DataSource(value="dataSource_dm")
	    public List<AnalysisManage> search(Map<String,Object> parameter); //模糊查询
		@DataSource(value="dataSource_dm")
	    public List<AnalysisManage> parentList();//查询一级标题
		@DataSource(value="dataSource_dm")
	    public List<AnalysisManage> groupList(Map<String,Object> parameter);//通过一级标题分组查询
		@DataSource(value="dataSource_dm")
	    public List<AnalysisManage> searchGroupList(Map<String,Object> parameter);//模糊分页查询
//	    
//	    public List<AnalysisManage> groupList(String type_name);
		@DataSource(value="dataSource_dm")
	    public int count(); 
		@DataSource(value="dataSource_dm")
	    public List<AnalysisManage> getOrderByTypename();//获得当前一级标题排序
		@DataSource(value="dataSource_dm")
	    public int reOrderByTypename(AnalysisManage analysisManage);//重置一级标题排序
		@DataSource(value="dataSource_dm")
	    public int searchCount(Map<String,Object> parameter);//模糊查询数量
		@DataSource(value="dataSource_dm")
	    public int countByGroup(String type_name); //分组查询数量
		@DataSource(value="dataSource_dm")
	    public AnalysisManage getFirstWeight();//得到权重第一的type name；
		@DataSource(value="dataSource_dm")
	    public int updateShow(AnalysisManage analysisManage);  //管理显示与否

}
