package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.Notice;

public interface NoticeServiceApp {
	public List<Notice> getByRid(int receiver_id);

}
