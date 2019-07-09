package com.wuhan_data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.NoticeMapper;
import com.wuhan_data.pojo.Notice;
import com.wuhan_data.service.NoticeService;
@Service
public class NoticeServicelmpl implements NoticeService {

	@Autowired
	NoticeMapper noticeMapper;
	@Override
	public int add(Notice notice) {
		// TODO Auto-generated method stub
		return noticeMapper.add(notice);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		noticeMapper.delete(id);
	}

	@Override
	public Notice get(int id) {
		// TODO Auto-generated method stub
		return noticeMapper.get(id);
	}

	@Override
	public int update(Notice notice) {
		// TODO Auto-generated method stub
		return noticeMapper.update(notice);
	}

	@Override
	public List<Notice> list() {
		// TODO Auto-generated method stub
		return noticeMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return noticeMapper.count();
	}

	@Override
	public List<Notice> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return noticeMapper.listByPage(parameter);
	}

	@Override
	public int addByRole(List<Notice> notice) {
		// TODO Auto-generated method stub
		return noticeMapper.addByRole(notice);
	}

	@Override
	public List<Notice> searchByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return noticeMapper.searchByContent(parameter);
	}

	@Override
	public int searchCountByContent(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return noticeMapper.searchCountByContent(parameter);
	}

}
