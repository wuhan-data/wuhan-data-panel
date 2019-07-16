package com.wuhan_data.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.app.mapper.FeedbackMapperApp;
import com.wuhan_data.app.service.FeedbackServiceApp;
import com.wuhan_data.pojo.Feedback;

@Service
public class FeedbackServiceAppImpl implements FeedbackServiceApp {
	@Autowired
	FeedbackMapperApp feedbackMapperApp;

	@Override
	public int add(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackMapperApp.add(feedback);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

		feedbackMapperApp.delete(id);
	}

	@Override
	public Feedback get(int id) {
		// TODO Auto-generated method stub
		return feedbackMapperApp.get(id);
	}

	@Override
	public int update(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackMapperApp.update(feedback);
	}

	@Override
	public List<Feedback> list() {
		// TODO Auto-generated method stub
		return feedbackMapperApp.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return feedbackMapperApp.count();
	}

	@Override
	public List<Feedback> getByUid(int uid) {
		// TODO Auto-generated method stub
		return feedbackMapperApp.getByUid(uid);
	}

}
