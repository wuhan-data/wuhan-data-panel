package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Message;

public interface MessageService {

	
	public int add(Message message);
	
	public int addByRole(List<Message> messages);
	
	public void delete(int id);
	
	public Message get(int id);
	
	public int update(Message message);
	
	public List<Message> list();
	
	public int count();
	
	public List<Message> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	public List<Message> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	public int searchCountByContent(Map<String, Object> parameter);


}
