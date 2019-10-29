package com.wuhan_data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.ThemeList;




public interface UserService {
	  public int add(User user);
	  
	  public void delete(int id);
	  
	  public User get(int id);
	  
	  public int updata(User user);      
	  
	  public List<User> list();
	  //
	  public User getByName(String username);
	  //通过角色，获得用户列表
	  public List<User> getByRole(int role_id); 
	  //通过tel，获得用户
	  public List<User> getByTel(Map<String,Object> parameter); 
	  //模糊查询，可分组
	  public List<User> search(Map<String,Object> parameter);
	  //模糊查询数量
	  public int searchCount(Map<String,Object> parameter);
	  // 列表，可分页
	  public List<User> listByPage(Map<String,Object> parameter);
	  //模糊查询真实姓名，不可分页
	  public List<User> searchByRealname(Map<String,Object> parameter);
	  //登录验证
	  public User logincheck(User user);
	  //用户注册
	  public void regist(User user);

	  public int count();
	  
	  //获得用户的所有权限
	  public Map<String, List<String>>getAllPower(int id);
	  //现有的用户中的角色是否存在该id，用于用户删除该id的时候进行判断
	  public boolean isExistRoleId(int roleId);
	  //现有的用户中的部门是否存在该id，用户部门删除该id的时候进行判断
	  public boolean isExistDepartmentId(int departmentId);
     


}
