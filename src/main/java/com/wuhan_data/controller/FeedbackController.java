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
	
	private static int uid=0;//用于模糊查询的名字
	
	//反馈界面初始化
	@RequestMapping("feedbackInit")
	public ModelAndView roleInit(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=feedbackService.count();
    	System.out.println(count);
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
        List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
        
        maView.addObject("feedbackListByPage", feedbackListByPage);
        maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("feedback");
    	return maView;
    }
	//分页显示
	@RequestMapping("feedbackListByPage")
	public ModelAndView  roleListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
		ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	int count=feedbackService.count();
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
        List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
        
        maView.addObject("feedbackListByPage", feedbackListByPage);
        maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("feedback");
    	return maView;
	}
	//按id进行查询
	
	@RequestMapping("feedbackSearchByUid")
    public ModelAndView roleSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	uid = Integer.valueOf(java.net.URLDecoder.decode(request.getParameter("uid"),"UTF-8"));
    	System.out.println("feedbackSearchById"+"uid"+uid);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("uid", uid);
           int count = feedbackService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("uid",uid);
           List<Feedback> feedbackListByPage= feedbackService.search(map);//分页查询二极栏目
           mav.addObject("feedbackListByPage", feedbackListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "feedbackSearchPage");//控制页码传递URL
           mav.setViewName("feedback");   
	       return mav;
    }
	@RequestMapping("feedbackSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("uid", uid);
           int count = feedbackService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("uid",uid);
           List<Feedback> feedbackListByPage= feedbackService.search(map);//分页查询二极栏目
           mav.addObject("feedbackListByPage", feedbackListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "feedbackSearchPage");//控制页码传递URL
           mav.setViewName("feedback");           
           return mav;
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
    	int id=Integer.valueOf(request.getParameter("editFeedbackID"));
    	String state=request.getParameter("editFeedbackID");
//    	Feedback feedback=new Feedback();
//		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
//    	feedback.setId(Integer.valueOf(request.getParameter("editFeedbackID")));
//    	feedback.setUid(Integer.valueOf(request.getParameter("editFeedbackUid")));
//    	feedback.setTitle(request.getParameter("editFeedbackTitle"));
//    	feedback.setText(request.getParameter("editFeedbackText"));
//    	feedback.setImg(request.getParameter("editFeedbackImg"));
//    	feedback.setContact(request.getParameter("editFeedbackContact"));
    	Feedback feedback=feedbackService.get(id);
    	feedback.setState(state);
    	feedbackService.update(feedback);

    	Page page=new Page();
    	int count=feedbackService.count();
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
        List<Feedback> feedbackListByPage=feedbackService.listByPage(map);
        
        maView.addObject("feedbackListByPage", feedbackListByPage);
        maView.addObject("controlURL", "feedbackListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("feedback");
      
    	return maView;
    }
    
	
	
	
}
