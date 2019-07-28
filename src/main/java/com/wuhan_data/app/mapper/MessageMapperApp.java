package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Message;
import com.wuhan_data.tools.DataSource;

public interface MessageMapperApp {
	@DataSource(value="dataSource_dm")
	public int add(Message message);
	@DataSource(value="dataSource_dm")
	public int addByRole(List<Message> message);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public Message get(int id);
	@DataSource(value="dataSource_dm")
	public int update(Message message);
	@DataSource(value="dataSource_dm")
	public List<Message> list();
	@DataSource(value="dataSource_dm")
	public int count();
	@DataSource(value="dataSource_dm")
	public List<Message> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	@DataSource(value="dataSource_dm")
	public List<Message> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_dm")
	public int searchCountByContent(Map<String, Object> parameter);
	//
	@DataSource(value="dataSource_dm")
	public List<Message> getByRid(int receiver_id);

}
