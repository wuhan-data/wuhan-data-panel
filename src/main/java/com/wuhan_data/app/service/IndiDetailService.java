package com.wuhan_data.app.service;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.TPIndiValue;

public interface IndiDetailService {

	List<String> getFreqCodeByIndiName(Map fcMap);

	List<String> indiDateByFreqName(Map paraMap);

	List<TPIndiValue> getIndiValue(Map<String, Object> map);

	void indiCollect(Collect collect);

	String getIndiShowType(Map showMap);

	String getIndiCode(Map paraMap);

	String getIndexName(Map codeLjMap);

	int getIsFavorite(Map favoriteMap);

	int getIndexStatus(Map indiNameAndSourceMap);

	List<String> getFreqCodeByIndiNameG(Map fcMap);

	List<String> indiDateByFreqNameG(Map paraMap);

	List<String> getAreaNameListG(Map paraMap);
	List<String> indiDateByFreqNameG1(Map parameterMap);
	List<TPIndiValue> getIndiValueG(Map<String, Object> map);

	String getIndexNameH(Map codeLjMap);

	List<String> getFreqCodeByIndiNameArea(Map fcMap);

	List<String> indiDateByFreqNameArea(Map paraMap);

	List<String> getIndiAreaList(Map paraMap);

	List<String> indiDateByFreqNameDefault(Map parameterMap);

	List<TPIndiValue> getIndiValueArea(Map defaultMap);

	String getIndiCodeG(Map paraMap);

}
