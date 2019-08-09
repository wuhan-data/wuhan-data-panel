package com.wuhan_data.app.service;

import java.util.Date;
import java.util.List;
import com.wuhan_data.pojo.SessionSQL;

public interface SessionSQLServiceApp {
	public int add(SessionSQL sessionSQL);
	public void delete(String sess_key);
	public SessionSQL get(String sess_key);
	public int update(SessionSQL sessionSQL);
	public boolean isKeyExist(String sess_key);
	public void set(String sess_key,String sess_value);
	public boolean isTimeOut(String sess_key,int second);
	public int deleteTimeoutToken(Date timeout);
}
