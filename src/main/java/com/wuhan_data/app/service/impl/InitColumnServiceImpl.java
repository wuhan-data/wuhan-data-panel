package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.InitColumnMapper;
import com.wuhan_data.app.service.InitColumnService;
import com.wuhan_data.pojo.AnalysisManage;

@Service
public class InitColumnServiceImpl implements InitColumnService {
	
	@Autowired
	InitColumnMapper initColumnMapper;

	@Override
	public List<AnalysisManage> getOnelist() {
		// TODO Auto-generated method stub
		return initColumnMapper.getOnelist();
	}

	@Override
	public List<AnalysisManage> getTwolist() {
		// TODO Auto-generated method stub
		return initColumnMapper.getTwolist();
	}

}
