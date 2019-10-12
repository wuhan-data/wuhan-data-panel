package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisLabel;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.AnalysisType;
<<<<<<< HEAD
import com.wuhan_data.tools.DataSource;
=======
>>>>>>> 2eade37d4f17a8691fde8f5d77143b92436665c9



public interface AnalysisManageMapper {
	@DataSource(value="dataSource_dm")
	    public int add(AnalysisManage analysisManage); 
<<<<<<< HEAD
	@DataSource(value="dataSource_dm")
	    public int addLabel(AnalysisLabel analysisLabel); //添加一级栏目下的二极分类
	@DataSource(value="dataSource_dm")
	    public int editLabel(AnalysisLabel analysisLabel); //修改一级栏目下的二极分类
	@DataSource(value="dataSource_dm")
	    public int getMaxWeight(int type_id);//得到一级栏目下二级分类的最大权重+1 新添加二级分类时使用
	@DataSource(value="dataSource_dm")
	    public int getLabelId(Map<String,Object> map); //更新二级分类权重时得到相应分类的id
	@DataSource(value="dataSource_dm")
	    public int updateWeight(Map<String,Object> map);//更新二级分类权重
	@DataSource(value="dataSource_dm")
=======
	    
	    public int addLabel(AnalysisLabel analysisLabel); //添加一级栏目下的二极分类
	    
	    public int editLabel(AnalysisLabel analysisLabel); //修改一级栏目下的二极分类
	    
	    public int getMaxWeight(int type_id);//得到一级栏目下二级分类的最大权重+1 新添加二级分类时使用
	    
	    public int getLabelId(Map<String,Object> map); //更新二级分类权重时得到相应分类的id
	    
	    public int updateWeight(Map<String,Object> map);//更新二级分类权重
	    
>>>>>>> 2eade37d4f17a8691fde8f5d77143b92436665c9
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
<<<<<<< HEAD
	@DataSource(value="dataSource_dm")
	    public List<AnalysisType> parentList();//查询一级标题
	@DataSource(value="dataSource_dm")
	    public List<AnalysisLabel> groupList(Map<String,Object> parameter);//通过一级标题分组查询
	@DataSource(value="dataSource_dm")
=======
	    
	    public List<AnalysisType> parentList();//查询一级标题
	    
	    public List<AnalysisLabel> groupList(Map<String,Object> parameter);//通过一级标题分组查询
	    
>>>>>>> 2eade37d4f17a8691fde8f5d77143b92436665c9
	    public List<AnalysisManage> searchGroupList(Map<String,Object> parameter);//模糊分页查询
//	    
//	    public List<AnalysisManage> groupList(String type_name);
	@DataSource(value="dataSource_dm")
	    public int count(); 
<<<<<<< HEAD
	@DataSource(value="dataSource_dm")
	    public List<AnalysisType> getOrderByTypename();//获得当前一级标题排序
	@DataSource(value="dataSource_dm")
	    public int reOrderByTypename(AnalysisType analysisType);//重置一级标题排序
	@DataSource(value="dataSource_dm")
	    public int searchCount(Map<String,Object> parameter);//模糊查询数量
	@DataSource(value="dataSource_dm")
	    public int countByGroup(int type_id); //分组查询数量
	@DataSource(value="dataSource_dm")
	    public AnalysisType getFirstWeight();//得到权重第一的type name；
	@DataSource(value="dataSource_dm")
	    public int updateShow(AnalysisLabel analysisLabel);  //管理显示与否
	@DataSource(value="dataSource_dm")
=======
	    
	    public List<AnalysisType> getOrderByTypename();//获得当前一级标题排序
	    
	    public int reOrderByTypename(AnalysisType analysisType);//重置一级标题排序
	    
	    public int searchCount(Map<String,Object> parameter);//模糊查询数量
	    
	    public int countByGroup(int type_id); //分组查询数量
	    
	    public AnalysisType getFirstWeight();//得到权重第一的type name；
	    
	    public int updateShow(AnalysisLabel analysisLabel);  //管理显示与否
	    
>>>>>>> 2eade37d4f17a8691fde8f5d77143b92436665c9
	    public int getTypeId(String tname);

}
