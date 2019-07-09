package com.wuhan_data.app.service;

import java.util.List;
import java.util.Map;

public interface IndiDetailService {

	List<String> getFreqCodeByIndiName(String appIndiName);

	List<String> indiDateByFreqName(Map paraMap);

}
