package com.wuhan_data.app.controller;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wuhan_data.app.service.AppIndexService;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.pojo.AnalysisIcon;
import com.wuhan_data.pojo.AnalysisType;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.service.UserService;
import com.wuhan_data.tools.StringToMap;

@Controller
@RequestMapping("")
public class AppIndexController {
	@Autowired
	AppIndexService appIndexService;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	@Autowired
	UserService userService;
	
	//测试
	@RequestMapping(value="t",produces = "text/plain;charset=utf-8")
    public ModelAndView t(){
        return new ModelAndView("testApp");
    }
	
	//测试
		@RequestMapping(value="tt",produces = "text/plain;charset=utf-8")
	    public ModelAndView tt(){
			System.out.println("###########");
	        return new ModelAndView("testApp");
	    }
	
	//轮播图
	@RequestMapping(value="initIndexPicture",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String initIndexPicture(){		
		List<IndexPic> indexList = appIndexService.getlist();
        String  param= JSON.toJSONString(indexList);        
        return param;
    }
	
	//经济分析十个icon
	@RequestMapping(value="initIndexIcon",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String initIndexIcon(){		
		List<AnalysisType> indexList = appIndexService.getIconList();
        String  param= JSON.toJSONString(indexList);        
        return param;
    }
	
	
	//首页的专题
	@RequestMapping(value="initIndexTopic",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String initIndexTopic(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException, UnknownHostException{
		Map map = new HashMap();
		map.put("errCode", "0");
		map.put("errMsg", "success");
		String ip = InetAddress.getLocalHost().getHostAddress()+":"+request.getLocalPort();
		List<IndexSpecial> indexList = appIndexService.getIndexSpecialList();
		for(int i=0;i<indexList.size();i++) {
			String hostIP = indexList.get(i).getImage();
			hostIP = hostIP.replace("http://","");//去除http和https前缀
			String [] arr = hostIP.split("/");//按‘/’分隔，取第一个
			hostIP = arr[0];
			indexList.get(i).setImage(indexList.get(i).getImage().replace(hostIP,ip));
		}      
        Map map1 = new HashMap();
        map1.put("topic", indexList);
		map.put("data", map1);
		 String  param= JSON.toJSONString(map); 
        return param;
    }
	
	
	@RequestMapping(value="specialDesc",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String specialDesc(HttpServletRequest request, 
            HttpServletResponse response){		
		int id = Integer.parseInt(request.getParameter("topicId"));
		List<IndexSpecial> indexList = appIndexService.getDesc(id);
		
        String  param= JSON.toJSONString(indexList); 
        return param;
    }
	
	

	//首页
	@RequestMapping(value="initHome",produces = "text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
    public String initHome(@RequestBody String json) throws UnknownHostException{
		Map map = new HashMap();
		map.put("errCode", "0");
		map.put("errMsg", "success");
		
		JSONObject requestObject = JSONObject.parseObject(json);
		  String keyWord = "";
		  String source = "全部";// 国统、湖统、全部
		  Integer userId = 0;
		  String token = "";
		  
		  Map<String, Object> data = new HashMap<String, Object>();
	   try {
	    token = requestObject.containsKey("token") == false ? "" : requestObject.get("token").toString();
	   } catch (Exception e) {
	    return this.apiReturn("-1", "参数获取异常", data);
	   }
	
	   try {
	    if (!token.equals("")) {
	     String mapString = sessionSQLServiceApp.get(token).getSess_value();
	     Map mapS = StringToMap.stringToMap(mapString);
	     userId = Integer.valueOf((String) mapS.get("userId"));
	    }
	   } catch (Exception e) {
	    System.out.println("无效的token令牌");
	   }
	   
	 //获得该用户的权限
//	  Map<String, List<String>> allPower = userService.getAllPower(userId);
//	  Set <String> power_h=new HashSet<String>();
//	  power_h = (Set<String>) allPower.get("powerIndexSpecials");

	  List<IndexSpecial> topic = appIndexService.getIndexSpecialList();
	  
	 
		 
	   
	
		
		List<IndexPic> slideshow = appIndexService.getlist();

		List<AnalysisType> analysis = appIndexService.getIconList();

		

		Map map1 = new HashMap();
		map1.put("slideshow", slideshow);
		map1.put("analysis", analysis);
		map1.put("topic", topic);
		map.put("data", map1);
        String  param= JSON.toJSONString(map);        
        return param;
    }
	
	public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
		  Map<String, Object> responseMap = new HashMap<String, Object>();
		  responseMap.put("errCode", errCode);
		  responseMap.put("errMsg", errMsg);
		  responseMap.put("data", data);
		  return JSON.toJSONString(responseMap, SerializerFeature.DisableCircularReferenceDetect);
		 }

}
