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
	public String getIndiCode(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiCode(paraMap);
	}
	@Override
	public String getIndexName(Map codeLjMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndexName(codeLjMap);
	}
	@Override
	public int getIsFavorite(Map favoriteMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIsFavorite(favoriteMap);
	}
	@Override
	public int getIndexStatus(Map indiNameAndSourceMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndexStatus(indiNameAndSourceMap);
	}
	@Override
	public List<String> getFreqCodeByIndiNameG(Map fcMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getFreqCodeByIndiNameG(fcMap);
	}
	@Override
	public List<String> indiDateByFreqNameG(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.indiDateByFreqNameG(paraMap);
	}
	@Override
	public List<String> getAreaNameListG(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getAreaNameListG(paraMap);
	}
	@Override
	public List<String> indiDateByFreqNameG1(Map parameterMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.indiDateByFreqNameG1(parameterMap);
	}
	@Override
	public List<TPIndiValue> getIndiValueG(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiValueG(map);
	}
	@Override
	public String getIndexNameH(Map codeLjMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndexNameH(codeLjMap);
	}
	@Override
	public List<String> getFreqCodeByIndiNameArea(Map fcMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getFreqCodeByIndiNameArea(fcMap);
	}
	@Override
	public List<String> indiDateByFreqNameArea(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.indiDateByFreqNameArea(paraMap);
	}
	@Override
	public List<String> getIndiAreaList(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiAreaList(paraMap);
	}
	@Override
	public List<String> indiDateByFreqNameDefault(Map parameterMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.indiDateByFreqNameDefault(parameterMap);
	}
	@Override
	public List<TPIndiValue> getIndiValueArea(Map defaultMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiValueArea(defaultMap);
	}
	@Override
	public String getIndiCodeG(Map paraMap) {
		// TODO Auto-generated method stub
		return indiDetailMapper.getIndiCodeG(paraMap);
	}

}
