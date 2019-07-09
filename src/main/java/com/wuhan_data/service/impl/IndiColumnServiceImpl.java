package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.IndiColumnMapper;
import com.wuhan_data.mapper.IndiCorrelativeMapper;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.service.IndiColumnService;

@Service
public class IndiColumnServiceImpl implements IndiColumnService {

	@Autowired
	IndiColumnMapper indiColumnMapper;
	
	@Override
	public int add(IndiCorrelative indiCorrelative) {
		// TODO Auto-generated method stub
		return indiColumnMapper.add(indiCorrelative);
	}

	@Override
	public void delete(int id) {
		indiColumnMapper.delete(id);

	}

	@Override
	public int update(IndiCorrelative indiCorrelative) {
		// TODO Auto-generated method stub
		return indiColumnMapper.update(indiCorrelative);
	}

	@Override
	public List<IndiCorrelative> getlist(Map map) {
		// TODO Auto-generated method stub
		return indiColumnMapper.getlist(map);
	}

	@Override
	public int total(int cid) {
		// TODO Auto-generated method stub
		return indiColumnMapper.total(cid);
	}

	@Override
	public int updateShow(IndiCorrelative indiCorrelative) {
		// TODO Auto-generated method stub
		return indiColumnMapper.updateShow(indiCorrelative);
	}

}
