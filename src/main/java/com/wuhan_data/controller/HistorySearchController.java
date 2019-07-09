package com.wuhan_data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.HistorySearch;
import com.wuhan_data.service.HistorySearchService;

@Controller
@RequestMapping("")
public class HistorySearchController {
	
	@Autowired
	HistorySearchService historySearchService;
	//指标搜索接口对接页面（界面上显示搜索历史接口）
    @RequestMapping("listHistorySearch")
    public String listHistorySearch(Model model){
        
        List<HistorySearch> historySearchList= historySearchService.list();
        
        for(int i=0;i<historySearchList.size();i++)
        	System.out.println(historySearchList.get(i).getUid()+" "+historySearchList.get(i).getKeyword());
         
        // 
        model.addAttribute("historySearchList", historySearchList);
        
        return "index";
    }

}
