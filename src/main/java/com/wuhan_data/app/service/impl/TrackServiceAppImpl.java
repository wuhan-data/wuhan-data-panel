package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.TrackMapperApp;
import com.wuhan_data.app.service.TrackServiceApp;
import com.wuhan_data.pojo.Track;

@Service
public class TrackServiceAppImpl implements TrackServiceApp {

	@Autowired
	TrackMapperApp trackMapper;
	@Override
	public int add(Track track) {
		// TODO Auto-generated method stub
		return trackMapper.add(track);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		trackMapper.delete(id);
	}

	@Override
	public Track get(int id) {
		// TODO Auto-generated method stub
		return trackMapper.get(id);
	}

	@Override
	public int update(Track track) {
		// TODO Auto-generated method stub
		return trackMapper.update(track);
	}

	@Override
	public List<Track> list() {
		// TODO Auto-generated method stub
		return trackMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return trackMapper.count();
	}

	@Override
	public List<Track> getByUid(int uid) {
		// TODO Auto-generated method stub
		return trackMapper.getByUid(uid);
	}

}
