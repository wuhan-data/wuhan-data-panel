package com.wuhan_data.app.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.VersionMapperApp;
import com.wuhan_data.app.service.VersionServiceApp;

import com.wuhan_data.pojo.Version;
@Service
public class VersionServiceAppImpl implements VersionServiceApp {

	@Autowired
	VersionMapperApp versionMapper;
	@Override
	public int add(Version version) {
		// TODO Auto-generated method stub
		return versionMapper.add(version);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		versionMapper.delete(id);

	}

	@Override
	public Version get(int id) {
		// TODO Auto-generated method stub
		return versionMapper.get(id);
	}

	@Override
	public int update(Version version) {
		// TODO Auto-generated method stub
		return versionMapper.update(version);
	}

	@Override
	public java.util.List<Version> List() {
		// TODO Auto-generated method stub
		return versionMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return versionMapper.count();
	}

	@Override
	public java.util.List<Version> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return versionMapper.listByPage(parameter);
	}

	@Override
	public java.util.List<Version> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return versionMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return versionMapper.searchCount(parameter);
	}

	@Override
	public java.util.List<Version> versionDetection(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return versionMapper.versionDetection(parameter);
	}

}
