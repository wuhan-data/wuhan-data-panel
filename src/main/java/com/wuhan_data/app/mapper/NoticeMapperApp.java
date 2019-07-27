package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Notice;
import com.wuhan_data.tools.DataSource;

public interface NoticeMapperApp {
	@DataSource(value="dataSource_dm")
	public int add(Notice notice);
	@DataSource(value="dataSource_dm")
	public int addByRole(List<Notice> notice);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public Notice get(int id);
	@DataSource(value="dataSource_dm")
	public int update(Notice notice);
	@DataSource(value="dataSource_dm")
	public List<Notice> list();
	@DataSource(value="dataSource_dm")
	public int count();
	@DataSource(value="dataSource_dm")
	public List<Notice> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	@DataSource(value="dataSource_dm")
	public List<Notice> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_dm")
	public int searchCountByContent(Map<String, Object> parameter);
	
	@DataSource(value="dataSource_dm")
	public List<Notice> getByRid(int receiver_id);
	

}
