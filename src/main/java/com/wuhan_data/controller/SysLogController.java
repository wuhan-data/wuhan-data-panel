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
	private String e_type="";
	@RequestMapping("sysLogInit")
	public ModelAndView sysLogInit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//参数获取
    	String currentPage=null;
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogInit:参数获取"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
        	int count=sysLogService.count();
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
            List<SysLog> sysLogListByPage=sysLogService.listByPage(map);
            System.out.println("zuixin"+sysLogListByPage.get(0).toString());
            maView.addObject("sysLogListByPage",sysLogListByPage);
            maView.addObject("controlURL", "sysLogListByPage");//控制页码传递URL
            maView.addObject("page", page); 
            int type=0;
            if (e_type.equals("user"))
            {
            	type=1;
            }
            if (e_type.equals("admin"))
            {
            	type=2;
            }
            maView.addObject("type",type);
        	maView.setViewName("sysLog");
        	return maView;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogInit:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
			return maView;
		}
    	
    }
	@RequestMapping("sysLogListByPage")
	public ModelAndView  sysLogListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
		//参数获取
    	String currentPage=null;
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogListByPage:参数获取"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
		//数据库操作
    	try {
    		Page page=new Page();
        	int count=sysLogService.count();
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
            List<SysLog> sysLogListByPage=sysLogService.listByPage(map);
            maView.addObject("sysLogListByPage", sysLogListByPage);
            maView.addObject("controlURL", "sysLogListByPage");//控制页码传递URL
            maView.addObject("page", page); 
            int type=0;
            if (e_type.equals("user"))
            {
            	type=1;
            }
            if (e_type.equals("admin"))
            {
            	type=2;
            }
            maView.addObject("type",type);
        	maView.setViewName("sysLog");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogListByPage:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
			return maView;
		}
	}
	@RequestMapping("sysLogSearchByName")
    public ModelAndView sysLogSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	String currentPage=null;
    	try {
    		e_type = java.net.URLDecoder.decode(request.getParameter("e_type"),"UTF-8");
    		System.out.println(e_type);
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchByName:参数获取"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("e_type", e_type);
            int count = sysLogService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("e_type",e_type);
            List<SysLog> sysLogListByPage= sysLogService.search(map);//分页查询二极栏目
            maView.addObject("sysLogListByPage", sysLogListByPage);
            maView.addObject("controlURL", "sysLogSearchPage");//控制页码传递URL
            maView.addObject("page", page); 
            int type=0;
            if (e_type.equals("user"))
            {
            	type=1;
            }
            if (e_type.equals("admin"))
            {
            	type=2;
            }
            maView.addObject("type",type);
            maView.setViewName("sysLog");        
            return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchByName:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
			return maView;
		} 
    }
	@RequestMapping("sysLogSearchPage")
    public ModelAndView sysLogSearchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	String currentPage=null;
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchPage:参数获取"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			mav.setViewName("error");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("e_type", e_type);
            int count = sysLogService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("e_type",e_type);
            List<SysLog> sysLogListByPage= sysLogService.search(map);//分页查询二极栏目
            mav.addObject("sysLogListByPage", sysLogListByPage);
            mav.addObject("controlURL", "sysLogSearchPage");//控制页码传递URL
            mav.addObject("page", page); 
            int type=0;
            if (e_type.equals("user"))
            {
            	type=1;
            }
            if (e_type.equals("admin"))
            {
            	type=2;
            }
            mav.addObject("type",type);
            mav.setViewName("sysLog");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchPage:参数获取"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			mav.setViewName("error");
			return mav;
		}
    }
}
