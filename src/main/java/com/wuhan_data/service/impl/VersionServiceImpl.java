package com.wuhan_data.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.VersionMapper;
import com.wuhan_data.pojo.Version;
import com.wuhan_data.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	VersionMapper versionMapper;
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

}
