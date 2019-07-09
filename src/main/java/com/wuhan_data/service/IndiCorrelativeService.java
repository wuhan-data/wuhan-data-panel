package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.Page;

public interface IndiCorrelativeService {
	
	public int add(IndiCorrelative indiCorrelative);
	
	public void delete(int id);
	
	public IndiCorrelative get(int id);
	
	public int update(IndiCorrelative indiCorrelative);
	
	public List<IndiCorrelative> list();
	public List<IndiCorrelative> list(Page page);
	
	public int total();

	public List<IndiCorrelative> listAddPage(Map<String, Object> map);

	public String searchIndiNameById(String addCorrelativeIndiCode);

	public void per_show_correIndi(int id);

	public void no_per_show_correIndi(int id);

}
