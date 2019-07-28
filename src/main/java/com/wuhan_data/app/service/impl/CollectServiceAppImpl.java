package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.CollectMapperApp;
import com.wuhan_data.app.service.CollectServiceApp;
import com.wuhan_data.pojo.Collect;
@Service
public class CollectServiceAppImpl implements CollectServiceApp{

	@Autowired
	CollectMapperApp collectMapperApp;
	@Override
	public int add(Collect collect) {
		// TODO Auto-generated method stub
		return collectMapperApp.add(collect);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		collectMapperApp.delete(id);
		
	}

	@Override
	public Collect get(int id) {
		// TODO Auto-generated method stub
		return collectMapperApp.get(id);
	}

	@Override
	public int update(Collect collect) {
		// TODO Auto-generated method stub
		return collectMapperApp.update(collect);
	}

	@Override
	public List<Collect> list() {
		// TODO Auto-generated method stub
		return collectMapperApp.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return collectMapperApp.count();
	}

	@Override
	public List<Collect> getByUid(int uid) {
		// TODO Auto-generated method stub
		return collectMapperApp.getByUid(uid);
	}

	@Override
	public int deleteByUidTypeIndex(Collect collect) {
		// TODO Auto-generated method stub
		return collectMapperApp.deleteByUidTypeIndex(collect);
	}

	@Override
	public int IsExist(Collect collect) {
		// TODO Auto-generated method stub
		return collectMapperApp.IsExist(collect);
	}

}
