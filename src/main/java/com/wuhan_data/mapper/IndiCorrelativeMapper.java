package com.wuhan_data.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.tools.DataSource;


public interface IndiCorrelativeMapper {
	@DataSource(value="dataSource_dm")
	public int add(IndiCorrelative indiCorrelative);
	@DataSource(value="dataSource_dm")
	public void delete(int id);
	@DataSource(value="dataSource_dm")
	public IndiCorrelative get(int id);
	@DataSource(value="dataSource_dm")
	public int update(IndiCorrelative indiCorrelative);
	@DataSource(value="dataSource_dm")
	public List<IndiCorrelative> list();
	@DataSource(value="dataSource_dm")
	public List<IndiCorrelative> list(Page page);
	@DataSource(value="dataSource_dm")
	public int total();
	@DataSource(value="dataSource_dm")
	public List<IndiCorrelative> listAddPage(Map<String, Object> map);
	@DataSource(value="dataSource_dm")
	public String searchIndiNameById(String addCorrelativeIndiCode);
	@DataSource(value="dataSource_dm")
	public void per_show_correIndi(int id);
	@DataSource(value="dataSource_dm")
	public void no_per_show_correIndi(int id);

}
