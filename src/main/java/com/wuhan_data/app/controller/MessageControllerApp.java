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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.api.model.MEP;
import com.wuhan_data.app.service.MessageServiceApp;
import com.wuhan_data.app.service.NoticeServiceApp;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.pojo.Message;
import com.wuhan_data.pojo.Notice;
import com.wuhan_data.tools.SendMessage;
import com.wuhan_data.tools.SessionApp;
import com.wuhan_data.tools.StringToMap;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class MessageControllerApp {
	
	@Autowired
	MessageServiceApp messageServiceApp;
	@Autowired
	NoticeServiceApp noticeServiceApp;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	
	//消息页 get
	@RequestMapping(value="getMessageApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String getMessage(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{

		Map mapReturn=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	String tokenString=mapget.get("token").toString();
	  	if(sessionSQLServiceApp.get(tokenString)==null)
	  	{
	  		mapReturn.put("errCode", "2");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
	  	else {
	  		String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
	  		Map map1=StringToMap.stringToMap(mapString);
	  		int receiver_id=Integer.valueOf((String)map1.get("userId"));
			List<Message> messages=new ArrayList<Message>();
			List<Notice> notices=new ArrayList<Notice>();
			messages=messageServiceApp.getByRid(receiver_id);
			notices=noticeServiceApp.getByRid(receiver_id);
			mapReturn.put("errCode", "0");
			mapReturn.put("errMsg","获得消息成功");
			List list=new ArrayList();
			for (int i=0;i<messages.size();i++)
			{
				Message message=messages.get(i);
				Map map =new HashMap();
				String idString=String.valueOf(message.getId());
				String title=message.getTitle();
				String label="消息通知";
				String remarks=message.getRemarks();
				Date create_time=message.getCreate_time();
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss"); 
				String dateTime=formatter.format(create_time);
				map.put("messageId",idString);
				map.put("title", title);
				map.put("label",label);
				map.put("dateTime",dateTime);
				map.put("content", remarks);
				list.add(map);
			}
			for (int i=0;i<notices.size();i++)
			{
				Notice notice=notices.get(i);
				Map map =new HashMap();
				String idString=String.valueOf(notice.getId());
				String title=notice.getTitle();
				String label="系统推送";
				String remarks=notice.getRemarks();
				Date create_time=notice.getCreate_time();
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss"); 
				String dateTime=formatter.format(create_time);
				map.put("messageId",idString);
				map.put("title", title);
				map.put("label",label);
				map.put("dateTime",dateTime);
				map.put("content", remarks);
				list.add(map);
			}
			Map map2 =new HashMap();
			map2.put("message", list);
			mapReturn.put("data", map2);
		}
		String param=JSON.toJSONString(mapReturn);
		System.out.println("get消息接口"+param);
		return param;

	}
	

}
