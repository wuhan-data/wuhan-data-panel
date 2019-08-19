package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.SpecialDetail;

public interface IndexPicManage {

	public int add(IndexPic indexPic); //添加一个轮播图
	
	public void delete(int id); //删除一个轮播图
	
	
	public int update(IndexPic indexPic); //更新轮播图名称
	
	public int updateTitle(IndexPic indexPic); //更新轮播图名称
	
	public List<IndexPic> getlist(Map map);//查找所有轮播图
	
	public int total();//查询数量
	
	public int updateShow(IndexPic indexPic);  //管理轮播图显示与否
	

}
