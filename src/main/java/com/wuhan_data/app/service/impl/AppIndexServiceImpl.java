package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.AppIndexMapper;
import com.wuhan_data.app.service.AppIndexService;
import com.wuhan_data.pojo.AnalysisIcon;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;

@Service
public class AppIndexServiceImpl implements AppIndexService {
	
	@Autowired
	AppIndexMapper appIndexMapper;

	@Override
	public List<IndexPic> getlist() {
		// TODO Auto-generated method stub
		
		return appIndexMapper.getlist();
	}

	@Override
	public List<AnalysisIcon> getIconList() {
		// TODO Auto-generated method stub
		return appIndexMapper.getIconList();
	}

	@Override
	public List<IndexSpecial> getIndexSpecialList() {
		// TODO Auto-generated method stub
		return appIndexMapper.getIndexSpecialList();
	}

	@Override
	public List<IndexSpecial> getDesc(int topicId) {
		// TODO Auto-generated method stub
		return appIndexMapper.getDesc(topicId);
	}

}
