package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.wuhan_data.app.service.CollectServiceApp;
import com.wuhan_data.pojo.Collect;

@Controller
@RequestMapping("")
public class CollectControllerApp {
	@Autowired
	CollectServiceApp  collectServiceApp;
	@RequestMapping(value="setCollect",produces="text/plain;charset=utf-8")
	@ResponseBody
	public String setCollect(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
//		int uid=Integer.valueOf(request.getParameter("uid"));
//		String type=request.getParameter("type");
//		int index_id=Integer.valueOf(request.getParameter("index_id"));
		int uid=0;
		String type ="指标数据";
		String index_id="150";
		String indi_source="国统";
		Date create_time=new Date();
		Collect collect=new Collect();
		collect.setUid(uid);
		collect.setType(type);
		collect.setIndex_id(index_id);
		collect.setIndi_source(indi_source);
		collect.setCreate_time(create_time);
		int code=0;
		if (collectServiceApp.add(collect)!=0)
		{
			mapReturn.put("code","1");
			mapReturn.put("msg","收藏成功");
		}
		else {
			mapReturn.put("code","0");
			mapReturn.put("msg","收藏失败");
			
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	
	// 收藏页get
	@RequestMapping(value="getCollect",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String getCollect(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		int uid=Integer.valueOf(request.getParameter("id"));
		List<Collect> collects=collectServiceApp.getByUid(uid);
		List list1=new ArrayList();
		List list2=new ArrayList();
		
		for(int i=0;i<collects.size();i++)
		{
			Map map=new HashMap();
			Collect collect=collects.get(i);
			if (collect.getType().trim().equals("指标数据"))
			{
				map.put("title", collect.getId().toString());
				map.put("label",collect.getIndex_id());
				list1.add(map);
			}
			else {
				map.put("title", collect.getId().toString());
				map.put("label",collect.getIndex_id());
				list2.add(map);
			}
		}
		mapReturn.put("code","1");
		mapReturn.put("msg","收藏获取成功");
		mapReturn.put("economyData",list2);
		mapReturn.put("indexData",list1);
		
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	

}
