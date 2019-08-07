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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.app.mapper.TrackMapperApp;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Feedback;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.service.FeedbackService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class FeedbackController {
	@Autowired
	FeedbackService feedbackService;
	private static String  title="";//用于模糊查询的名字
	//反馈界面初始化
	@RequestMapping("feedbackInit")
	public ModelAndView roleInit(HttpServletRequest request,  HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//获取参数
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackInit:参数获取"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
        	int count=feedbackService.count();
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
            List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
            maView.addObject("feedbackListByPage", feedbackListByPage);
            maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("feedback");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackInit:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
	//分页显示
	@RequestMapping("feedbackListByPage")
	public ModelAndView  roleListByPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView maView=new ModelAndView();
		//获取参数
		String currentPage="";
		try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackListByPage:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
        //数据库操作
		try {
			Page page=new Page();
	    	int count=feedbackService.count();
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
	        List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
	        maView.addObject("feedbackListByPage", feedbackListByPage);
	        maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("feedback");
	    	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackListByPage:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
	}
	//按titlle进行查询
	
	@RequestMapping("feedbackSearchByUid")
    public ModelAndView roleSearchByName(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//获取参数 
    	String currentPage=null;
    	try {
    		currentPage=request.getParameter("currentPage");
    		title = java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackSearchByUid:获取数据"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    	  //数据库操作
    	try {
    		 Page page=new Page(); //分页类
             Map<String,Object> mapSearch = new HashMap<String, Object>();
             mapSearch.put("title", title);
             int count = feedbackService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
             map.put("title",title);
             List<Feedback> feedbackListByPage= feedbackService.search(map);//分页查询二极栏目
             mav.addObject("feedbackListByPage", feedbackListByPage);  
             mav.addObject("page", page);
             mav.addObject("controlURL", "feedbackSearchPage");//控制页码传递URL
             mav.setViewName("feedback");   
             return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackSearchByUid:数据库操作"+e.toString());
			mav.setViewName("login");
			return mav;
		}  
    }
	@RequestMapping("feedbackSearchPage")
    public ModelAndView searchPage(HttpServletRequest request,  HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//获取参数
    	String currentPage=null;
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackSearchPage:获取数据"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("title", title);
            int count = feedbackService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("title",title);
            List<Feedback> feedbackListByPage= feedbackService.search(map);//分页查询二极栏目
            mav.addObject("feedbackListByPage", feedbackListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "feedbackSearchPage");//控制页码传递URL
            mav.setViewName("feedback");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("feedbackSearchPage:数据库操作"+e.toString());
			mav.setViewName("login");
			return mav;
		}   
    }
	
	//添加,这是前台添加的，后台不用添加
	//删除,反馈一般不允许删除
	
	
	
	
	
	
	
	//编辑
	@RequestMapping("editFeedback")
    public ModelAndView editRole(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	int id=0;
    	String editFeedbackState="";
    	String currentPage="";
    	//参数获取
    	try {
			id=Integer.valueOf(request.getParameter("editFeedbackID"));
			editFeedbackState=request.getParameter("editFeedbackState");
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editFeedback:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
//    	Feedback feedback=new Feedback();
//		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
//    	feedback.setId(Integer.valueOf(request.getParameter("editFeedbackID")));
//    	feedback.setUid(Integer.valueOf(request.getParameter("editFeedbackUid")));
//    	feedback.setTitle(request.getParameter("editFeedbackTitle"));
//    	feedback.setText(request.getParameter("editFeedbackText"));
//    	feedback.setImg(request.getParameter("editFeedbackImg"));
//    	feedback.setContact(request.getParameter("editFeedbackContact"));
    	//数据库操作
    	try {
    		Feedback feedback=feedbackService.get(id);
        	feedback.setState(editFeedbackState);
        	//数据库操作
        	feedbackService.update(feedback);
        	Page page=new Page();
        	int count=feedbackService.count();
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
            List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
            maView.addObject("feedbackListByPage", feedbackListByPage);
            maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("feedback");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editFeedback:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
	 @RequestMapping("deleteFeedback")
	    public ModelAndView deleteUser(HttpServletRequest request,  HttpServletResponse response) throws IOException{
	    	ModelAndView maView = new ModelAndView();
	    	//参数获取
	    	int id=0;
	    	String currentPage="";
	    	try {
	    		id= Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
	    		currentPage=request.getParameter("currentPage");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("deleteFeedback:参数获取"+e.toString());
				maView.setViewName("login");
				return maView;
			}
	    	//数据库操作
	    	try {
	        	feedbackService.delete(id);
	        	Page page=new Page();
	        	int count=feedbackService.count();
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
	            List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
	            maView.addObject("feedbackListByPage", feedbackListByPage);
	            maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
	            maView.addObject("page", page); 
	        	maView.setViewName("feedback");
	        	return maView;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("deleteDepartment:数据库操作"+e.toString());
				maView.setViewName("login");
				return maView;
			}
	    }
    
	
	
	
}
