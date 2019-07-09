package com.wuhan_data.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.tools.Page;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Notice;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.NoticeService;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.UserService;
@Controller
@RequestMapping("")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	private static String content="";//用于模糊查询的名字
	@RequestMapping("listNotice")
	public ModelAndView listNoticce() {
		ModelAndView maView=new ModelAndView();
		List<Notice> cs=noticeService.list();
		maView.addObject("cs",cs);
		maView.setViewName("notice");
		return maView;
	}
	@RequestMapping("noticeInit")
	public ModelAndView noticeInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=noticeService.count();
    	
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
        List<Notice> noticeListByPage=noticeService.listByPage(map);
        List<Role> roleList=roleService.List();
        maView.addObject("roleList", roleList);
        maView.addObject("noticeListByPage",  noticeListByPage);
        maView.addObject("controlURL", "noticeListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("notice");
    	return maView;
    }
	@RequestMapping("noticeListByPage")
	public ModelAndView noticeListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=noticeService.count();
    	
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
        List<Notice> noticeListByPage=noticeService.listByPage(map);
        List<Role> roleList=roleService.List();
        maView.addObject("roleList", roleList);
        maView.addObject("noticeListByPage",  noticeListByPage);
        maView.addObject("controlURL", "noticeListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("notice");
    	return maView;
    }
	@RequestMapping("addNotice")
	 public ModelAndView addNotice(HttpServletRequest request, 
	            HttpServletResponse response) throws IOException{
//	    	request.setCharacterEncoding("UTF-8");    	
//	        response.setCharacterEncoding("UTF-8");
	    	ModelAndView maView = new ModelAndView();
	    	Notice notice=new Notice();
	    	notice.setSender_id(Integer.valueOf(request.getParameter("addSender_id")));
	    	notice.setReceiver_id(Integer.valueOf(request.getParameter("addReceiver_id")));
	    	notice.setContent(request.getParameter("addContent"));
	    	notice.setCreate_time(new Date());
	    	noticeService.add(notice);
	    	
	    	Page page=new Page();
	    	
	    	int count=noticeService.count();
	    	
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
	        List<Notice> noticeListByPage=noticeService.listByPage(map);
	        List<Role> roleList=roleService.List();
	        maView.addObject("roleList", roleList);
	        maView.addObject("noticeListByPage",  noticeListByPage);
	        maView.addObject("controlURL", "noticeListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("notice");
	    	return maView;
	    }
	
	@RequestMapping("addNoticeByRole")
	 public ModelAndView addNoticeByRole(HttpServletRequest request, 
	            HttpServletResponse response) throws IOException{
//	    	request.setCharacterEncoding("UTF-8");    	
//	        response.setCharacterEncoding("UTF-8");
	    	ModelAndView maView = new ModelAndView();
	    	
	    	int role_id=Integer.valueOf(request.getParameter("roleListSelectNotice"));
	    	int sender_id=Integer.valueOf(request.getParameter("addByRoleSender_id"));
	    	String content=request.getParameter("addByRoleContent");
	    	List<User> userByRoleList=userService.getByRole(role_id);
	    	List<Notice> noticeList=new ArrayList<Notice>();
	    	for (int i=1;i<=userByRoleList.size();i++)
	    	{
	    		int Re_id=userByRoleList.get(i-1).getId();
	    		Notice notice=new Notice();
	    		notice.setSender_id(sender_id);
	    		notice.setReceiver_id(Re_id);
	    		notice.setContent(content);
	    		notice.setCreate_time(new Date());
	    		noticeList.add(notice);
	    	};
	    	System.out.println(noticeList);
	    	noticeService.addByRole(noticeList);
	    	
		/*
		 * Notice notice=new Notice();
		 * notice.setSender_id(Integer.valueOf(request.getParameter("addSender_id")));
		 * notice.setReceiver_id(Integer.valueOf(request.getParameter("addReceiver_id"))
		 * ); notice.setContent(request.getParameter("addContent"));
		 * notice.setCreate_time(new Date()); noticeService.add(notice);
		 */
	    	
	    	Page page=new Page();
	    	
	    	int count=noticeService.count();
	    	
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
	        List<Notice> noticeListByPage=noticeService.listByPage(map);
	        List<Role> roleList=roleService.List();
	        maView.addObject("roleList", roleList);
	        maView.addObject("noticeListByPage",  noticeListByPage);
	        maView.addObject("controlURL", "noticeListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("notice");
	    	return maView;
	    }
	
	
	@RequestMapping("editNotice")
	public ModelAndView editNotice(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	Notice notice=new Notice();
    	notice.setId(Integer.valueOf(request.getParameter("editNoticeID")));
    	notice.setSender_id(Integer.valueOf(request.getParameter("editNoticeSender_id")));
    	notice.setReceiver_id(Integer.valueOf(request.getParameter("editNoticeReceiver_id")));
    	notice.setContent(request.getParameter("editNoticeContent"));
    	notice.setCreate_time(new Date());
    	noticeService.update(notice);
    	
    	Page page=new Page();
    	
    	int count=noticeService.count();
    	
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
        List<Notice> noticeListByPage=noticeService.listByPage(map);
        List<Role> roleList=roleService.List();
        maView.addObject("roleList", roleList);
        maView.addObject("noticeListByPage",  noticeListByPage);
        maView.addObject("controlURL", "noticeListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("notice");
    	return maView;
    }
	@RequestMapping("deleteNotice")
	public ModelAndView deleteNotice(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		
		ModelAndView maView = new ModelAndView();
		int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
		System.out.print("id:"+id);
		noticeService.delete(id);
    	
    	Page page=new Page();
    	
    	int count=noticeService.count();
    	
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
        List<Notice> noticeListByPage=noticeService.listByPage(map);
        List<Role> roleList=roleService.List();
        maView.addObject("roleList", roleList);
        maView.addObject("noticeListByPage",  noticeListByPage);
        maView.addObject("controlURL", "noticeListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("notice");
    	return maView;
    }
	@RequestMapping("noticeSearchByContent")
    public ModelAndView noticeSearchByContent(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	content = java.net.URLDecoder.decode(request.getParameter("content"),"UTF-8");
    	 System.out.println("content="+content);
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("content", content);
           int count = noticeService.searchCountByContent(mapSearch);//每一个一级栏目下面二极栏目的数量
          
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
           map.put("content",content);
           List<Notice> noticeListByPage=noticeService.searchByContent(map);
           List<Role> roleList=roleService.List();
           mav.addObject("roleList", roleList);
           mav.addObject("noticeListByPage",  noticeListByPage);
           mav.addObject("controlURL", "noticeSearchListByPage");//控制页码传递URL
           mav.addObject("page", page); 
           mav.setViewName("notice");
       	   return mav;
    }
	@RequestMapping("noticeSearchListByPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("content", content);
           int count = noticeService.searchCountByContent(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("content",content);
           List<Notice> noticeListByPage=noticeService.searchByContent(map);
           List<Role> roleList=roleService.List();
           mav.addObject("roleList", roleList);
           mav.addObject("noticeListByPage",  noticeListByPage);
           mav.addObject("controlURL", "noticeSearchListByPage");//控制页码传递URL
           mav.addObject("page", page); 
           mav.setViewName("notice");
       	   return mav;
    }
   
}
