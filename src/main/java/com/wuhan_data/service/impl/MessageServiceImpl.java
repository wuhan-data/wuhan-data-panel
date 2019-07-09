package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.MessageMapper;
import com.wuhan_data.pojo.Message;
import com.wuhan_data.service.MessageService;
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageMapper messageMapper;
	@Override
	public int add(Message message) {
		// TODO Auto-generated method stub
		return messageMapper.add(message);
	}

	@Override
	public int addByRole(List<Message> message) {
		// TODO Auto-generated method stub
		return messageMapper.addByRole(message);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		messageMapper.delete(id);

	}

	@Override
	public Message get(int id) {
		// TODO Auto-generated method stub
		return messageMapper.get(id);
	}

	@Override
	public int update(Message message) {
		// TODO Auto-generated method stub
		return messageMapper.update(message);
	}

	@Override
	public List<Message> list() {
		// TODO Auto-generated method stub
		return messageMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return messageMapper.count();
	}

	@Override
	public List<Message> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return messageMapper.listByPage(parameter);
	}

	@Override
	public List<Message> searchByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return messageMapper.searchByContent(parameter);
	}

	@Override
	public int searchCountByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return messageMapper.searchCountByContent(parameter);
	}

}
