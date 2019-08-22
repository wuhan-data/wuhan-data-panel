package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.SpecialMapper;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.service.SpecialService;

@Service
public class SpecialServiceImpl implements SpecialService {
	
	@Autowired
	SpecialMapper specialMapper;

	@Override
	public int add(IndexSpecial indexSpecial) {
		// TODO Auto-generated method stub
		return specialMapper.add(indexSpecial);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		specialMapper.delete(id);
		
	}

	@Override
	public int update(IndexSpecial indexSpecial) {
		// TODO Auto-generated method stub
		return specialMapper.update(indexSpecial);
	}

	@Override
	public List<IndexSpecial> getlist(Map map) {
		// TODO Auto-generated method stub
		return specialMapper.getlist(map);
	}

	@Override
	public int total() {
		// TODO Auto-generated method stub
		return specialMapper.total();
	}

	@Override
	public int updateShow(IndexSpecial indexSpecial) {
		// TODO Auto-generated method stub
		return specialMapper.updateShow(indexSpecial);
	}

	@Override
	public String getSname(int id) {
		// TODO Auto-generated method stub
		return specialMapper.getSname(id);
	}

	@Override
	public int reOrderByTitle(IndexSpecial indexSpecial) {
		// TODO Auto-generated method stub
		return specialMapper.reOrderByTitle(indexSpecial);
	}

	@Override
	public int updateTitle(IndexSpecial indexSpecial) {
		// TODO Auto-generated method stub
		return specialMapper.updateTitle(indexSpecial);
	}
	
	
	

	
}
