package com.wuhan_data.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.InitColumnService;
import com.wuhan_data.pojo.AnalysisManage;

@Controller
@RequestMapping("")
public class InitColumnController {
	
	@Autowired
	InitColumnService initColumnService;
	
	@RequestMapping(value="initColumn",produces = "text/plain;charset=utf-8")
	@ResponseBody
    public String initColumn(){
		List<AnalysisManage> oneList = initColumnService.getOnelist();
		List<AnalysisManage> twoList = initColumnService.getTwolist();
		List list = new ArrayList();
		list.add(oneList);
		List list1 = new ArrayList();
		list.add(twoList);		
        String  param= JSON.toJSONString(list);
        return param;
    }
	
}
