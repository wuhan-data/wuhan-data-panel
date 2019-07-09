package com.wuhan_data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.ErrorLogMapper;
import com.wuhan_data.pojo.ErrorLog;
import com.wuhan_data.service.ErrorLogService;


@Service
public class ErrorLogServiceImpl implements ErrorLogService{
	
	@Autowired
	ErrorLogMapper errorLogMapper;

	@Override
	public int add(ErrorLog errorLog) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ErrorLog get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(ErrorLog errorLog) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ErrorLog> list() {
		// TODO Auto-generated method stub
		return errorLogMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
