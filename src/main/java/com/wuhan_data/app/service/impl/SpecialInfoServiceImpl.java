package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.SpecialInfoMapper;
import com.wuhan_data.app.service.SpecialInfoService;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.indi_TF;

@Service
public class SpecialInfoServiceImpl implements SpecialInfoService {
	@Autowired
	SpecialInfoMapper SpecialInfoMapper;

	@Override
	public List<ColPlate> getPlateInfo(int indexid) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getPlateInfo(indexid);
	}

	@Override
	public List<IndiCorrelative> getIndiCorrelative(int indexid) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getIndiCorrelative(indexid);
	}

	@Override
	public List<ColPlateIndi> getIndiByPid(int pid) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getIndiByPid(pid);
	}

	@Override
	public List<indi_TF> getIndiInfoByTime(Map map) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getIndiInfoByTime(map);
	}

	@Override
	public List<String> getDateCodeByFreq(Map map) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getDateCodeByFreq(map);
	}

	@Override
	public List<String> getFreqCodeByIndiId(String indi_code) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getFreqCodeByIndiId(indi_code);
	}

	@Override
	public String getIndiShowType(String indi_code) {
		// TODO Auto-generated method stub
		return SpecialInfoMapper.getIndiShowType(indi_code);
		
	}

	



}
