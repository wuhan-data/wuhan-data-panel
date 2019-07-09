package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.HistorySearch;

public interface IndiSearchService {
	
	public List<String> searchSource();
	public List<HistorySearch> getHistorySearch(int uid);//获得该用户的历史搜索

}
