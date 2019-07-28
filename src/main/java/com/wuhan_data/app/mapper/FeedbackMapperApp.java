package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.Feedback;
import com.wuhan_data.tools.DataSource;

public interface FeedbackMapperApp {
	@DataSource(value="dataSource_dm")
	public int add(Feedback feedback);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public Feedback get(int id);
	@DataSource(value="dataSource_dm")
	public int update(Feedback feedback);
	@DataSource(value="dataSource_dm")
	public List<Feedback> list();
	@DataSource(value="dataSource_dm")
	public int count();
	@DataSource(value="dataSource_dm")
	public List<Feedback> getByUid(int uid);


}
