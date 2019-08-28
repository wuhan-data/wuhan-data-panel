package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.tools.DataSource;



public interface HistorySearchMapper {
	@DataSource(value="dataSource_dm")
	public int add(HistorySearch historySearch); 
	@DataSource(value="dataSource_dm")
    public void delete(int id); 
	@DataSource(value="dataSource_dm")
    public HistorySearch get(int id); 
	@DataSource(value="dataSource_dm")
    public int update(HistorySearch historySearch);  
	@DataSource(value="dataSource_dm")
    public List<HistorySearch> list();
	@DataSource(value="dataSource_dm")
    public int count(); 

}
