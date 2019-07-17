package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.IndiDetailMapper;
import com.wuhan_data.app.service.IndiDetailService;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.TPIndiValue;

@Service
public class IndiDetailServiceImpl implements IndiDetailService{
	
	@Autowired
	IndiDetailMapper indiDetailMapper;
	@Override
	public List<String> getFreqCodeByIndiName(Map fcMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getFreqCodeByIndiName(fcMap);
	}
	@Override
	public List<String> indiDateByFreqName(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.indiDateByFreqName(paraMap);
	}
	@Override
	public List<TPIndiValue> getIndiValue(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiValue(map);
	}
	@Override
	public void indiCollect(Collect collect) {
		// TODO Auto-generated method stub
		indiDetailMapper.indiCollect(collect);
	}
	@Override
	public String getIndiShowType(Map showMap) {
		// TODO Auto-generated method stub
		System.out.println("showMap:"+showMap.get("appIndiName"));
		System.out.println("showMap:"+showMap.get("source"));
		return indiDetailMapper.getIndiShowType(showMap);
	}
	@Override
	public String getIndiCode(String appIndiName) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiCode(appIndiName);
	}

}
