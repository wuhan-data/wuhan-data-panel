package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.UserMapper;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    UserMapper userMapper;
     
    public List<User> list(){
        return userMapper.list();
    }

	@Override
	public User logincheck(User user) {
		User u = userMapper.logincheck(user);
		
		return u;
	}

	@Override
	public void regist(User user) {
		userMapper.register(user);
		
	}

@Override
	public int add(User user) {
		return userMapper.add(user);
	}
	@Override
	public void delete(int id) {
		userMapper.delete(id);
	}
	@Override
	public User get(int id) {
		return userMapper.get(id);
	}
	@Override
	public int updata(User user) {
		return userMapper.update(user);
	}
	@Override
	public int count() {
		return userMapper.count();
	}
	
	@Override
	public List<User> search(Map<String,Object> parameter){
		return userMapper.search(parameter);
	}
	@Override
	public int searchCount(Map<String,Object> parameter) {
		return userMapper.searchCount(parameter);
	}
	
	
	@Override
	public List<User> listByPage(Map<String,Object> parameter){
		return userMapper.listByPage(parameter);
		
	}

	@Override
	public User getByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.getByName(username);
	}

	@Override
	public List<User> getByRole(int role_id) {
		// TODO Auto-generated method stub
		return userMapper.getByRole(role_id);
	}
	
	
	
	
	

}
