package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Version;
import com.wuhan_data.tools.DataSource;

public interface VersionMapper {
	 	@DataSource(value="dataSource_dm")
		public int add(Version version);
		 @DataSource(value="dataSource_dm")
		public void delete(int id);
		 @DataSource(value="dataSource_dm")
		public Version get(int id);
		 @DataSource(value="dataSource_dm")
		public int update(Version version);
		 @DataSource(value="dataSource_dm")
		public List<Version> list();
		 @DataSource(value="dataSource_dm")
		public int count();
		 //列表，可分页
		@DataSource(value="dataSource_dm")
		public List<Version> listByPage(Map<String,Object> parameter);
		 //模糊查询，可分页
		@DataSource(value="dataSource_dm")
		public List<Version> search(Map<String, Object> parameter);
		//模糊查询的数量
		@DataSource(value="dataSource_dm")
		public int searchCount(Map<String ,Object>parameter);

}