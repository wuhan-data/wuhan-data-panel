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
     
	    public void delete(int id); 
	        
	    public IndexManage get(int id); 
	      
	    public void update(IndexManage indexManager);  
	        
	    public List<IndexManage> list();
	   
	    @DataSource(value="dataSource_mysql")
	    public List<IndexManage> list1();
	     
	    public int count(); 
	    
	    public List<IndexManage> list(Page page);
	     
	    public int total();

		public void per_show(int id);

		public void no_per_show(int id);

		public List<String> IndiSearch(String keyword);

		public List<IndexManage> indiSearch(Map map);

		public List<IndexManage> listAddPage(Map<String, Object> map);

		public int searchCount(Map<String, Object> mapSearch);
		
		//模拟同方数据库
		@DataSource(value="dataSource_mysql")
	    public List<indi_TF> listTF();

		public void add_init(IndexManage indexManage);
		 
		 public List<IndexManage> listIndi();
}
