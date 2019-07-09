package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.AnalysisManage;

public interface InitColumnService {
	
	 public List<AnalysisManage> getOnelist();//得到排序的一级栏目	  
	  public List<AnalysisManage> getTwolist();//得到二级栏目

}
