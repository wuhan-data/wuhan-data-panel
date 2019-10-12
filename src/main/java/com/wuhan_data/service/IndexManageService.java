package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.pojo.indi_TF;

public interface IndexManageService {
	
	public int add(IndexManage indexManage); 
    
    public void delete(int id); 
        
    public IndexManage get(int id); 
      
    public void update(IndexManage indexManage);  
        
    public List<IndexManage> list();
  
     
    public int count(); 
    
    int total();
    List<IndexManage> list(Page page);

//    是否展示指标
	public void per_show(int id);
	public void no_per_show(int id);
	
	//指标搜索
	public List<String> IndiSearch(String keyword);

	public List<IndexManage> indiSearch(Map map);

	public List<IndexManage> listAddPage(Map<String, Object> map);

	public int searchCount(Map<String, Object> mapSearch);
	
	public List<IndexManage> list1();
	
	public List<indi_TF> listTF();

	public void add_init(IndexManage indexManage);
	public List<IndexManage> listIndi();

	public int totalG();

	public List<IndexManage> listAddPageG(Map<String, Object> map);

	public void updateG(IndexManage indexManage);

	public void deleteG(int indi_id);

	public void addG(IndexManage indexManage);

	public void per_showG(int indi_id);

	public void no_per_showG(int indi_id);

	public int searchCountG(Map<String, Object> mapSearch);

	public List<IndexManage> indiSearchG(Map<String, Object> map);
}
