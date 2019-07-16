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
import com.wuhan_data.app.service.TrackServiceApp;
import com.wuhan_data.pojo.Track;

@Controller
@RequestMapping("")
public class TrackControllerApp {
	
	@Autowired
	TrackServiceApp  trackServiceApp;
	@RequestMapping(value="setTrack",produces="text/plain;charset=utf-8")
	@ResponseBody
	public String setTrack(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
//		int uid=Integer.valueOf(request.getParameter("uid"));
//		String type=request.getParameter("type");
//		int index_id=Integer.valueOf(request.getParameter("index_id"));
		int uid=22;
		String type ="指标数据";
		String index_id="150";
		String indi_source="国统";
		Date create_time=new Date();
		Track track=new Track();
		track.setUid(uid);
		track.setType(type);
		track.setIndex_id(index_id);
		track.setIndi_source(indi_source);
		track.setCreate_time(create_time);
		int code=0;
		if (trackServiceApp.add(track)!=0)
		{
			mapReturn.put("code","1");
			mapReturn.put("msg","足迹记录成功");
		}
		else {
			mapReturn.put("code","0");
			mapReturn.put("msg","足迹记录失败");
			
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	
	// 收藏页get
	@RequestMapping(value="getTrack",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String getTrack(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		int uid=Integer.valueOf(request.getParameter("id"));
		//int uid=22;
		List<Track> tracks=trackServiceApp.getByUid(uid);
		List list1=new ArrayList();
		List list2=new ArrayList();
		
		for(int i=0;i<tracks.size();i++)
		{
			Map map=new HashMap();
			Track track=tracks.get(i);
			if (track.getType().trim().equals("指标数据"))
			{
				map.put("title", track.getId().toString());
				map.put("label",track.getIndex_id());
				list1.add(map);
			}
			else {
				map.put("title", track.getId().toString());
				map.put("label",track.getIndex_id());
				list2.add(map);
			}
		}
		mapReturn.put("code","1");
		mapReturn.put("msg","足迹获取成功");
		mapReturn.put("economyData",list2);
		mapReturn.put("indexData",list1);
		
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	

}
