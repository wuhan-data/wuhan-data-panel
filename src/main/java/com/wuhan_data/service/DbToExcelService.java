package com.wuhan_data.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndiAll;

public interface DbToExcelService {
	
//	public byte[] exportOrderData(List<IndiAll> indiAllList);

	public List<IndexManage> getIndi(String keyword);

	public List<String> getIndiSourceByIndiName(String indiName);

	public List<String> getIndiFreqCode(Map<String, String> indiNameSourceMap);

	public List<String> getIndiStartTime(Map<String, String> indiNameSourceFreqMap);

	public List<String> getIndiEndTime(Map<String, String> indiNameSourceFreqSTimeMap);

	public List<IndiAll> getSelectIndex(Map<String, String> indiConditionMap);

}
