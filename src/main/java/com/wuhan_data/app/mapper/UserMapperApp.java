package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.DataSource;

public interface UserMapperApp {
		@DataSource(value="dataSource_dm")
		public int add(User user); 
		@DataSource(value="dataSource_dm")  
		public void delete(int id); 
		@DataSource(value="dataSource_dm")  
		public User get(int id); 
		@DataSource(value="dataSource_dm")
		public int update(User user);  
		
		//通过tel改密码
		@DataSource(value="dataSource_dm")
		public int updatePasswordByTel(Map<String,Object> parameter);  
		//tongguo id gai mima 
		@DataSource(value="dataSource_dm")
		public int updatePasswordById(Map<String,Object> parameter);  
		//通过id改tel
		@DataSource(value="dataSource_dm")
		public int updateTelById(Map<String,Object> parameter);  
		@DataSource(value="dataSource_dm")  
		public List<User> list();
		@DataSource(value="dataSource_dm")
		public int count(); 
		//通过名字，获得用户
		@DataSource(value="dataSource_dm")  
		public User getByName(String username); 
		//通过tel，获得用户
		@DataSource(value="dataSource_dm")  
		public User getByTel(String tel); 
		//通过角色获得用户
		@DataSource(value="dataSource_dm")  
		public List<User> getByRole(int role_id); 
		//模糊查询，可分页
		@DataSource(value="dataSource_dm")
		public List<User> search(Map<String,Object> parameter);
		//模糊查询数量
		@DataSource(value="dataSource_dm")
		public int searchCount(Map<String,Object> parameter);
		//列表，可分页
		@DataSource(value="dataSource_dm")
		public List<User> listByPage(Map<String,Object> parameter);

		//登录验证
		@DataSource(value="dataSource_dm")
		User logincheck(User user);
		
		@DataSource(value="dataSource_dm")
		User logincheckByTel(Map<String,Object> parameter);
		
		
		@DataSource(value="dataSource_dm")
		int telIsExist(Map<String,Object> parameter);
		
		@DataSource(value="dataSource_dm")
		int setHeadById(Map<String,Object> parameter);

	

}