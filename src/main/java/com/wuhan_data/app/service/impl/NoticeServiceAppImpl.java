package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.NoticeMapperApp;
import com.wuhan_data.app.service.NoticeServiceApp;
import com.wuhan_data.pojo.Notice;

@Service
public class NoticeServiceAppImpl implements NoticeServiceApp {

	@Autowired
	NoticeMapperApp noticeMapperApp;
	@Override
	public List<Notice> getByRid(int receiver_id) {
		// TODO Auto-generated method stub
		return noticeMapperApp.getByRid(receiver_id);
	}

}
