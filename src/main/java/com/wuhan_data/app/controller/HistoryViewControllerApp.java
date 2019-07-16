package com.wuhan_data.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.HistoryViewServiceApp;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.HistoryView;

@Controller
@RequestMapping("")
public class HistoryViewControllerApp {
	@Autowired
	HistoryViewServiceApp historyViewServiceApp;
	
	@RequestMapping(value="setHistoryView",produces="text/plain;charset=utf-8")
	@ResponseBody
	public String setHistoryView(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
//		int uid=Integer.valueOf(request.getParameter("uid"));
//		String func_name=request.getParameter("func_name");
//		int index_id=Integer.valueOf(request.getParameter("index_id"));
//		String title=request.getParameter("title");
//		String view_url=request.getParameter("view_url");
		int uid=0;
		String func_name="nihao";
		String title="你好";
		String view_url="mfia";
		Date create_time=new Date();
		HistoryView historyView=new HistoryView();
		historyView.setUid(uid);
		historyView.setFunc_name(func_name);
		historyView.setTitle(title);
		historyView.setView_url(view_url);
		historyView.setCreate_time(create_time);
		if (historyViewServiceApp.add(historyView)!=0)
		{
			mapReturn.put("code","0");
			mapReturn.put("msg","收藏成功");
		}
		else {
			mapReturn.put("code","1");
			mapReturn.put("msg","收藏失败");
			
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	@RequestMapping(value="getHistoryView",produces="text/plain;charset=utf-8")
	@ResponseBody
	public String getHistoryView(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		//int uid=Integer.valueOf(request.getParameter("uid"));
		int uid=0;
		List<HistoryView> historyViews=historyViewServiceApp.getByUid(uid);
		
		mapReturn.put("code","0");
		mapReturn.put("msg","收藏成功");
		mapReturn.put("data",historyViews);
		
		String param=JSON.toJSONString(mapReturn);
		return param;
	}

}
