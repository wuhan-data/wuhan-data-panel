package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wuhan_data.pojo.UserOpLog;

public interface UserOpLogService {
	
	public int add(UserOpLog userOpLog);
	
	public int delete(int id);
	
	public UserOpLog get(int id);
	
	public int update(UserOpLog userOpLog);
	
	public List<UserOpLog> list();
	
	public int count();
	
	public List<UserOpLog> listByPage(Map<String, Object> parameter);
	
	public List<UserOpLog> search(Map<String, Object> parameter);
	
	public int searchCount(Map<String, Object>parameter);
	
	public int addOp(int user_id,String op_msg,HttpServletRequest request, String op_interface);
}
