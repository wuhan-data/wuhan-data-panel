package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.Message2MapperApp;
import com.wuhan_data.app.service.Message2ServiceApp;
import com.wuhan_data.pojo.Message2;
@Service
public class Message2ServiceAppImpl implements Message2ServiceApp {

	@Autowired
	Message2MapperApp message2MapperApp;
	@Override
	public List<Message2> getByRid(int receiver_id) {
		// TODO Auto-generated method stub
		return message2MapperApp.getByRid(receiver_id);
	}
	@Override
	public List<Message2> getByLabel(String label) {
		// TODO Auto-generated method stub
		return message2MapperApp.getByLabel(label);
	}

}
