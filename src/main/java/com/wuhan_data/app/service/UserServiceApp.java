package com.wuhan_data.app.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.User;

public interface UserServiceApp {
	public int add(User user);
	  
	  public void delete(int id);
	  
	  public User get(int id);
	  
	  public int updata(User user);    
	  
	  public int updatePasswordByTel(Map<String,Object> parameter);
	  //
	  public int updatePasswordById(Map<String,Object> parameter);
	  
	  //
	  public int updateTelById(Map<String,Object> parameter);
	  
	  public List<User> list();
	  //
	  public User getByName(String username);
	  //
	  public User getByTel(String tel);
	  //通过角色，获得用户列表
	  public List<User> getByRole(int role_id); 
	  //模糊查询，可分组
	  public List<User> search(Map<String,Object> parameter);
	  //模糊查询数量
	  public int searchCount(Map<String,Object> parameter);
	  // 列表，可分页
	  public List<User> listByPage(Map<String,Object> parameter);
	  //登录验证
	  public User logincheck(User user);
	  public User logincheckByTel(Map<String,Object> parameter);
	  //用户注册
	  public void register(Map<String,Object> parameter);
	  public int telIsExist(Map<String,Object> parameter);
	  public int count();
	  public int setHeadById(Map<String,Object> parameter);
   

}
