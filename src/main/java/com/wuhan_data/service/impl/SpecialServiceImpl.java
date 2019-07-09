package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.SpecialMapper;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.service.SpecialService;

@Service
public class SpecialServiceImpl implements SpecialService {
	
	@Autowired
	SpecialMapper specialMapper;
	

	@Override
	public int add(SpecialDetail specialDetail) {
		// TODO Auto-generated method stub
		return specialMapper.add(specialDetail);
	}

	@Override
	public void delete(int id) {
		specialMapper.delete(id);
	}

	@Override
	public int update(SpecialDetail specialDetail) {
		// TODO Auto-generated method stub
		return specialMapper.update(specialDetail);
	}

	@Override
	public List<SpecialDetail> getlist(Map map) {
		// TODO Auto-generated method stub
		return specialMapper.getlist(map);
	}

	@Override
	public int total() {
		// TODO Auto-generated method stub
		return specialMapper.total();
	}

	@Override
	public int updateShow(SpecialDetail specialDetail) {
		// TODO Auto-generated method stub
		return specialMapper.updateShow(specialDetail);
	}

	@Override
	public int maxSpecialId() {
		// TODO Auto-generated method stub
		return specialMapper.maxSpecialId();
	}

	@Override
	public List<SpecialDetail> getIndiList(Map map) {
		// TODO Auto-generated method stub
		return specialMapper.getIndiList(map);
	}

	@Override
	public int deleteIndi(int id) {
		// TODO Auto-generated method stub
		return specialMapper.deleteIndi(id);
	}

	@Override
	public int addIndi(SpecialDetail specialDetail) {
		// TODO Auto-generated method stub
		return specialMapper.addIndi(specialDetail);
	}

	@Override
	public int updateIndi(SpecialDetail specialDetail) {
		// TODO Auto-generated method stub
		return specialMapper.updateIndi(specialDetail);
	}

	@Override
	public int updateIndiShow(SpecialDetail specialDetail) {
		// TODO Auto-generated method stub
		return specialMapper.updateIndiShow(specialDetail);
	}

	@Override
	public int totalIndi(int special_id) {
		// TODO Auto-generated method stub
		return specialMapper.totalIndi(special_id);
	}

	@Override
	public String getSname(int special_id) {
		// TODO Auto-generated method stub
		return specialMapper.getSname(special_id);
	}

}
