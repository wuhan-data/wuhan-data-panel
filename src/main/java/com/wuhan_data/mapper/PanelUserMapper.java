package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.PanelUser;
import com.wuhan_data.tools.DataSource;


public interface PanelUserMapper {
		@DataSource(value="dataSource_dm")
	    public int add(PanelUser panelUser); 
		@DataSource(value="dataSource_dm")
	    public void delete(int id); 
		@DataSource(value="dataSource_dm")
	    public PanelUser get(int id); 
		@DataSource(value="dataSource_dm")
	    public int update(PanelUser panelUser);  
		@DataSource(value="dataSource_dm")
	    public List<PanelUser> list();
		@DataSource(value="dataSource_dm")
	    public int count(); 

}
