package com.wuhan_data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wuhan_data.service.IndexManageService;

@Controller
@RequestMapping("")
public class IndiSearchController {
	@Autowired
	IndexManageService indexManageService;
	
	@RequestMapping("IndiSearch1")
	public String indiSearch(String SearchKeyWord,Model model) {
		List<String> indiCodeList=null;
	    indiCodeList= indexManageService.IndiSearch(SearchKeyWord);
	    for(int i=0;i<indiCodeList.size();i++)
	    	System.out.println(indiCodeList.get(i));
		return "OK";
	}

}
