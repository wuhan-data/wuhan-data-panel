package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.tools.DataSource;

public interface SpecialMapper {
	@DataSource(value="dataSource_dm")
    public int add(IndexSpecial indexSpecial); //添加一个专题
	@DataSource(value="dataSource_dm")
	public void delete(int id); //删除一个专题
	@DataSource(value="dataSource_dm")
	public int update(IndexSpecial indexSpecial); //更新专题名称
	@DataSource(value="dataSource_dm")
	public int updateTitle(IndexSpecial indexSpecial); //更新专题名称
	@DataSource(value="dataSource_dm")
	public List<IndexSpecial> getlist(Map<String,Object> map);//分页查找所有专题
	@DataSource(value="dataSource_dm")
	public int total();//查询专题数量
	@DataSource(value="dataSource_dm")
	public int updateShow(IndexSpecial indexSpecial);  //管理专题显示与否
	@DataSource(value="dataSource_dm")
	public int updateShowType(IndexSpecial indexSpecial);  //管理专题显示方式
	@DataSource(value="dataSource_dm")
	public String getSname(int id);//得到专题名称
	@DataSource(value="dataSource_dm")
	public int reOrderByTitle(IndexSpecial indexSpecial);//重置一级标题排序
	@DataSource(value="dataSource_dm")
	public int getSpecialId(Map<String,Object> map);
	@DataSource(value="dataSource_dm")
	public int updateWeight(Map<String,Object> map);
	@DataSource(value="dataSource_dm")
	public int getMaxWeight();
	
	
	


}
