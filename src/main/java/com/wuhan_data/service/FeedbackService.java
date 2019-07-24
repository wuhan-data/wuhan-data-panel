package com.wuhan_data.service;
import java.util.List;
import java.util.Map;
import com.wuhan_data.pojo.Feedback;
public interface FeedbackService {
	public int add(Feedback feedback);
	public void delete(int id);
	public Feedback get(int id);	
	public int update(Feedback feedback);	
	public List<Feedback> list();	
	public int count();
	// 列表，可分页
	public List<Feedback> listByPage(Map<String,Object> parameter);
	//模糊查询，可分组
	public List<Feedback> search(Map<String,Object> parameter);
	//模糊查询数量
	public int searchCount(Map<String,Object> parameter);

}
