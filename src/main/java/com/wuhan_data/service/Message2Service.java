package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Message2;

public interface Message2Service {
	public int add(Message2 message2);
	public void delete(int id);
	public Message2 get(int id);
	public int update(Message2 message2);
	public List<Message2> list();
	public int count();
	public List<Message2> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	public List<Message2> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	public int searchCountByContent(Map<String, Object> parameter);

}
