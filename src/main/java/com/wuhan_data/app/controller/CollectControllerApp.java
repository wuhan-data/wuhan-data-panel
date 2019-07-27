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
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.tools.SessionApp;
import com.wuhan_data.tools.StringToMap;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class CollectControllerApp {
	@Autowired
	CollectServiceApp  collectServiceApp;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	
	@RequestMapping(value="setCollectApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String setCollect(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map data=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	//请求参数获取
	  	String tokenString="";
	  	String typeString="";
	  	String indexIdString="";
	  	String indexNameString="";
	  	String sourceString="";
	  	try {
	  		tokenString=mapget.get("token").toString();
		  	typeString=mapget.get("type").toString();
		  	indexIdString=mapget.get("indexId").toString();
		  	indexNameString=mapget.get("indexName").toString();
		  	sourceString=mapget.get("source").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-2", "请求参数错误", data);
		}
	  	
	  //token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return this.apiReturn("-1", "数据库异常", data);
		}  	
	  	if(tokenIsEmpty)
	  	{
	  		return this.apiReturn("-3", "token令牌错误", data);
	  	}
	  	else {
	  		try {
	  			String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
		  		Map map=StringToMap.stringToMap(mapString);
		  		int uid=Integer.valueOf((String)map.get("userId"));
				Collect collect=new Collect();
				collect.setUid(uid);
				collect.setType(typeString);
				collect.setIndex_id(indexIdString);
				collect.setIndex_name(indexNameString);
				collect.setIndi_source(sourceString);
				collect.setCreate_time(new Date());
				if (collectServiceApp.add(collect)!=0)
				{
					return this.apiReturn("0", "收藏成功", data);
				}
				else {
					return this.apiReturn("-2", "收藏失败", data);
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "数据库操作异常", data);
			}		
		}
	}
	
	
	// 收藏页get
	@RequestMapping(value="getCollectApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String getCollect(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map data=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	//获取参数
	  	String tokenString="";
	  	try {
	  		tokenString=mapget.get("token").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-2", "参数请求异常", data);
		}
	  	
	  	 //token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
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
		  		Map map=StringToMap.stringToMap(mapString);
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
						map1.put("indexName",collect.getIndex_name());
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
						map1.put("indexName",collect.getIndex_name());
						map1.put("source", collect.getIndi_source());
						//时间数据格式化
						Date create = collect.getCreate_time();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						String createTime = formatter.format(create);
						map1.put("createTime", createTime);
						list2.add(map1);
					}
				}
				Map map2=new HashMap();
				data.put("economyData", list2);
				data.put("indexData", list1);
				return this.apiReturn("0", "获取收藏成功", data);
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "数据库错误", data);
			}
		}
	}
	 public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("errCode", errCode);
			responseMap.put("errMsg", errMsg);
			responseMap.put("data", data);
			return JSON.toJSONString(responseMap);
		}
	  

}
