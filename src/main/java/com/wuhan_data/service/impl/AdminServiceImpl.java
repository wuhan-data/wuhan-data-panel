package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.AdminMapper;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminMapper adminMapper;

	@Override
	public int add(Admin admin) {
		// TODO Auto-generated method stub
		return adminMapper.add(admin) ;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		adminMapper.delete(id);

	}

	@Override
	public Admin get(int id) {
		// TODO Auto-generated method stub
		return adminMapper.get(id);
	}

	@Override
	public int update(Admin admin) {
		// TODO Auto-generated method stub
		return adminMapper.update(admin);
	}

	@Override
	public List<Admin> list() {
		// TODO Auto-generated method stub
		return adminMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return adminMapper.count();
	}

	@Override
	public List<Admin> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return adminMapper.listByPage(parameter);
	}

	@Override
	public List<Admin> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return adminMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return adminMapper.searchCount(parameter);
	}

	@Override
	public int adminLogin(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return adminMapper.adminLogin(parameter);
	}

	@Override
	public Admin getByName(String username) {
		// TODO Auto-generated method stub
		return adminMapper.getByName(username);
	}

}
