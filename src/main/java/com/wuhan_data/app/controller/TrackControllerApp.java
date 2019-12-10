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
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.app.service.TrackServiceApp;
import com.wuhan_data.pojo.Track;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.tools.StringToMap;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class TrackControllerApp {
	
	@Autowired
	TrackServiceApp  trackServiceApp;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	@Autowired
	SysLogService sysLogService;
	//set足迹接口
	@RequestMapping(value="setTrackApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String setTrack(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map data=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	//获取请求参数
	  	String tokenString="";
	  	String typeString="";
	  	String indexIdString="";
	  	String indexNameString="";
	  	String sourceString="";
	  	String ljString="";
	  	try {
	  		tokenString=mapget.get("token").toString();
		  	typeString=mapget.get("type").toString();
		  	indexIdString=mapget.get("indexId").toString();
		  	indexNameString=mapget.get("indexName").toString();
		  	sourceString=mapget.get("source").toString();
		  	ljString=mapget.get("path").toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("setTrackApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-2", "请求参数异常", data);
		}
	  	//token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("setTrackApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
			return this.apiReturn("-1", "数据库异常", data);
		}  
	  	if(tokenIsEmpty)
	  	{
	  		return this.apiReturn("-3", "token令牌错误", data);
	  	}
	  	else {
	  		//获取设置数据
	  		try {
	  			String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
		  		Map map=StringToMap.stringToMap(mapString);
		  		int uid=Integer.valueOf((String)map.get("userId"));
				Date create_time=new Date();
				Track track=new Track();
				track.setUid(uid);
				track.setType(typeString);
				track.setIndex_id(indexIdString);
				track.setIndex_name(indexNameString);
				track.setIndi_source(sourceString);
				track.setLj(ljString);
				track.setIsarea("");
				track.setCreate_time(create_time);
				//足迹存在
				if (trackServiceApp.isExist(track)!=0)
				{
					trackServiceApp.updateCreateTime(track);
					return this.apiReturn("0", "足迹记录成功", data);
				}
				//足迹不存在
				else {
					if (trackServiceApp.add(track)!=0)
					{
						return this.apiReturn("0", "足迹记录成功", data);
					}
					else {
						return this.apiReturn("-2", "足迹记录失败", data);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("setTrackApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库操作异常", e);
				return this.apiReturn("-1", "数据库操作异常", data);
			}

		}
	}
	
	
	// get收藏接口
	@RequestMapping(value="getTrackApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String getTrack(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map data=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	String tokenString="";
	  	//请求参数获取
	  	try {
	  		tokenString=mapget.get("token").toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getTrackApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-2", "请求参数错误", data);
		}
	  	
	  	//token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getTrackApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
			return this.apiReturn("-1", "数据库异常", data);
		}  

	  	if(tokenIsEmpty)
	  	{
	  		return this.apiReturn("-3", "token令牌错误", data);
	  	}
		else {
			//获取数据
			try {
				String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
		  		Map map1=StringToMap.stringToMap(mapString);
		  		int uid=Integer.valueOf((String)map1.get("userId"));
				List<Track> tracks=trackServiceApp.getByUid(uid);
				List list1=new ArrayList();
				List list2=new ArrayList();
				for(int i=tracks.size()-1;i>=0;i--)
				{
					Map map=new HashMap();
					Track track=tracks.get(i);
					if (track.getType().trim().equals("指标数据"))
					{
						map.put("type", "指标数据");
						map.put("trackId", track.getId().toString());
						//map.put("source", track.getIndi_source());
						
						String source=track.getIndi_source();
						String sourceArea="";
						if (source.equals("湖统"))
						{
							sourceArea="";
						}
						else
						{
							String[] sp=source.split("-");
							sourceArea=sp[1];
							source=sp[0];
						}
						map.put("source", source);
						map.put("sourceArea", sourceArea);
						
						map.put("indexId",track.getIndex_id());
						map.put("indexName",track.getIndex_name());
						map.put("path",track.getLj());
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
						map.put("indexName",track.getIndex_name());
						Date create = track.getCreate_time();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						String createTime = formatter.format(create);
						map.put("createTime", createTime);
						list2.add(map);
					}
				}
				Map map2=new HashMap();
				data.put("economyData", list2);
				data.put("indexData", list1);
				return this.apiReturn("0", "足迹获取成功", data);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("getTrackApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库操作异常", e);
				return this.apiReturn("-1", "数据库操作异常", data);
			}		
		}
	}
	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("errCode", errCode);
		responseMap.put("errMsg", errMsg);
		responseMap.put("data", data);
		System.out.println(JSON.toJSONString(responseMap));
		return JSON.toJSONString(responseMap);
	}
	
}
