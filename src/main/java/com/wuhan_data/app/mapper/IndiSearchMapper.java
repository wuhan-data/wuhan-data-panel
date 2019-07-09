package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.HistorySearch;

public interface IndiSearchMapper {
	
	 public List<String> searchSource();//获得指标搜索的所有来源
	 public List<HistorySearch> getHistorySearch(int uid);//获得该用户的历史搜索
	 

}
