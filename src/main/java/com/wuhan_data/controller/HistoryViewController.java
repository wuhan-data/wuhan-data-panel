package com.wuhan_data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.HistoryView;
import com.wuhan_data.service.HistoryViewService;

@Controller
@RequestMapping("")
public class HistoryViewController {
	
	@Autowired
	HistoryViewService historyViewService;
 
    @RequestMapping("listHistoryView")
    public ModelAndView listHistoryView(){
        ModelAndView mav = new ModelAndView();
        List<HistoryView> historyViewList= historyViewService.list();
         
        // 
        mav.addObject("historyViewList", historyViewList);
        // 
        mav.setViewName("listHistoryView");
        return mav;
    }

}
