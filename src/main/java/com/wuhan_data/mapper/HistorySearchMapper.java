package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.HistorySearch;



public interface HistorySearchMapper {
	
public int add(HistorySearch historySearch); 
    
    public void delete(int id); 
        
    public HistorySearch get(int id); 
      
    public int update(HistorySearch historySearch);  
        
    public List<HistorySearch> list();
     
    public int count(); 

}
