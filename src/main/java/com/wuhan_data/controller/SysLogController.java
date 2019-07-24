package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.tools.Page;
@Controller
@RequestMapping("")
public class SysLogController {
	@Autowired
	SysLogService sysLogService;
	private String operate_user_name="";
	@RequestMapping("sysLogInit")
	public ModelAndView departmentInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=sysLogService.count();
    	
    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<SysLog> sysLogListByPage=sysLogService.listByPage(map);
        
        maView.addObject("sysLogListByPage", sysLogListByPage);
        maView.addObject("controlURL", "sysLogListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("sysLog");
    	return maView;
    }
	@RequestMapping("sysLogListByPage")
	public ModelAndView  departmentSelectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=sysLogService.count();
    	
    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
        String currentPage=request.getParameter("currentPage");
        Pattern pattern = Pattern.compile("[0-9]{1,9}");
        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
            page.setCurrentPage(1);
        } else {
            page.setCurrentPage(Integer.valueOf(currentPage));
        }
        page.setTotalNumber(count);
        page.count();
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
        map.put("page", page);
        List<SysLog> sysLogListByPage=sysLogService.listByPage(map);
        
        
        maView.addObject("sysLogListByPage", sysLogListByPage);
        maView.addObject("controlURL", "sysLogListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("sysLog");
    	return maView;
	}
	@RequestMapping("sysLogSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	operate_user_name = java.net.URLDecoder.decode(request.getParameter(" operate_user_name"),"UTF-8");

//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("operate_user_name", operate_user_name);
           int count = sysLogService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
           System.out.println("count:"+count);
           Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
           String currentPage=request.getParameter("currentPage");
           Pattern pattern = Pattern.compile("[0-9]{1,9}");
           if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
               page.setCurrentPage(1);
           } else {
               page.setCurrentPage(Integer.valueOf(currentPage));
           }
           page.setTotalNumber(count);
           page.count();
           map.put("page", page);
           map.put("operate_user_name",operate_user_name);
           List<SysLog> sysLogListByPage= sysLogService.search(map);//分页查询二极栏目
          
           maView.addObject("sysLogListByPage", sysLogListByPage);
           maView.addObject("controlURL", "sysLogSearchPage");//控制页码传递URL
           maView.addObject("page", page); 
           maView.setViewName("sysLog");        
           return maView;
    }
	@RequestMapping("sysLogSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("operate_user_name", operate_user_name);
           int count = sysLogService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
           //System.out.println("operate:"+operate_user_name);
           System.out.println("count:"+count);
           Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数       
           String currentPage=request.getParameter("currentPage");
           Pattern pattern = Pattern.compile("[0-9]{1,9}");
           if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
               page.setCurrentPage(1);
           } else {
               page.setCurrentPage(Integer.valueOf(currentPage));
           }
           page.setTotalNumber(count);
           page.count();
           map.put("page", page);
           map.put("operate_user_name",operate_user_name);
           List<SysLog> sysLogListByPage= sysLogService.search(map);//分页查询二极栏目
           mav.addObject("sysLogListByPage", sysLogListByPage);
           mav.addObject("controlURL", "sysLogSearchPage");//控制页码传递URL
           mav.addObject("page", page); 
           mav.setViewName("sysLog");           
           return mav;
    }

}
