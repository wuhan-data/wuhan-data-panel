package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.tools.DataSource;

public interface IndiSearchMapper {
	
	 public List<String> searchSource();//获得指标搜索的所有来源
	 @DataSource(value="dataSource_dm")
	 public List<HistorySearch> getHistorySearch(int uid);//获得该用户的历史搜索
	 @DataSource(value="dataSource_mysql")
	public List<IndexManage> searchIndi(Map paraMap);
	 @DataSource(value="dataSource_dm")
	public List<String> getTrendList(String nowDate);
	 @DataSource(value="dataSource_dm")
	public List<String> getTrendList1(String nowDate);
	 @DataSource(value="dataSource_dm")
	public void addSearchHistory(HistorySearch historySearch);
	 @DataSource(value="dataSource_dm")
	public String getTrendSource(Map paraMap);
	 @DataSource(value="dataSource_mysql")
	public List<IndexManage> searchIndiG(String keyWord);
	@DataSource(value="dataSource_mysql")
	public List<IndexManage> searchIndiH(String keyWord);
	@DataSource(value="dataSource_mysql")
	public int getIsArea(IndexManage indexManage);
	 @DataSource(value="dataSource_dm")
	public String getTrendLj(Map paraMap);
	 
	 @DataSource(value="dataSource_mysql")
	public List<IndexManage> searchIndiHPower();
	 

}
