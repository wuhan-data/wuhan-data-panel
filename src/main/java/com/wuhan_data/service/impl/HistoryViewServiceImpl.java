package com.wuhan_data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.HistoryViewMapper;
import com.wuhan_data.pojo.HistoryView;
import com.wuhan_data.service.HistoryViewService;

@Service
public class HistoryViewServiceImpl implements HistoryViewService{
	
	@Autowired
	HistoryViewMapper historyViewMapper;

	@Override
	public int add(HistoryView historyView) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HistoryView get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(HistoryView historyView) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<HistoryView> list() {
		// TODO Auto-generated method stub
		return historyViewMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
