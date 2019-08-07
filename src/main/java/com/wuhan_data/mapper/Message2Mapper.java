package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;
import com.wuhan_data.pojo.Message2;
import com.wuhan_data.tools.DataSource;

public interface Message2Mapper {
	@DataSource(value="dataSource_mysql")
	public int add(Message2 message2);
	@DataSource(value="dataSource_mysql")
	public void delete(int id);
	@DataSource(value="dataSource_mysql")
	public Message2 get(int id);
	@DataSource(value="dataSource_mysql")
	public int update(Message2 message2);
	@DataSource(value="dataSource_mysql")
	public List<Message2> list();
	@DataSource(value="dataSource_mysql")
	public int count();
	@DataSource(value="dataSource_mysql")
	public List<Message2> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	@DataSource(value="dataSource_mysql")
	public List<Message2> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	@DataSource(value="dataSource_mysql")
	public int searchCountByContent(Map<String, Object> parameter);

}
