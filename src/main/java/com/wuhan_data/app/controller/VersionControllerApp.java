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
import org.w3c.dom.ls.LSException;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;
import com.wuhan_data.app.service.CollectServiceApp;
import com.wuhan_data.app.service.VersionServiceApp;
import com.wuhan_data.pojo.Version;
import com.wuhan_data.tools.SessionApp;

@Controller
@RequestMapping("")
public class VersionControllerApp {
	@Autowired
	VersionServiceApp  versionServiceApp;
	//获取最近版本信息接口
	@RequestMapping(value="getVersionApp",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String getVersion(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		//int uid=Integer.valueOf(request.getParameter("id"));
		Map map=new HashMap();
		map.put("platform", "IOS");
		List<Version> versions=versionServiceApp.versionDetection(map);
		Map mapAnd=new HashMap();
		mapAnd.put("platform", "Android");
		List<Version> versionList=versionServiceApp.versionDetection(mapAnd);
		if (versions.size()==0||versionList.size()==0)
		{
			mapReturn.put("errCode","-1");
			mapReturn.put("errMsg","最新版本信息获取失败");
		}
		else {
			Version iosVersion=versions.get(0);
			Version androidVersion=versionList.get(0);
			mapReturn.put("errCode","0");
			mapReturn.put("errMsg","最新版本信息获取成功");
			List list1 = new ArrayList();
			Map map1 = new HashMap();
			map1.put("appid", iosVersion.getAppid());
			map1.put("version", iosVersion.getVersion());
			map1.put("description",iosVersion.getText());
			map1.put("url", iosVersion.getUrl());
			list1.add(map1);
			List list2 = new ArrayList();
			Map map2 = new HashMap();
			map2.put("appid", androidVersion.getAppid());
			map2.put("version", androidVersion.getVersion());
			map2.put("description",androidVersion.getText());
			map2.put("url", androidVersion.getUrl());
			list2.add(map2);
			Map map3 = new HashMap();
			map3.put("IOS", list1);
			map3.put("Android", list2);
			//mapReturn.put("data", map3);
			mapReturn.put("IOS", map1);
			mapReturn.put("Android", map2);
		}
		String param=JSON.toJSONString(mapReturn);
		System.out.println("最新版本信息接口："+param);
		return param;
	}
	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("errCode", errCode);
		responseMap.put("errMsg", errMsg);
		responseMap.put("data", data);
		System.out.println(JSON.toJSONString(responseMap));
		return JSON.toJSONString(responseMap);
	}
//	@RequestMapping(value="checkVersion",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
//	@ResponseBody
//	public String checkVersion(HttpServletRequest request, 
//            HttpServletResponse response) throws Exception
//	{
//		Map mapReturn=new HashMap();
//		//int uid=Integer.valueOf(request.getParameter("id"));
////		String appid=request.getParameter("appid");
////		String version=request.getParameter("version");
//		String appid="20";
//		String version="852";
//		Map map=new HashMap();
//		map.put("appid", appid);
//		List<Version> versions=versionServiceApp.versionDetection(map);
//		Version newVersion=versions.get(0);
//		if (newVersion.getVersion().trim().equals(version.trim()))
//		{
//			mapReturn.put("code",0);
//			mapReturn.put("msg","本应用已经是最新");
//			mapReturn.put("data",null);
//			
//		}
//		else {
//			mapReturn.put("code", 1);
//			mapReturn.put("msg","有待更新的版本");
//			List list=new ArrayList();
//			Map map1=new HashMap();
//			map1.put("text", newVersion.getText());
//			map1.put("url", newVersion.getUrl());
//			list.add(map1);
//			mapReturn.put("data", list);
//		}
//		String param=JSON.toJSONString(mapReturn);
//		return param;
//	}

}
