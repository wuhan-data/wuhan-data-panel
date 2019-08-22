package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.IndexPicManage;
import com.wuhan_data.pojo.IndexManage;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.pojo.indi_TF;
import com.wuhan_data.service.IndexManageService;
import com.wuhan_data.service.IndexPicService;

@Service
public class IndexPicServiceImpl implements IndexPicService {
	
	
	@Autowired
	IndexPicManage indexPicManage;

	@Override
	public int add(IndexPic indexPic) {
		// TODO Auto-generated method stub
		return indexPicManage.add(indexPic);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		indexPicManage.delete(id);
		
	}

	@Override
	public int update(IndexPic indexPic) {
		// TODO Auto-generated method stub
		
		return indexPicManage.update(indexPic);
	}

	@Override
	public List<IndexPic> getlist(Map map) {
		// TODO Auto-generated method stub
		return indexPicManage.getlist(map);
	}

	@Override
	public int total() {
		// TODO Auto-generated method stub
		return indexPicManage.total();
	}

	@Override
	public int updateShow(IndexPic indexPic) {
		// TODO Auto-generated method stub
		return indexPicManage.updateShow(indexPic);
	}

	@Override
	public int updateTitle(IndexPic indexPic) {
		// TODO Auto-generated method stub
		return indexPicManage.updateTitle(indexPic);
	}
	


	

}
