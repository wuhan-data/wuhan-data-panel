package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.RoleMapper;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.service.RoleService;

@Service
public class RoleServicelmpl implements RoleService {
	
	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public int add(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.add(role);
	}
	
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		roleMapper.delete(id);
		
	}

	@Override
	public Role get(int id) {
		// TODO Auto-generated method stub
		return roleMapper.get(id);
	}
	@Override
	public Role getByName(String name) {
		return roleMapper.getByName(name);
	}

	@Override
	public int update(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.update(role);
	}

	@Override
	public java.util.List<Role> List() {
		// TODO Auto-generated method stub
		return roleMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return roleMapper.count();
	}

	@Override
	public java.util.List<Role> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.listByPage(parameter);
	}

	@Override
	public java.util.List<Role> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.searchCount(parameter);
	}

	@Override
	public java.util.List<Role> getByCode(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return roleMapper.getByCode(parameter);
	}




}
