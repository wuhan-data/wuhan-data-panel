package com.wuhan_data.app.mapper;

import com.wuhan_data.tools.DataSource;

import java.util.ArrayList;


public interface AuthorityMapper {

		@DataSource(value="dataSource_dm")
	    public ArrayList<Object> getAnalysisListByUserId(Integer userId);


}
