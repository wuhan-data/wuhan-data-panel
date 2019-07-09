package com.wuhan_data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.PanelUser;
import com.wuhan_data.service.PanelUserService;

@Controller
@RequestMapping("")
public class PanelUserController {
	
	@Autowired
    PanelUserService panelUserService;
 
    @RequestMapping("listPanelUser")
    public ModelAndView listPanelUser(){
        ModelAndView mav = new ModelAndView();
        List<PanelUser> panelUserList= panelUserService.list();
    	
    	
        // 
        mav.addObject("panelUserList", panelUserList);
        // 
        mav.setViewName("listPanelUser");
        return mav;
    	
    }
}



