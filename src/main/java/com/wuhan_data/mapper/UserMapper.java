package com.wuhan_data.mapper;
import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.DataSource;

//import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
public interface UserMapper {
	
//	@DataSource(value="dataSource_mysql")
	    public int add(User user); 
//	@DataSource(value="dataSource_mysql")  
	    public void delete(int id); 
//	@DataSource(value="dataSource_mysql")  
	    public User get(int id); 
//	@DataSource(value="dataSource_mysql")
	    public int update(User user);  
//	@DataSource(value="dataSource_mysql")  
	    public List<User> list();
//	@DataSource(value="dataSource_mysql")
	    public int count(); 
	//通过名字，获得用户
//	@DataSource(value="dataSource_mysql")  
    public User getByName(String username); 
	//通过角色获得用户
//	@DataSource(value="dataSource_mysql")  
    public List<User> getByRole(int role_id); 
	//模糊查询，可分页
//	@DataSource(value="dataSource_mysql")
	public List<User> search(Map<String,Object> parameter);
	//模糊查询数量
//	@DataSource(value="dataSource_mysql")
    public int searchCount(Map<String,Object> parameter);
	//列表，可分页
//	@DataSource(value="dataSource_mysql")
	public List<User> listByPage(Map<String,Object> parameter);
	
		//登录验证
//	    @DataSource(value="dataSource_mysql")
		User logincheck(User user);
		//注册
//	    @DataSource(value="dataSource_mysql")
	    void register(User user);


}
