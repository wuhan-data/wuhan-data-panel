package com.wuhan_data.service.impl;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.SysLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhan_data.mapper.SysLogMapper;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	SysLogMapper sysLogMapper;
	
	@Override
	public int add(SysLog sysLog) {
		// TODO Auto-generated method stub
		return sysLogMapper.add(sysLog);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		sysLogMapper.delete(id);
	}

	@Override
	public SysLog get(int id) {
		// TODO Auto-generated method stub
		return sysLogMapper.get(id);
	}

	@Override
	public int update(SysLog sysLog) {
		// TODO Auto-generated method stub
		return sysLogMapper.update(sysLog);
	}

	@Override
	public List<SysLog> list() {
		// TODO Auto-generated method stub
		return sysLogMapper.list();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return sysLogMapper.count();
	}

	@Override
	public List<SysLog> listByPage(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return sysLogMapper.listByPage(parameter);
	}

	@Override
	public List<SysLog> search(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return sysLogMapper.search(parameter);
	}

	@Override
	public int searchCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return sysLogMapper.searchCount(parameter);
	}

	@Override
	public int addUser(HttpServletRequest request, String e_interface, String e_msg,Exception ex) {
		// TODO Auto-generated method stub
		try {
			SysLog sysLog=new SysLog();
			sysLog.setOperate_user_id(0);
			sysLog.setE_type("user");
			sysLog.setE_interface(e_interface);
			Map<String,Object> map = new HashMap<String,Object>();  
	        Enumeration paramNames = request.getParameterNames();  
	        while (paramNames.hasMoreElements()) {  
	             String paramName = (String) paramNames.nextElement();  

	             String[] paramValues = request.getParameterValues(paramName);  
	             if (paramValues.length >0) {  
	                 String paramValue = paramValues[0];  
	                 if (paramValue.length() != 0) {  
	                     map.put(paramName, paramValue);  
	                 }  
	             }  
	         }  
			String e_parameter=map.toString();
			sysLog.setE_parameter(e_parameter);
			sysLog.setE_msg(e_msg);
			String eString="";
			for(int i=0;i<ex.getStackTrace().length;i++)
			{
				eString=eString+ex.getStackTrace()[i].toString();
			}
			sysLog.setE_error(eString);
			System.out.println(eString);
			sysLog.setCreate_time(new Date());
			int a=sysLogMapper.add(sysLog);
			return a;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("用户日志添加出现错误");
			e.printStackTrace();
		}
		return 0;
		
	}

	@Override
	public int addAdmin(HttpServletRequest request, String e_interface, String e_msg,Exception ex) {
		// TODO Auto-generated method stub
		try {
			SysLog sysLog=new SysLog();
			sysLog.setOperate_user_id(0);
			sysLog.setE_type("admin");
			sysLog.setE_interface(e_interface);
			
			
//			Map map1=request.getParameterMap();  
//		    Set keSet=map1.entrySet();  
//		    for(Iterator itr=keSet.iterator();itr.hasNext();){  
//		        Map.Entry me=(Map.Entry)itr.next();  
//		        Object ok=me.getKey();  
//		        Object ov=me.getValue();  
//		        String[] value=new String[1];  
//		        if(ov instanceof String[]){  
//		            value=(String[])ov;  
//		        }else{  
//		            value[0]=ov.toString();  
//		        }  
//		  
//		        for(int k=0;k<value.length;k++){  
//		            System.out.println(ok+"="+value[k]);  
//		        }  
//		      }
			
			
			
			
			
			Map<String,Object> map = new HashMap<String,Object>();  
	        Enumeration paramNames = request.getParameterNames();  
	        while (paramNames.hasMoreElements()) {  
	             String paramName = (String) paramNames.nextElement();  

	             String[] paramValues = request.getParameterValues(paramName);  
	             if (paramValues.length >0) {  
	                 String paramValue = paramValues[0];  
	                 if (paramValue.length() != 0) {  
	                     map.put(paramName, paramValue);  
	                 }  
	             }  
	         }  
			String e_parameter=map.toString();
			sysLog.setE_parameter(e_parameter);
			sysLog.setE_msg(e_msg);
			String eString="";
			for(int i=0;i<ex.getStackTrace().length;i++)
			{
				eString=eString+ex.getStackTrace()[i].toString();
			}
			sysLog.setE_error(eString);
			sysLog.setCreate_time(new Date());
			int a=sysLogMapper.add(sysLog);
			return a;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("管理员日志添加出现错误");
			e.printStackTrace();
		}
		return 0;
	}

}
