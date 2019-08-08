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
	private int operate_user_id=0;
	@RequestMapping("sysLogInit")
	public ModelAndView departmentInit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
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
			maView.setViewName("login");
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
            maView.addObject("sysLogListByPage",sysLogListByPage);
            maView.addObject("controlURL", "sysLogListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("sysLog");
        	return maView;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogInit:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
	@RequestMapping("sysLogListByPage")
	public ModelAndView  departmentSelectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
		//参数获取
    	String currentPage=null;
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogListByPage:参数获取"+e.toString());
			maView.setViewName("login");
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
        	maView.setViewName("sysLog");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogListByPage:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
	}
	@RequestMapping("sysLogSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	String currentPage=null;
    	try {
    		operate_user_id = Integer.valueOf(java.net.URLDecoder.decode(request.getParameter("operate_user_id"),"UTF-8"));
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchByName:参数获取"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("operate_user_id", operate_user_id);
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
            map.put("operate_user_id",operate_user_id);
            List<SysLog> sysLogListByPage= sysLogService.search(map);//分页查询二极栏目
            maView.addObject("sysLogListByPage", sysLogListByPage);
            maView.addObject("controlURL", "sysLogSearchPage");//控制页码传递URL
            maView.addObject("page", page); 
            maView.setViewName("sysLog");        
            return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchByName:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		} 
    }
	@RequestMapping("sysLogSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
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
			mav.setViewName("login");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("operate_user_id", operate_user_id);
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
            map.put("operate_user_id",operate_user_id);
            List<SysLog> sysLogListByPage= sysLogService.search(map);//分页查询二极栏目
            mav.addObject("sysLogListByPage", sysLogListByPage);
            mav.addObject("controlURL", "sysLogSearchPage");//控制页码传递URL
            mav.addObject("page", page); 
            mav.setViewName("sysLog");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sysLogSearchPage:参数获取"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    }
}
