package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.IndiSearchMapper;
import com.wuhan_data.app.service.IndiSearchService;
import com.wuhan_data.pojo.HistorySearch;

@Service
public class IndiSearchServiceImpl implements IndiSearchService{
	
	@Autowired
	IndiSearchMapper indiSearchMapper;

	@Override
	public List<String> searchSource() {
		// TODO Auto-generated method stub
		return indiSearchMapper.searchSource();
	}

	@Override
	public List<HistorySearch> getHistorySearch(int uid) {
		// TODO Auto-generated method stub
		return indiSearchMapper.getHistorySearch(uid);
	}

	@Override
	public List<String> searchIndi(Map paraMap) {
		// TODO Auto-generated method stub
		return indiSearchMapper.searchIndi(paraMap);
	}

	@Override
	public List<String> getTrendList(String nowDate) {
		// TODO Auto-generated method stub
		return indiSearchMapper.getTrendList(nowDate);
	}

	@Override
	public List<String> getTrendList1(String nowDate) {
		// TODO Auto-generated method stub
		return indiSearchMapper.getTrendList1(nowDate);
	}

	@Override
	public void addSearchHistory(HistorySearch historySearch) {
		// TODO Auto-generated method stub
		indiSearchMapper.addSearchHistory(historySearch);
		
	}

}
