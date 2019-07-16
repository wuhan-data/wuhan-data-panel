package com.wuhan_data.app.service;

import java.util.List;

import com.wuhan_data.pojo.Feedback;

public interface FeedbackServiceApp {

	public int add(Feedback feedback);

	public void delete(int id);

	public Feedback get(int id);

	public int update(Feedback feedback);

	public List<Feedback> list();
	
	public int count();
	public List<Feedback> getByUid(int uid);
}
