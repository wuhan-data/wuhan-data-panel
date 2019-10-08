package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.pojo.User;;

public interface SysLogService {

	public int add(SysLog sysLog);
	
	public void delete(int id);
	
	public SysLog get(int id) ;
	
	public int update(SysLog sysLog);
	
	public List<SysLog> list();
	
	public int count();
	
	 // 列表，可分页
	public List<SysLog> listByPage(Map<String,Object> parameter);
	//模糊查询，可分组
	public List<SysLog> search(Map<String,Object> parameter);
	//模糊查询数量
	public int searchCount(Map<String,Object> parameter);
	
	//前台出现错误
	public int addUser(HttpServletRequest request,String e_interface,String e_msg,Exception ex);
	
	//后台出现错误
	public int addAdmin(HttpServletRequest request,String e_interface,String e_msg,Exception ex);
}
