package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.tools.DataSource;

public interface IndiSearchMapper {
	
	 public List<String> searchSource();//获得指标搜索的所有来源
	 public List<HistorySearch> getHistorySearch(int uid);//获得该用户的历史搜索
	 @DataSource(value="dataSource_mysql")
	public List<IndexManage> searchIndi(Map paraMap);
	public List<String> getTrendList(String nowDate);
	public List<String> getTrendList1(String nowDate);
	public void addSearchHistory(HistorySearch historySearch);
	public String getTrendSource(Map paraMap);
	@DataSource(value="dataSource_mysql")
	public List<IndexManage> searchIndiAll(String keyWord);
	 

}
