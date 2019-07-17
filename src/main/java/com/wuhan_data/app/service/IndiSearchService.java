package com.wuhan_data.app.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.pojo.IndexManage;

public interface IndiSearchService {
	
	public List<String> searchSource();
	public List<HistorySearch> getHistorySearch(int uid);//获得该用户的历史搜索
	public List<IndexManage> searchIndi(Map paraMap);
	public List<String> getTrendList(String nowDate);
	public List<String> getTrendList1(String nowDate);
	public void addSearchHistory(HistorySearch historySearch);
	public String getTrendSource(Map paraMap);
	public List<IndexManage> searchIndiAll(String keyWord);

}
