package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.Message2Mapper;
import com.wuhan_data.pojo.Message2;
import com.wuhan_data.service.Message2Service;

@Service
public class Message2ServiceImpl implements Message2Service {

	@Autowired
	Message2Mapper message2Mapper;
	@Override
	public int add(Message2 message2) {
		// TODO Auto-generated method stub
		return message2Mapper.add(message2);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		message2Mapper.delete(id);
	}

	@Override
	public Message2 get(int id) {
		// TODO Auto-generated method stub
		return message2Mapper.get(id);
	}

	@Override
	public int update(Message2 message2) {
		// TODO Auto-generated method stub
		return message2Mapper.update(message2);
	}

	@Override
	public List<Message2> list() {
		// TODO Auto-generated method stub
		return message2Mapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return message2Mapper.count();
	}

	@Override
	public List<Message2> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return message2Mapper.listByPage(parameter);
	}

	@Override
	public List<Message2> searchByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return message2Mapper.searchByContent(parameter);
	}

	@Override
	public int searchCountByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return message2Mapper.searchCountByContent(parameter);
	}

}
