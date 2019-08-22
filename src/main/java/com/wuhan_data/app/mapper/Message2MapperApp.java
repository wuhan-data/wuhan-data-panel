package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Message2;
import com.wuhan_data.tools.DataSource;

public interface Message2MapperApp {
	@DataSource(value="dataSource_dm")
	public int add(Message2 message2);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public Message2 get(int id);
	@DataSource(value="dataSource_dm")
	public int update(Message2 message2);
	@DataSource(value="dataSource_dm")
	public List<Message2> list();
	@DataSource(value="dataSource_dm")
	public int count();
	@DataSource(value="dataSource_dm")
	public List<Message2> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	@DataSource(value="dataSource_dm")
	public List<Message2> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_dm")
	public int searchCountByContent(Map<String, Object> parameter);
	//
	@DataSource(value="dataSource_dm")
	public List<Message2> getByRid(int receiver_id);
	//
	@DataSource(value="dataSource_dm")
	public List<Message2> getByLabel(String label);

}
