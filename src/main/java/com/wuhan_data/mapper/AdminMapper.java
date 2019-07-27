package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Admin;
import com.wuhan_data.tools.DataSource;

public interface AdminMapper {

	     @DataSource(value="dataSource_dm")
		public int add(Admin admin);
		 @DataSource(value="dataSource_dm")
		public void delete(int id);
		 @DataSource(value="dataSource_dm")
		public Admin get(int id);
		 @DataSource(value="dataSource_dm")
		public Admin getByName(String username);
		 @DataSource(value="dataSource_dm")
		public int update(Admin admin);
		 @DataSource(value="dataSource_dm")
		public List<Admin> list();
		 @DataSource(value="dataSource_dm")
		public int count() ;
		 //列表，可分页
		 @DataSource(value="dataSource_dm")
		public List<Admin> listByPage(Map<String,Object> parameter);
		 //模糊查询，可分页
		@DataSource(value="dataSource_dm")
		public List<Admin> search(Map<String, Object> parameter);
		//模糊查询的数量
		@DataSource(value="dataSource_dm")
		public int searchCount(Map<String ,Object>parameter);
		//登录验证
		@DataSource(value="dataSource_dm")
		public int adminLogin(Map<String, Object> parameter);

}
