package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.MessageMapperApp;
import com.wuhan_data.app.service.MessageServiceApp;
import com.wuhan_data.mapper.MessageMapper;
import com.wuhan_data.pojo.Message;

@Service
public class MessageServiceAppImpl implements MessageServiceApp {

	@Autowired
	MessageMapperApp messageMapperApp;
	@Override
	public List<Message> getByRid(int receiver_id) {
		// TODO Auto-generated method stub
		return messageMapperApp.getByRid(receiver_id);
	}

}
