package com.wuhan_data.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.LoginAppMapper;
import com.wuhan_data.app.service.LoginAppService;
@Service
public class LoginAppServiceImpl implements LoginAppService{
	
	@Autowired
	LoginAppMapper loginAppMapper;

	@Override
	public String getPwByTel(String username) {
		// TODO Auto-generated method stub
		return loginAppMapper.getPwByTel(username);
	}

}
