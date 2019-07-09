package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.AnalysisIcon;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;

public interface AppIndexMapper {
	
	public List<IndexPic> getlist(); //查询轮播图
	public List<AnalysisIcon> getIconList(); //查询经济分析icon
	public List<IndexSpecial> getIndexSpecialList(); //查询首页专题
	
	public List<IndexSpecial> getDesc(int topicId);//获取专题描述信息
	

}
