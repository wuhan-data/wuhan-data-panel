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
import com.wuhan_data.app.service.CollectServiceApp;
import com.wuhan_data.pojo.Collect;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class CollectControllerApp {
	@Autowired
	CollectServiceApp  collectServiceApp;
	
	
	@RequestMapping(value="setCollectApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String setCollect(HttpServletRequest request, 
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
			mapReturn.put("errCode", "2");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
	  	else {
	  		Map map=(HashMap)session.getAttribute(tokenString);
	  		int uid=Integer.valueOf((String)map.get("userId"));
	  		//int uid=0;
//			String type ="指标数据";
//			String index_id="150";
//			String indi_source="国统";
//			Date create_time=new Date();
			Collect collect=new Collect();
			collect.setUid(uid);
			collect.setType(typeString);
			collect.setIndex_id(indexIdString);
			collect.setIndi_source(sourceString);
			collect.setCreate_time(new Date());
			if (collectServiceApp.add(collect)!=0)
			{
				mapReturn.put("errCode","0");
				mapReturn.put("errMsg","收藏成功");
			}
			else {
				mapReturn.put("errCode","1");
				mapReturn.put("errMsg","收藏失败");
				
			}
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	
	// 收藏页get这个有点问题就是我给不出indexName，只有id，可以根据id找到indexName
	@RequestMapping(value="getCollectApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String getCollect(HttpServletRequest request, 
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
	  		Map map=(HashMap)session.getAttribute(tokenString);
	  		int uid=Integer.valueOf((String)map.get("userId"));
	  		List<Collect> collects=collectServiceApp.getByUid(uid);
	  		List list1=new ArrayList();
			List list2=new ArrayList();
			
			for(int i=0;i<collects.size();i++)
			{
				Map map1=new HashMap();
				Collect collect=collects.get(i);
				if (collect.getType().trim().equals("指标数据"))
				{
					map1.put("type", "指标数据");
					map1.put("collectId", collect.getId().toString());
					map1.put("indexId",collect.getIndex_id());
					map1.put("source", collect.getIndi_source());
					//时间数据格式化
					Date create = collect.getCreate_time();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String createTime = formatter.format(create);
					map1.put("createTime", createTime);
					list1.add(map1);
				}
				else {
					map1.put("type", "经济分析");
					map1.put("collectId", collect.getId().toString());
					map1.put("indexId",collect.getIndex_id());
					map1.put("source", collect.getIndi_source());
					//时间数据格式化
					Date create = collect.getCreate_time();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String createTime = formatter.format(create);
					map1.put("createTime", createTime);
					list1.add(map1);
				}
			}
			mapReturn.put("errCode","0");
			mapReturn.put("errMsg","获取收藏成功");//传的数据格式不对
			Map map2=new HashMap();
			map2.put("economyData", list2);
			map2.put("indexData", list1);
			mapReturn.put("data",map2);

		}
		String param=JSON.toJSONString(mapReturn);
		System.out.println("get收藏接口:"+param);
		return param;
	}
	

}
