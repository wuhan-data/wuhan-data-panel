package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.ColPlateIndiMapper;
import com.wuhan_data.pojo.ColPlateIndi;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.service.ColPlateIndiService;

@Service
public class ColPlateIndiServiceImpl implements ColPlateIndiService {
	
	@Autowired
	ColPlateIndiMapper colPlateIndiMapper;
	

	@Override
	public int add(ColPlateIndi colPlateIndi) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.add(colPlateIndi);
	}

	@Override
	public void delete(int id) {
		colPlateIndiMapper.delete(id);
	}

	@Override
	public int update(ColPlateIndi colPlateIndi) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.update(colPlateIndi);
	}

	@Override
	public List<ColPlateIndi> getlist(Map map) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.getlist(map);
	}

	@Override
	public int total(int pid) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.total(pid);
	}

	@Override
	public int updateShow(ColPlateIndi colPlateIndi) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.updateShow(colPlateIndi);
	}

	@Override
	public List<IndexManage> getAllIndi(){
		// TODO Auto-generated method stub
		return colPlateIndiMapper.getAllIndi();
	}

	@Override
	public IndexManage getIndiInfo(String indi_code) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.getIndiInfo(indi_code);
	}

	@Override
	public String getPname(int pid) {
		// TODO Auto-generated method stub
		return colPlateIndiMapper.getPname(pid);
	}

}
