package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.tools.DataSource;

public interface IndexPicManage {
	@DataSource(value="dataSource_dm")
	public int add(IndexPic indexPic); //添加一个轮播图
	@DataSource(value="dataSource_dm")
	public void delete(int id); //删除一个轮播图
	
	@DataSource(value="dataSource_dm")
	public int update(IndexPic indexPic); //更新轮播图名称
	@DataSource(value="dataSource_dm")
	public int updateTitle(IndexPic indexPic); //更新轮播图名称
	@DataSource(value="dataSource_dm")
	public List<IndexPic> getlist(Map map);//查找所有轮播图
	@DataSource(value="dataSource_dm")
	public int total();//查询数量
	@DataSource(value="dataSource_dm")
	public int updateShow(IndexPic indexPic);  //管理轮播图显示与否
	

}
