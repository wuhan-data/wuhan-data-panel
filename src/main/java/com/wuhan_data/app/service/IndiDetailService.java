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

	String getIndiCode(String appIndiName);

	String getIndexName(String indexCode);

	int getIsFavorite(Map favoriteMap);

	int getIndexStatus(Map indiNameAndSourceMap);

}
