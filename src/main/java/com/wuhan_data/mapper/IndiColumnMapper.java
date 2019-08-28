package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.tools.DataSource;


public interface IndiColumnMapper {
	@DataSource(value="dataSource_dm")
	public int add(IndiCorrelative indiCorrelative);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public int update(IndiCorrelative indiCorrelative);
	@DataSource(value="dataSource_dm")
	public List<IndiCorrelative> getlist(Map map);//根据栏目id查找所有指标
	@DataSource(value="dataSource_dm")
	public int total(int cid);//查询数量
	@DataSource(value="dataSource_dm")
	public int updateShow(IndiCorrelative indiCorrelative);  //管理显示与否

}
