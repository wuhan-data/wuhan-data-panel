package com.wuhan_data.app.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wuhan_data.app.mapper.SessionSQLMapperApp;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.pojo.SessionSQL;
@Service
public class SessionSQLServiceAppImpl implements SessionSQLServiceApp {
	@Autowired
	SessionSQLMapperApp sessionSQLMapperApp;

	@Override
	public int add(SessionSQL sessionSQL) {
		// TODO Auto-generated method stub
		return sessionSQLMapperApp.add(sessionSQL);
	}

	@Override
	public void delete(String sess_key) {
		// TODO Auto-generated method stub
		sessionSQLMapperApp.delete(sess_key);
	}

	@Override
	public SessionSQL get(String sess_key) {
		// TODO Auto-generated method stub
		return sessionSQLMapperApp.get(sess_key);
	}

	@Override
	public int update(SessionSQL sessionSQL) {
		// TODO Auto-generated method stub
		return sessionSQLMapperApp.update(sessionSQL);
	}

	@Override
	public boolean isKeyExist(String sess_key) {
		// TODO Auto-generated method stub
		if(sessionSQLMapperApp.get(sess_key)!=null)
			return true;
		return false;
	}

	@Override
	public void set(String sess_key, String sess_value) {
		// TODO Auto-generated method stub
		Date date=new Date();
		SessionSQL sessionSQL=new SessionSQL();
		sessionSQL.setSess_key(sess_key);
		sessionSQL.setSess_value(sess_value);
		sessionSQL.setCreate_date(date);
		//判断key是否存在，存在的话就update，不存在的话就add
		if(isKeyExist(sess_key))
		{
			update(sessionSQL);
			}
		else {
			add(sessionSQL);
		}
		
	}

	@Override
	public boolean isTimeOut(String sess_key, int second) {
		// TODO Auto-generated method stub
		
		SessionSQL sessionSQL=get(sess_key);
		//给的key对应可能是空值
		if(sessionSQL==null)
		{
			return true;
		}
		else {
			Date oldTime=sessionSQL.getCreate_date();
			Date newTime=new Date();
			long timeDiff=newTime.getTime()-oldTime.getTime();
			if(timeDiff>second*1000)
				return true;
			return false;
		}
		
	}

	@Override
	public int deleteTimeoutToken(Date timeout) {
		// TODO Auto-generated method stub
		return sessionSQLMapperApp.deleteTimeoutToken(timeout);
	}

}
