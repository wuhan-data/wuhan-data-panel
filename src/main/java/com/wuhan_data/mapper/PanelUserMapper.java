package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.PanelUser;


public interface PanelUserMapper {
	    public int add(PanelUser panelUser); 
     
	    public void delete(int id); 
	        
	    public PanelUser get(int id); 
	      
	    public int update(PanelUser panelUser);  
	        
	    public List<PanelUser> list();
	     
	    public int count(); 

}
