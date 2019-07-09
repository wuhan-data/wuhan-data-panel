package com.wuhan_data.service;

import java.util.List;

import com.wuhan_data.pojo.ErrorLog;


public interface ErrorLogService {
	
	public int add(ErrorLog errorLog); 
    
    public void delete(int id); 
        
    public ErrorLog get(int id); 
      
    public int update(ErrorLog errorLog);  
        
    public List<ErrorLog> list();
     
    public int count(); 

}
