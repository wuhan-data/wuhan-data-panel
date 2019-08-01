package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.Message2;

public interface Message2ServiceApp {
	public List<Message2> getByRid(int receiver_id);
	public List<Message2> getByLabel(String label);
}
