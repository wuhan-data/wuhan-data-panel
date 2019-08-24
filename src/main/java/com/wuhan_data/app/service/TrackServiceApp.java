package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.Track;

public interface TrackServiceApp {
	public int add(Track track);
	public void delete(int id);
	public Track get(int id);
	public int update(Track track);
	public List<Track> list();
	public int count();
	public List<Track> getByUid(int uid);
	public int isExist(Track track);
	public int updateCreateTime(Track track);

}
