package com.wuhan_data.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.FeedbackMapper;
import com.wuhan_data.pojo.Feedback;
import com.wuhan_data.service.FeedbackService;
@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	FeedbackMapper feedbackMapper;

	@Override
	public int add(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackMapper.add(feedback);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		feedbackMapper.delete(id);
	}

	@Override
	public Feedback get(int id) {
		// TODO Auto-generated method stub
		return feedbackMapper.get(id);
	}

	@Override
	public int update(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackMapper.update(feedback);
	}

	@Override
	public java.util.List<Feedback> list() {
		// TODO Auto-generated method stub
		return feedbackMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return feedbackMapper.count();
	}

	@Override
	public java.util.List<Feedback> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return feedbackMapper.listByPage(parameter);
	}

	@Override
	public java.util.List<Feedback> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return feedbackMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return feedbackMapper.searchCount(parameter);
	}

}
