package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.HistoryView;



public interface HistoryViewMapper {
	public int add(HistoryView historyView); 
    
    public void delete(int id); 
        
    public HistoryView get(int id); 
      
    public int update(HistoryView historyView);  
        
    public List<HistoryView> list();
     
    public int count(); 

}
