package com.wuhan_data.mapper;
import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.DataSource;

public interface UserMapper {
	
	@DataSource(value="dataSource_dm")
	public int add(User user); 
	@DataSource(value="dataSource_dm")  
	public void delete(int id); 
	@DataSource(value="dataSource_dm")  
	public User get(int id); 
	@DataSource(value="dataSource_dm")
	public int update(User user);  
	@DataSource(value="dataSource_dm")  
	public List<User> list();
	@DataSource(value="dataSource_dm")
	public int count(); 
	//通过名字，获得用户
	@DataSource(value="dataSource_dm")  
    public User getByName(String username); 
	//通过角色获得用户
	@DataSource(value="dataSource_dm")  
    public List<User> getByRole(String role_id); 
	//通过tel获得用户
	@DataSource(value="dataSource_dm")  
	public List<User> getByTel(Map<String,Object> parameter); 
	//模糊查询，可分页
	@DataSource(value="dataSource_dm")
	public List<User> search(Map<String,Object> parameter);
	//模糊查询数量
	@DataSource(value="dataSource_dm")
    public int searchCount(Map<String,Object> parameter);
	//列表，可分页
	@DataSource(value="dataSource_dm")
	public List<User> listByPage(Map<String,Object> parameter);
	//模糊查询真是姓名，不可分页
	@DataSource(value="dataSource_dm")
	public List<User> searchByRealname(Map<String,Object> parameter);
	//登录验证
	@DataSource(value="dataSource_dm")
	User logincheck(User user);
	//注册
	@DataSource(value="dataSource_dm")
	void register(User user);


}
