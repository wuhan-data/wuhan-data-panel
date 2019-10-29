package com.wuhan_data.service.impl;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.tool.PrettyPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.UserOpLogMapper;
import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.pojo.UserOpLog;
import com.wuhan_data.service.UserOpLogService;

@Service
public class UserOpLogServiceImpl implements UserOpLogService {

	@Autowired
	UserOpLogMapper userOpLogMapper;
	@Override
	public int add(UserOpLog userOpLog) {
		// TODO Auto-generated method stub
		return userOpLogMapper.add(userOpLog);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return userOpLogMapper.delete(id);
	}

	@Override
	public UserOpLog get(int id) {
		// TODO Auto-generated method stub
		return userOpLogMapper.get(id);
	}

	@Override
	public int update(UserOpLog userOpLog) {
		// TODO Auto-generated method stub
		return userOpLogMapper.update(userOpLog);
	}

	@Override
	public List<UserOpLog> list() {
		// TODO Auto-generated method stub
		return userOpLogMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return userOpLogMapper.count();
	}

	@Override
	public List<UserOpLog> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userOpLogMapper.listByPage(parameter);
	}

	@Override
	public List<UserOpLog> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userOpLogMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return userOpLogMapper.searchCount(parameter);
	}

	@Override
	public int addOp(int user_id, String op_msg, HttpServletRequest request, String op_interface) {
		// TODO Auto-generated method stub
		try {
			UserOpLog userOpLog=new UserOpLog();
			userOpLog.setUser_id(user_id);
			userOpLog.setOp_msg(op_msg);
			userOpLog.setOp_interface(op_interface);
			Map<String,Object> map = new HashMap<String,Object>();  
	        Enumeration paramNames = request.getParameterNames();  
	        System.out.println("nihao"+paramNames.hasMoreElements());
	        while (paramNames.hasMoreElements()) {  
	             String paramName = (String) paramNames.nextElement(); 
	             System.out.println("param"+paramName);

	             String[] paramValues = request.getParameterValues(paramName);  
	             if (paramValues.length >0) {  
	                 String paramValue = paramValues[0];  
	                 if (paramValue.length() != 0) {  
	                     map.put(paramName, paramValue);  
	                 }  
	             }  
	         }  
			String op_parameter=map.toString();
			userOpLog.setOp_parameter(op_parameter);
			userOpLog.setOp_create_time(new Date());
			System.out.println("userOpLog"+userOpLog.toString());
			int a=userOpLogMapper.add(userOpLog);
			return a;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("用户操作日志添加出现错误");
			e.printStackTrace();
		}
		return 0;
	}

}
