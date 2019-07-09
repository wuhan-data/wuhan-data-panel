package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Notice;

public interface NoticeService {
	
	
	public int add(Notice notice);
	
	public int addByRole(List<Notice> notice);
	
	public void delete(int id);
	
	public Notice get(int id);
	
	public int update(Notice notice);
	
	public List<Notice> list();
	
	public int count();
	
	public List<Notice> listByPage(Map<String, Object> parameter);
	//模糊查询可以分页
	public List<Notice> searchByContent(Map<String, Object> parameter);
	//模糊查询的数量
	public int searchCountByContent(Map<String, Object> parameter);


}
