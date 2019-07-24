package com.wuhan_data.service;

import java.util.List;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiAll;

public interface DbToExcelService {
	
	public byte[] exportOrderData(String id);

	public List<IndexManage> getIndi(String keyword);

	public List<String> getIndiSourceByIndiName(String indiName);

}
