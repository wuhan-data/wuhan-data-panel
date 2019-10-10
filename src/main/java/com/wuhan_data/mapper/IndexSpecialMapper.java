package com.wuhan_data.mapper;

import java.util.List;

import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.tools.DataSource;

public interface IndexSpecialMapper {
	@DataSource(value="dataSource_dm")
    public List<IndexSpecial> list(); 

}
