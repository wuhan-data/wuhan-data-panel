package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.PlateInfoMapper;
import com.wuhan_data.app.service.PlateInfoService;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.indi_TF;

@Service
public class PlateInfoServiceImpl implements PlateInfoService {
	
	@Autowired
	PlateInfoMapper plateInfoMapper;

	@Override
	public List<ColPlate> getPlateInfo(int indexid) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getPlateInfo(indexid);
	}

	@Override
	public List<IndiCorrelative> getIndiCorrelative(int indexid) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getIndiCorrelative(indexid);
	}

	@Override
	public List<ColPlateIndi> getIndiByPid(int pid) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getIndiByPid(pid);
	}

	@Override
	public List<indi_TF> getIndiInfoByTime(Map map) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getIndiInfoByTime(map);
	}

	@Override
	public List<String> getDateCodeByFreq(Map map) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getDateCodeByFreq(map);
	}

	@Override
	public List<String> getFreqCodeByIndiId(String indi_code) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getFreqCodeByIndiId(indi_code);
	}

	@Override
	public String getIndiShowType(String indi_code) {
		// TODO Auto-generated method stub
		return plateInfoMapper.getIndiShowType(indi_code);
		
	}

	

}
