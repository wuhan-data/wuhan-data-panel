package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.IndiCorrelativeMapper;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.Page;
import com.wuhan_data.service.IndiCorrelativeService;


@Service
public class IndiCorrelativeServiceImpl implements IndiCorrelativeService{

	@Autowired
	IndiCorrelativeMapper indiCorrelativeMapper;
	@Override
	public int add(IndiCorrelative indiCorrelative) {
		indiCorrelativeMapper.add(indiCorrelative);
		return 0;
	}

	@Override
	public void delete(int id) {
		indiCorrelativeMapper.delete(id);
		
	}

	@Override
	public IndiCorrelative get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(IndiCorrelative indiCorrelative) {
		indiCorrelativeMapper.update(indiCorrelative);
		return 0;
	}

	@Override
	public List<IndiCorrelative> list() {
		// TODO Auto-generated method stub
		return indiCorrelativeMapper.list();
	}

	@Override
	public int total() {
		// TODO Auto-generated method stub
		return indiCorrelativeMapper.total();
	}

	@Override
	public List<IndiCorrelative> list(Page page) {
		// TODO Auto-generated method stub
		return indiCorrelativeMapper.list(page);
	}

	@Override
	public List<IndiCorrelative> listAddPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return indiCorrelativeMapper.listAddPage(map);
	}

	@Override
	public String searchIndiNameById(String addCorrelativeIndiCode) {
		// TODO Auto-generated method stub
		return indiCorrelativeMapper.searchIndiNameById(addCorrelativeIndiCode);
	}

	@Override
	public void per_show_correIndi(int id) {
		// TODO Auto-generated method stub
		indiCorrelativeMapper.per_show_correIndi(id);
		
	}

	@Override
	public void no_per_show_correIndi(int id) {
		// TODO Auto-generated method stub
		indiCorrelativeMapper.no_per_show_correIndi(id);
	}

}
