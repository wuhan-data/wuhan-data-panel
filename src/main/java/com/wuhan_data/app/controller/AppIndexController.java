package com.wuhan_data.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.AppIndexService;
import com.wuhan_data.pojo.AnalysisIcon;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;

@Controller
@RequestMapping("")
public class AppIndexController {
	@Autowired
	AppIndexService appIndexService;
	
	//测试
	@RequestMapping(value="t",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
	@ResponseBody
    public String t(@RequestBody String json){
		System.out.println(json);
		Map map = new HashMap();	
		map.put("code", 1);
		map.put("id", 123456);
        String  param= JSON.toJSONString(map);
        return param;
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
		List<AnalysisIcon> indexList = appIndexService.getIconList();
        String  param= JSON.toJSONString(indexList);        
        return param;
    }
	
	
	//首页的专题
	@RequestMapping(value="initIndexSpecial",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String initIndexSpecial(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException{
		
		List<IndexSpecial> indexList = appIndexService.getIndexSpecialList();
        String  param= JSON.toJSONString(indexList); 
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
	

}
