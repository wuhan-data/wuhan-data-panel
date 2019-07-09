package com.wuhan_data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Collect;
import com.wuhan_data.service.CollectService;


@Controller
@RequestMapping("")
public class CollectController {
	
	@Autowired
	CollectService collectService;
 
    @RequestMapping("listCollect")
    public ModelAndView listCollect(){
        ModelAndView mav = new ModelAndView();
        List<Collect> collectList= collectService.list();
         
        // 
        mav.addObject("collectList", collectList);
        // 
        mav.setViewName("listCollect");
        return mav;
    }

}
