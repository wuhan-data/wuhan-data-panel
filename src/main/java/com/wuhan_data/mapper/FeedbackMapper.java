package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Feedback;
import com.wuhan_data.tools.DataSource;

public interface FeedbackMapper {
	@DataSource(value="dataSource_mysql")
	public int add(Feedback feedback);
	@DataSource(value="dataSource_mysql")
	public void delete(int id);
	@DataSource(value="dataSource_mysql")
	public Feedback get(int id);
	@DataSource(value="dataSource_mysql")
	public int update(Feedback feedback);
	@DataSource(value="dataSource_mysql")
	public List<Feedback> list();
	@DataSource(value="dataSource_mysql")
	public int count();
	//列表，可分页
	@DataSource(value="dataSource_mysql")
	public List<Feedback> listByPage(Map<String,Object> parameter);
	//模糊查询，可分页
	@DataSource(value="dataSource_mysql")
	public List<Feedback> search(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_mysql")
	public int searchCount(Map<String ,Object>parameter);


}
