package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.pojo.indi_TF;
import com.wuhan_data.tools.DataSource;


public interface IndexManageMapper {
		@DataSource(value="dataSource_dm")
	    public int add(IndexManage indexManager); 
		@DataSource(value="dataSource_dm")
	    public void delete(int id); 
		@DataSource(value="dataSource_dm")
	    public IndexManage get(int id); 
		@DataSource(value="dataSource_dm")
	    public void update(IndexManage indexManager);  
		@DataSource(value="dataSource_dm")
	    public List<IndexManage> list();
	   
	    @DataSource(value="dataSource_mysql")
	    public List<IndexManage> list1();
	    @DataSource(value="dataSource_dm")
	    public int count(); 
	    @DataSource(value="dataSource_dm")
	    public List<IndexManage> list(Page page);
	    @DataSource(value="dataSource_dm")
	    public int total();
	    @DataSource(value="dataSource_dm")
		public void per_show(int id);
	    @DataSource(value="dataSource_dm")
		public void no_per_show(int id);
	    @DataSource(value="dataSource_dm")
		public List<String> IndiSearch(String keyword);
	    @DataSource(value="dataSource_dm")
		public List<IndexManage> indiSearch(Map map);
	    @DataSource(value="dataSource_dm")
		public List<IndexManage> listAddPage(Map<String, Object> map);
	    @DataSource(value="dataSource_dm")
		public int searchCount(Map<String, Object> mapSearch);
		
		//模拟同方数据库
		@DataSource(value="dataSource_mysql")
	    public List<indi_TF> listTF();
	    @DataSource(value="dataSource_dm")
		public void add_init(IndexManage indexManage);
	    @DataSource(value="dataSource_dm")
		 public List<IndexManage> listIndi();
}
