package com.wuhan_data.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.PanelUserMapper;
import com.wuhan_data.mapper.UserMapper;
import com.wuhan_data.pojo.PanelUser;
import com.wuhan_data.service.PanelUserService;
import com.wuhan_data.service.UserService;


@Service
public class PanelUserServiceImpl implements PanelUserService{
	@Autowired
    PanelUserMapper panelUserMapper;

	@Override
	public int add(PanelUser panelUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PanelUser get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(PanelUser panelUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanelUser> list() {
		// TODO Auto-generated method stub
		return panelUserMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
