package com.wuhan_data.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.SysLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.SysLogMapper;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	SysLogMapper sysLogMapper;
	
	@Override
	public int add(int operate_user_id,String operate,String method) {
		// TODO Auto-generated method stub
		SysLog sysLog=new SysLog();
		sysLog.setOperate_user_id(operate_user_id);
		sysLog.setOperate(operate);
		sysLog.setMethod(method);
		sysLog.setCreate_time(new Date());
		return sysLogMapper.add(sysLog);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		sysLogMapper.delete(id);
	}

	@Override
	public SysLog get(int id) {
		// TODO Auto-generated method stub
		return sysLogMapper.get(id);
	}

	@Override
	public int update(SysLog sysLog) {
		// TODO Auto-generated method stub
		return sysLogMapper.update(sysLog);
	}

	@Override
	public List<SysLog> list() {
		// TODO Auto-generated method stub
		return sysLogMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return sysLogMapper.count();
	}

	@Override
	public List<SysLog> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return sysLogMapper.listByPage(parameter);
	}

	@Override
	public List<SysLog> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return sysLogMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return sysLogMapper.searchCount(parameter);
	}

}
