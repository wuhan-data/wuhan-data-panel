package com.wuhan_data.service;

import java.util.List;

import com.wuhan_data.pojo.Collect;

public interface CollectService {
	
	public int add(Collect collect); 
    
    public void delete(int id); 
        
    public Collect get(int id); 
      
    public int update(Collect collect);  
        
    public List<Collect> list();
     
    public int count(); 

}
