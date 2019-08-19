package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;


import com.wuhan_data.pojo.Menu;
import com.wuhan_data.tools.DataSource;
public interface MenuMapper {
	 @DataSource(value="dataSource_mysql")
		public int add(Menu menu);
		 @DataSource(value="dataSource_mysql")
		public void delete(int id);
		 @DataSource(value="dataSource_mysql")
		public Menu get(int id);
		 @DataSource(value="dataSource_mysql")
		public int update(Menu menu);
		 @DataSource(value="dataSource_mysql")
		public List<Menu> list();
		 @DataSource(value="dataSource_mysql")
		public int count() ;
		 //精确查询
		 @DataSource(value="dataSource_mysql")
		public List<Menu> searchByRole(String[] parameter);
		 @DataSource(value="dataSource_mysql")
		public List<Menu> searchByRoleGroupByOne(String[] parameter);
		 @DataSource(value="dataSource_mysql")
		public List<Menu> searchByRoleAndLevel_one(Map<String, Object> parameter);
		 @DataSource(value="dataSource_mysql")
		public List<Menu> getRole_nameBy(Map<String, Object> parameter);
		 //获取第一级目录
		 @DataSource(value="dataSource_mysql")
		public List<Menu> getLevelOne();
		 //获取第一级目录对应的第二级目录
		 @DataSource(value="dataSource_mysql")
		public List<Menu> getLevelTwoByLevelOne(Map<String, Object> parameter);
		 

}
