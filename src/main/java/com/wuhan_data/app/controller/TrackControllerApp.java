package com.wuhan_data.app.controller;

import java.lang.reflect.Method;
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
import com.wuhan_data.app.service.TrackServiceApp;
import com.wuhan_data.pojo.Track;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class TrackControllerApp {
	
	@Autowired
	TrackServiceApp  trackServiceApp;
	//set足迹接口
	@RequestMapping(value="setTrackApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String setTrack(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map mapReturn=new HashMap();
//		int uid=Integer.valueOf(request.getParameter("uid"));
//		String type=request.getParameter("type");
//		int index_id=Integer.valueOf(request.getParameter("index_id"));
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	String tokenString=mapget.get("token").toString();
	  	String typeString=mapget.get("type").toString();
	  	String indexIdString=mapget.get("indexId").toString();
	  	String sourceString=mapget.get("source").toString();
	  	HttpSession session = request.getSession();
	  	if(session.getAttribute(tokenString)==null)
	  	{
			mapReturn.put("errCode","2");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
	  	else {
	  		Map map=(HashMap)session.getAttribute(tokenString);
	  		int uid=Integer.valueOf((String)map.get("userId"));
//	  		int uid=22;
//			String type ="指标数据";
//			String index_id="150";
//			String indi_source="国统";
			Date create_time=new Date();
			Track track=new Track();
			track.setUid(uid);
			track.setType(typeString);
			track.setIndex_id(indexIdString);
			track.setIndi_source(sourceString);
			track.setCreate_time(create_time);
			if (trackServiceApp.add(track)!=0)
			{
				mapReturn.put("errCode","0");
				mapReturn.put("errMsg","足迹记录成功");
			}
			else {
				mapReturn.put("errCode","1");
				mapReturn.put("errMsg","足迹记录失败");
				
			}
		}

		String param=JSON.toJSONString(mapReturn);
		System.out.println("set足迹接口:"+param);
		return param;
	}
	
	
	// get收藏接口
	@RequestMapping(value="getTrackApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String getTrack(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		
		
		
		Map mapReturn=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	String tokenString=mapget.get("token").toString();
	  	HttpSession session = request.getSession();
	  	if(session.getAttribute(tokenString)==null)
	  	{
			mapReturn.put("errCode", "2");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
		else {
			Map map1=(HashMap)session.getAttribute(tokenString);
	  		int uid=Integer.valueOf((String)map1.get("userId"));
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
					map.put("type", "指标数据");
					map.put("trackId", track.getId().toString());
					map.put("source", track.getIndi_source());
					map.put("indexId",track.getIndex_id());
					Date create = track.getCreate_time();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String createTime = formatter.format(create);
					map.put("createTime", createTime);
					list1.add(map);
				}
				else {
					map.put("type", "经济分析");
					map.put("trackId", track.getId().toString());
					map.put("source", track.getIndi_source());
					map.put("indexId",track.getIndex_id());
					Date create = track.getCreate_time();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String createTime = formatter.format(create);
					map.put("createTime", createTime);
					
					list2.add(map);
				}
			}
			mapReturn.put("errCode","0");
			mapReturn.put("errMsg","足迹获取成功");
			Map map2=new HashMap();
			map2.put("economyData", list2);
			map2.put("indexData", list1);
			mapReturn.put("data",map2);//返回的格式不正确
		}

		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	

}