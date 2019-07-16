package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.Message;

public interface MessageServiceApp {
	
	public List<Message> getByRid(int receiver_id);

}
