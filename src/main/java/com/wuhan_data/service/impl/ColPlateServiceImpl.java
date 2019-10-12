package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.ColPlateMapper;
import com.wuhan_data.pojo.ColPlate;
import com.wuhan_data.service.ColPlateService;

@Service
public class ColPlateServiceImpl implements ColPlateService {
	
	@Autowired
	ColPlateMapper colPlateMapper;

	@Override
	public int add(ColPlate colPlate) {
		// TODO Auto-generated method stub
		return colPlateMapper.add(colPlate);
	}

	@Override
	public void delete(int id) {
		colPlateMapper.delete(id);

	}

	@Override
	public int update(ColPlate colPlate) {
		// TODO Auto-generated method stub
		return colPlateMapper.update(colPlate);
	}

	@Override
	public List<ColPlate> getlist(Map map) {
		// TODO Auto-generated method stub
		return colPlateMapper.getlist(map);
	}

	@Override
	public int total(int cid) {
		// TODO Auto-generated method stub
		return colPlateMapper.total(cid);
	}

	@Override
	public int updateShow(ColPlate colPlate) {
		// TODO Auto-generated method stub
		return colPlateMapper.updateShow(colPlate);
	}

	@Override
	public int getAddPid(int cid) {
		// TODO Auto-generated method stub
		return colPlateMapper.getAddPid(cid);
	}

	@Override
	public String getAddCname(int cid) {
		// TODO Auto-generated method stub
		return colPlateMapper.getAddCname(cid);
	}

	@Override
	public int updateWeight(Map map) {
		// TODO Auto-generated method stub
		return colPlateMapper.updateWeight(map);
	}

	@Override
	public int getPid(Map map) {
		// TODO Auto-generated method stub
		return colPlateMapper.getPid(map);
	}

	@Override
	public String getThemeName(int theme_id) {
		// TODO Auto-generated method stub
		return colPlateMapper.getThemeName(theme_id);
	}

}
