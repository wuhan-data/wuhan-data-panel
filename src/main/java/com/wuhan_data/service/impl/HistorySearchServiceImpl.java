package com.wuhan_data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.HistorySearchMapper;
import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.service.HistorySearchService;


@Service
public class HistorySearchServiceImpl implements HistorySearchService{
	
	@Autowired
	HistorySearchMapper historySearchMapper;

	@Override
	public int add(HistorySearch historySearch) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HistorySearch get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(HistorySearch historySearch) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<HistorySearch> list() {
		// TODO Auto-generated method stub
		return historySearchMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
