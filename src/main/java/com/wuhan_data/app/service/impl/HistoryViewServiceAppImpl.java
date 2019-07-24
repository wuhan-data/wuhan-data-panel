package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.HistoryViewMapperApp;
import com.wuhan_data.app.service.HistoryViewServiceApp;
import com.wuhan_data.mapper.HistoryViewMapper;
import com.wuhan_data.pojo.HistoryView;
@Service
public class HistoryViewServiceAppImpl implements HistoryViewServiceApp {

	@Autowired
	HistoryViewMapperApp historyViewMapper;
	@Override
	public int add(HistoryView historyView) {
		// TODO Auto-generated method stub
		return historyViewMapper.add(historyView);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		historyViewMapper.delete(id);
		
	}

	@Override
	public HistoryView get(int id) {
		// TODO Auto-generated method stub
		return historyViewMapper.get(id);
	}

	@Override
	public int update(HistoryView historyView) {
		// TODO Auto-generated method stub
		return historyViewMapper.update(historyView);
	}

	@Override
	public List<HistoryView> list() {
		// TODO Auto-generated method stub
		return historyViewMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return historyViewMapper.count();
	}

	@Override
	public List<HistoryView> getByUid(int uid) {
		// TODO Auto-generated method stub
		return historyViewMapper.getByUid(uid);
	}

}
