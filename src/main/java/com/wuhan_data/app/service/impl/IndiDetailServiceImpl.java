package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.IndiDetailMapper;
import com.wuhan_data.app.service.IndiDetailService;

@Service
public class IndiDetailServiceImpl implements IndiDetailService{
	
	@Autowired
	IndiDetailMapper indiDetailMapper;
	@Override
	public List<String> getFreqCodeByIndiName(String appIndiName) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getFreqCodeByIndiName(appIndiName);
	}
	@Override
	public List<String> indiDateByFreqName(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.indiDateByFreqName(paraMap);
	}

}
