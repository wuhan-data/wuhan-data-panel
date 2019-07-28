package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.AnalysisIcon;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.tools.DataSource;

public interface AppIndexMapper {
	
	@DataSource(value="dataSource_dm")
	public List<IndexPic> getlist(); //查询轮播图
	@DataSource(value="dataSource_dm")
	public List<AnalysisIcon> getIconList(); //查询经济分析icon
	@DataSource(value="dataSource_dm")
	public List<IndexSpecial> getIndexSpecialList(); //查询首页专题
	@DataSource(value="dataSource_dm")
	public List<IndexSpecial> getDesc(int topicId);//获取专题描述信息
	

}
