package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.InitColumnMapper;
import com.wuhan_data.app.service.InitColumnService;
import com.wuhan_data.pojo.AnalysisManage;
//import com.wuhan_data.pojo.ColCollection;
import com.wuhan_data.pojo.Collect;

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
	public List<AnalysisManage> getTwolist(int weight) {
		// TODO Auto-generated method stub
		return initColumnMapper.getTwolist(weight);
	}

	@Override
	public String getFavorite(int cid) {
		// TODO Auto-generated method stub
		return initColumnMapper.getFavorite(cid);
		
	}

	@Override
	public void setFavorite(int cid) {
		 initColumnMapper.setFavorite(cid);

		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNotFavorite(int cid) {
		initColumnMapper.setNotFavorite(cid);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colCollect(Collect collect) {
		initColumnMapper.colCollect(collect);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colDelete(int cid) {
		initColumnMapper.colDelete(cid);
		// TODO Auto-generated method stub
		
	}

}
