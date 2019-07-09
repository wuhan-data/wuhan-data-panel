package com.wuhan_data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.CollectMapper;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.service.CollectService;

@Service
public class CollectServiceImpl implements CollectService{
	
	@Autowired
	CollectMapper collectMapper;

	@Override
	public int add(Collect collect) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collect get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Collect collect) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Collect> list() {
		// TODO Auto-generated method stub
		return collectMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
