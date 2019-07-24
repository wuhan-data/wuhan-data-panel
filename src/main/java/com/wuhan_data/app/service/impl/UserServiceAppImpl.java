package com.wuhan_data.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.UserMapperApp;
import com.wuhan_data.app.service.UserServiceApp;
import com.wuhan_data.pojo.User;

@Service
public class UserServiceAppImpl implements UserServiceApp {
	@Autowired
	UserMapperApp userMapperApp;

	@Override
	public int add(User user) {
		// TODO Auto-generated method stub
		return userMapperApp.add(user);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		userMapperApp.delete(id);

	}

	@Override
	public User get(int id) {
		// TODO Auto-generated method stub
		return userMapperApp.get(id);
	}

	@Override
	public int updata(User user) {
		// TODO Auto-generated method stub
		return userMapperApp.update(user);
	}

	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return userMapperApp.list();
	}

	@Override
	public User getByName(String username) {
		// TODO Auto-generated method stub
		return userMapperApp.getByName(username);
	}

	@Override
	public List<User> getByRole(int role_id) {
		// TODO Auto-generated method stub
		return userMapperApp.getByRole(role_id);
	}

	@Override
	public List<User> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.searchCount(parameter);
	}

	@Override
	public List<User> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.listByPage(parameter);
	}

	@Override
	public User logincheck(User user) {
		// TODO Auto-generated method stub
		return userMapperApp.logincheck(user);
	}


	@Override
	public int count() {
		// TODO Auto-generated method stub
		return userMapperApp.count();
	}

	@Override
	public User logincheckByTel(Map<String,Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.logincheckByTel(parameter);
	}

	@Override
	public int telIsExist(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.telIsExist(parameter);
	}

	@Override
	public User getByTel(String tel) {
		// TODO Auto-generated method stub
		return userMapperApp.getByTel(tel);
	}

	@Override
	public int updatePasswordByTel(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.updatePasswordByTel(parameter);
	}

	@Override
	public int updateTelById(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.updateTelById(parameter);
	}

	@Override
	public int setHeadById(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.setHeadById(parameter);
	}

	@Override
	public int updatePasswordById(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userMapperApp.updatePasswordById(parameter);
	}

}
