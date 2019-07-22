package com.wuhan_data.app.mapper;

import java.util.List;

import com.wuhan_data.pojo.Track;
import com.wuhan_data.tools.DataSource;

public interface TrackMapperApp {
	@DataSource(value="dataSource_mysql")
	public int add(Track track);
	@DataSource(value="dataSource_mysql")
	public void delete(int id);
	@DataSource(value="dataSource_mysql")
	public Track get(int id);
	@DataSource(value="dataSource_mysql")
	public int update(Track track);
	@DataSource(value="dataSource_mysql")
	public List<Track> list();
	@DataSource(value="dataSource_mysql")
	public int count();
	@DataSource(value="dataSource_mysql")
	public List<Track> getByUid(int uid);

}