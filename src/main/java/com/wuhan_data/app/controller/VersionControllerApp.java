package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;
import com.wuhan_data.app.service.CollectServiceApp;
import com.wuhan_data.app.service.VersionServiceApp;
import com.wuhan_data.pojo.Version;

@Controller
@RequestMapping("")
public class VersionControllerApp {
	@Autowired
	VersionServiceApp  versionServiceApp;
	
	
	
	@RequestMapping(value="getVersion",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String getVersion(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		//int uid=Integer.valueOf(request.getParameter("id"));
		int id=1;
		Version version=versionServiceApp.get(id);
		
		mapReturn.put("code","1");
		mapReturn.put("msg","收藏获取成功");
		mapReturn.put("data",version);
		
		
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	@RequestMapping(value="checkVersion",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String checkVersion(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		//int uid=Integer.valueOf(request.getParameter("id"));
//		String appid=request.getParameter("appid");
//		String version=request.getParameter("version");
		String appid="20";
		String version="852";
		Map map=new HashMap();
		map.put("appid", appid);
		List<Version> versions=versionServiceApp.versionDetection(map);
		Version newVersion=versions.get(0);
		if (newVersion.getVersion().trim().equals(version.trim()))
		{
			mapReturn.put("code",0);
			mapReturn.put("msg","本应用已经是最新");
			mapReturn.put("data",null);
			
		}
		else {
			mapReturn.put("code", 1);
			mapReturn.put("msg","有待更新的版本");
			List list=new ArrayList();
			Map map1=new HashMap();
			map1.put("text", newVersion.getText());
			map1.put("url", newVersion.getUrl());
			list.add(map1);
			mapReturn.put("data", list);
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}

}
