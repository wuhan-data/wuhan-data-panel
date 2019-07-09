package com.wuhan_data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.ErrorLog;
import com.wuhan_data.service.ErrorLogService;


@Controller
@RequestMapping("")
public class ErrorLogController {
	
	@Autowired
	ErrorLogService errorLogService;
 
    @RequestMapping("listErrorLog")
    public ModelAndView listHistorySearch(){
        ModelAndView mav = new ModelAndView();
        List<ErrorLog> ErrorLogList= errorLogService.list();
         
        // 
        mav.addObject("ErrorLogList", ErrorLogList);
        // 
        mav.setViewName("listErrorLog");
        return mav;
    }

}
