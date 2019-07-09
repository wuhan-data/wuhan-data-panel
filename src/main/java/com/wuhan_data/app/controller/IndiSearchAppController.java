package com.wuhan_data.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.IndiSearchService;
import com.wuhan_data.pojo.HistorySearch;


@Controller
@RequestMapping("")
public class IndiSearchAppController {
	
	@Autowired
	IndiSearchService indiSearchService;
	
	@RequestMapping(value="searchsource",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String searchSource(Model model) {
		
		//获得搜索的所有来源（如湖北统计局，国家统计局等）
		List<String> indisourceList=indiSearchService.searchSource();
		
		//获得历史搜素
		int uid=1;//应从session中获取
		List<HistorySearch> historySearchList=indiSearchService.getHistorySearch(uid);
		
		Map map=new HashMap();
		map.put("indisourceList", indisourceList);
		map.put("historySearchList", historySearchList);
		String  param= JSON.toJSONString(map);
		return param;
	}

}
