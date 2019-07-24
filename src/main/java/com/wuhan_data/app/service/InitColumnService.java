package com.wuhan_data.app.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisManage;


import com.wuhan_data.pojo.Collect;

public interface InitColumnService {
	
	 public List<AnalysisManage> getOnelist();//得到排序的一级栏目	  
	  public List<AnalysisManage> getTwolist(int weight);//得到二级栏目
	  public String getFavorite(int cid);//得到栏目收藏状态
	  public void setFavorite(int cid);//设置为收藏状态
	  public void setNotFavorite(int cid);//设置为未收藏状态
	  public void colCollect(Collect collect);
	  public void colDelete(int cid);


}
