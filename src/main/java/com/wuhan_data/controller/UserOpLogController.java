package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.UserOpLog;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.UserOpLogService;
import com.wuhan_data.tools.MenuList;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class UserOpLogController {
	@Autowired
	UserOpLogService userOpLogService;
	@Autowired
	SysLogService sysLogService;
	@Autowired
	MenuService menuService;
	
	private static String userOpLogSearch="";//用于模糊查询的名字
	
	@RequestMapping("userOpLogInit")
	public ModelAndView adminInit(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//获取参数
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("UserOpLogInit,参数获取失败");	
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
			maView.setViewName("error");
			return maView;
		} 
    	//读取数据库
    	try {
    		Page page=new Page();
        	int count=userOpLogService.count();
        	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
            Pattern pattern = Pattern.compile("[0-9]{1,9}");
            if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
                page.setCurrentPage(1);
            } else {
                page.setCurrentPage(Integer.valueOf(currentPage));
            }
            page.setTotalNumber(count);
            page.count();
            map.put("page", page);

    		List<UserOpLog> userOpLogListByPage=userOpLogService.listByPage(map);
            maView.addObject("userOpLogListByPage", userOpLogListByPage);
            maView.addObject("controlURL", "userOpLogListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("userOpLog");

        	return maView;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("UserOpLogInit：读取数据库错误");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			e.printStackTrace();
			maView.setViewName("error");
			return maView;
		}
    	
    }
	@RequestMapping("userOpLogListByPage")
	public ModelAndView  departmentSelectAnalysisListByPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		ModelAndView maView=new ModelAndView();
		//参数获取
		String currentPage="";
		try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userOpLogListByPage:参数获取");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
			maView.setViewName("error");
			return maView;
		}
		//数据库操作
		try {
			Page page=new Page();
	    	int count=userOpLogService.count();
	    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
	        Pattern pattern = Pattern.compile("[0-9]{1,9}");
	        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
	            page.setCurrentPage(1);
	        } else {
	            page.setCurrentPage(Integer.valueOf(currentPage));
	        }
	        page.setTotalNumber(count);
	        page.count();
	        map.put("page", page);
	        
	    	List<UserOpLog> userOpLogListByPage=userOpLogService.listByPage(map);
            maView.addObject("userOpLogListByPage", userOpLogListByPage);
            maView.addObject("controlURL", "userOpLogListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("userOpLog");
        	
	    	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userOpLogListByPage:数据库操作");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			e.printStackTrace();
			maView.setViewName("error");
			return maView;
		}
    	
	}
	
	@RequestMapping("userOpLogSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	String currentPage="";
    	try {
    		System.out.println("canshu"+request.getParameter("userOpLogSearch"));
    		userOpLogSearch = java.net.URLDecoder.decode(request.getParameter("userOpLogSearch"),"UTF-8");
        	currentPage=request.getParameter("currentPage");
        	System.out.println("seactc"+userOpLogSearch);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userOpLogSearchByName:获取数据错误");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
			mav.setViewName("error");
			return mav;
		}
    	//数据库操作
    	try {
    		
    		Page page=new Page();
    		Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("op_msg", userOpLogSearch);
            
	    	int count=userOpLogService.searchCount(mapSearch);
	    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
	        Pattern pattern = Pattern.compile("[0-9]{1,9}");
	        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
	            page.setCurrentPage(1);
	        } else {
	            page.setCurrentPage(Integer.valueOf(currentPage));
	        }
	        page.setTotalNumber(count);
	        page.count();
	        map.put("page", page);
	        map.put("op_msg", userOpLogSearch);
	    	List<UserOpLog> userOpLogListByPage=userOpLogService.search(map);
            mav.addObject("userOpLogListByPage", userOpLogListByPage);
            mav.addObject("controlURL", "userOpLogSearchPage");//控制页码传递URL
            mav.addObject("page", page); 
        	mav.setViewName("userOpLog");
    		  
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userOpLogSearchByName:数据库操作错误");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			e.printStackTrace();
			mav.setViewName("error");
			return mav;
		}
    	   
    }
	@RequestMapping("userOpLogSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	String currentPage="";
    	try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userOpLogSearchPage:参数获取");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
			mav.setViewName("error");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
    		Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("op_msg", userOpLogSearch);
            
            
	    	int count=userOpLogService.searchCount(mapSearch);
	    	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
	        Pattern pattern = Pattern.compile("[0-9]{1,9}");
	        if(currentPage == null ||  !pattern.matcher(currentPage).matches()) {
	            page.setCurrentPage(1);
	        } else {
	            page.setCurrentPage(Integer.valueOf(currentPage));
	        }
	        page.setTotalNumber(count);
	        page.count();
	        map.put("page", page);
	        map.put("op_msg", userOpLogSearch);
	    	List<UserOpLog> userOpLogListByPage=userOpLogService.search(map);
            mav.addObject("userOpLogListByPage", userOpLogListByPage);
            mav.addObject("controlURL", "userOpLogSearchPage");//控制页码传递URL
            mav.addObject("page", page); 
        	mav.setViewName("userOpLog"); 
        	
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userOpLogSearchPage:数据库操作");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			e.printStackTrace();
			mav.setViewName("error");
			return mav;
		}
    	   
    }
	
	
	
	
	
	

}
