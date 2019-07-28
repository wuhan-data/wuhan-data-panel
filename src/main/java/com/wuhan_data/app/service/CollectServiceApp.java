package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.Collect;

public interface CollectServiceApp {

	public int add(Collect collect);
	public void delete(int id);
	public Collect get(int id);
	public int update(Collect collect);
	public List<Collect> list();
	public int count();
	public List<Collect> getByUid(int uid);
	public int deleteByUidTypeIndex(Collect collect);
	public int  IsExist(Collect collect);
}
