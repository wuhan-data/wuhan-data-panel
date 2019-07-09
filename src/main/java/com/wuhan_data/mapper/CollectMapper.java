package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.Collect;

public interface CollectMapper {
	
	    public int add(Collect collect); 
	   
	    public void delete(int id); 
  
	    public Collect get(int id); 
	      
	    public int update(Collect collect);  
	        
	    public List<Collect> list();
	     
	    public int count(); 

}
