package com.wuhan_data.app.controller;

//import java.sql.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.InitColumnService;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.Collect;
import com.wuhan_data.pojo.User;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class InitColumnController {
	
	@Autowired
	InitColumnService initColumnService;
	
	@RequestMapping(value="initColumn",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String initColumn(){
		Map map = new HashMap();
		map.put("errCode", "0");
		map.put("errMsg", "success");
		List list = new ArrayList();
		List<AnalysisManage> oneList = initColumnService.getOnelist();		
		for(int i=0;i<oneList.size();i++) {
			Map map1 = new HashMap();
			map1.put("id", oneList.get(i).getWeight());
			map1.put("name", oneList.get(i).getType_name());
			List<AnalysisManage> twoList = initColumnService.getTwolist(oneList.get(i).getWeight());
			map1.put("subList", twoList);
			list.add(map1);
		}
		Map map2 = new HashMap();
		map2.put("list", list);
		map.put("data", map2);
        String  param= JSON.toJSONString(map);
        
        
        return param;
    }
	@RequestMapping(value="initColumn/collection",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
	@ResponseBody
	public String collection(@RequestBody String json,HttpServletRequest request) {
//		String theme_name="工业应收账款增速";//从app中获取
		JSONObject jsonObject=JSONObject.fromObject(json);
		AnalysisManage analysisManage=(AnalysisManage)JSONObject.toBean(jsonObject, AnalysisManage.class);
		String param;
		int index_id=analysisManage.getId();
//		int index_id=5;//从前端获取
		String isFavorite=initColumnService.getFavorite(index_id);
		HttpSession session=request.getSession();
		Admin user=(Admin)session.getAttribute("user");
		int uid=user.getId();
//		int uid=2;//从session中获得
		String type="栏目数据";
		Date create_time=new Date();
		Collect collect=new Collect();
		collect.setUid(uid);
		collect.setCreate_time(create_time);
		collect.setIndex_id(index_id);
		collect.setType(type);
		System.out.println("uid"+collect.getUid());
		System.out.println("create_time"+collect.getCreate_time());
		System.out.println("index_id"+collect.getIndex_id());
		System.out.println("type"+collect.getType());
//		Map map=new HashMap();
//		map.put("theme_name",theme_name);
//		map.put("isFavorite",isFavorite);
//		System.out.println("theme_name"+theme_name);
		System.out.println("isFavorite"+isFavorite);
		if(isFavorite.equals("false")) {
		    initColumnService.setFavorite(index_id);
		    
		    initColumnService.colCollect(collect);
		    Map map=new HashMap();
		    map.put("收藏指标：", "收藏指标成功！");
		   param= JSON.toJSONString(map);
		   System.out.println("i am here");
//		    return param;
		}
		else
		{
			initColumnService.setNotFavorite(index_id);
			
			
			initColumnService.colDelete(index_id);
			Map map=new HashMap();
		    map.put("删除指标：", "删除指标成功！");
		    param= JSON.toJSONString(map);
		    System.out.println("what");
//		    return param;
			
		}
		System.out.println("param"+param);
		return param;
		
    	
    }
	
}
