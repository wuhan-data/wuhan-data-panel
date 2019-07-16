package com.wuhan_data.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.api.model.MEP;
import com.wuhan_data.app.service.MessageServiceApp;
import com.wuhan_data.app.service.NoticeServiceApp;
import com.wuhan_data.pojo.Message;
import com.wuhan_data.pojo.Notice;
import com.wuhan_data.tools.SendMessage;

@Controller
@RequestMapping("")
public class MessageControllerApp {
	
	@Autowired
	MessageServiceApp messageServiceApp;
	@Autowired
	NoticeServiceApp noticeServiceApp;
	
	
	//消息页 get
	@RequestMapping(value="getMessage",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String getMessage(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		int receiver_id=Integer.valueOf(request.getParameter("id"));
		//int receiver_id=22;
		List<Message> messages=new ArrayList<Message>();
		List<Notice> notices=new ArrayList<Notice>();
		messages=messageServiceApp.getByRid(receiver_id);
		notices=noticeServiceApp.getByRid(receiver_id);
		mapReturn.put("code", 1);
		mapReturn.put("msg","获得数据");
		List list=new ArrayList();
		for (int i=0;i<messages.size();i++)
		{
			Message message=messages.get(i);
			Map map =new HashMap();
			String title=message.getTitle();
			String label="系统通知";
			String text=message.getRemarks();
			Date create_time=message.getCreate_time();
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd"); 
			String datetime=formatter.format(create_time);
			map.put("title", title);
			map.put("label",label);
			map.put("datetime",datetime);
			map.put("text", text);
			list.add(map);
		}
		for (int i=0;i<notices.size();i++)
		{
			Notice notice=notices.get(i);
			Map map =new HashMap();
			String title=notice.getTitle();
			String label="推送消息";
			String text=notice.getRemarks();
			Date create_time=notice.getCreate_time();
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd"); 
			String datetime=formatter.format(create_time);
			map.put("title", title);
			map.put("label",label);
			map.put("datetime",datetime);
			map.put("text", text);
			list.add(map);
		}
//		mapReturn.put("message",messages);
//		mapReturn.put("notice",notices);
		mapReturn.put("data", list);
		String param=JSON.toJSONString(mapReturn);
		return param;

	}
	

}
