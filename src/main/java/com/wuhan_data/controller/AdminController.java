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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Menu;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.AdminService;
import com.wuhan_data.service.AdminService;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.impl.SysLogServiceImpl;
import com.wuhan_data.tools.MenuList;
import com.wuhan_data.tools.Page;

import sun.print.PSPrinterJob.PluginPrinter;;

@Controller
@RequestMapping("")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	@Autowired
	SysLogService sysLogService;
	@Autowired
	MenuService menuService;
	private static String adminname="";//用于模糊查询的名字
	@RequestMapping("listAdmin")
	public ModelAndView listAdmin() {
		ModelAndView mav=new ModelAndView();
		List<Admin> adminList=adminService.list();
		
		mav.addObject("adminList",adminList);
		
		mav.setViewName("listAdmin");
		return mav;
		
	}
	/*
	 * @RequestMapping("test") public ModelAndView test() { ModelAndView mav=new
	 * ModelAndView();
	 * 
	 * mav.setViewName("test"); return mav;
	 * 
	 * }
	 */
	
	
	@RequestMapping("adminInit")
	public ModelAndView adminInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
       
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=adminService.count();
    	
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
        List<Admin> adminListByPage=adminService.listByPage(map);
        
    	List<Menu> menus=menuService.list();
    	List<String> allMenuLevelTwo=new ArrayList<String>();
    	for (int i=0;i<menus.size();i++)
    		allMenuLevelTwo.add(menus.get(i).getLevel_two());
        maView.addObject("allMenuLevelTwo",allMenuLevelTwo);
        
        maView.addObject("adminListByPage", adminListByPage);
        maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("admin");
    	System.out.println(allMenuLevelTwo);
    	return maView;
    }
	@RequestMapping("adminSelectAnalysisListByPage")
	public ModelAndView  adminSelectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=adminService.count();
    	
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
        List<Admin> adminListByPage=adminService.listByPage(map);
        
        maView.addObject("adminListByPage", adminListByPage);
        maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("admin");
    	return maView;
	}
	@RequestMapping("adminSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	adminname = java.net.URLDecoder.decode(request.getParameter("adminname"),"UTF-8");
    	System.out.println("adminname"+adminname);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("adminname", adminname);
           int count = adminService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("adminname",adminname);
           List<Admin> adminListByPage= adminService.search(map);//分页查询二极栏目
           System.out.print(adminListByPage);
           mav.addObject("adminListByPage", adminListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "adminSearchPage");//控制页码传递URL
           mav.setViewName("admin");     
           
           HttpSession session=request.getSession();
           Admin adminLL=(Admin)session.getAttribute("user"); 
       	   sysLogService.add(adminLL.getUsername(),"adminSearchByName","com.wuhan_data.controller.AdminController.adminSearchByName");
           
           return mav;
    }
	@RequestMapping("adminSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    	System.out.println("adminname"+adminname);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("adminname", adminname);
           int count = adminService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("adminname",adminname);
           List<Admin> adminListByPage= adminService.search(map);//分页查询二极栏目
           mav.addObject("adminListByPage", adminListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "adminSearchPage");//控制页码传递URL
           mav.setViewName("admin");           
           return mav;
    }
	//add admin
    @RequestMapping("addAdmin")
    public ModelAndView addAdmin(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
 //   	System.out.println("theme_name"+theme_name);
//    	String theme_name="%"+search+"%";
    	Admin admin=new Admin();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	admin.setUsername(request.getParameter("addAdminUsername"));
    	admin.setPassword(request.getParameter("addAdminPassword"));
    	admin.setStatus(Integer.valueOf(request.getParameter("addAdminStatus")));
    	admin.setRole_list(request.getParameter("addAdminRole_list"));
    	admin.setCreate_date(new Date());
    	
    	
    	String[] getLevelTwoList=request.getParameterValues("addMenuLevelTwo");
    	for(int i=0;i<getLevelTwoList.length;i++)
    	{
    		System.out.println(getLevelTwoList[i]);
    	}
    	
    	
    	
    	
    	
    	
    	
    	adminService.add(admin);
    	
    	Page page=new Page();
    	int count=adminService.count();
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
        List<Admin> adminListByPage=adminService.listByPage(map);
        
        maView.addObject("adminListByPage", adminListByPage);
        maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("admin");
    	
    	 
        HttpSession session=request.getSession();
        Admin adminLL=(Admin)session.getAttribute("user"); 
    	sysLogService.add(adminLL.getUsername(),"addAdmin","com.wuhan_data.controller.AdminController.addAdmin");
        
    	
    	return maView;
    }
  //修改
    @RequestMapping("editAdmin")
    public ModelAndView editAdmin(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	System.out.println("进入方法--------------");
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");   	
//    	String theme_name="%"+search+"%";
    	Admin admin=new Admin();
    	admin.setId(Integer.valueOf(request.getParameter("editAdminID")));
    	admin.setUsername(request.getParameter("editAdminUsername"));
    	admin.setPassword(request.getParameter("editAdminPassword"));
    	admin.setStatus(Integer.valueOf(request.getParameter("editAdminStatus")));
    	admin.setRole_list(request.getParameter("editAdminRole_list"));
    	int a=adminService.update(admin);
    	System.out.println("a"+a);
    	Page page=new Page();
    	int count=adminService.count();
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
        List<Admin> adminListByPage=adminService.listByPage(map);    
        maView.addObject("adminListByPage", adminListByPage);
        maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("admin");
    	
    	 
        HttpSession session=request.getSession();
        Admin adminLL=(Admin)session.getAttribute("user"); 
    	sysLogService.add(adminLL.getUsername(),"editAdmin","com.wuhan_data.controller.AdminController.editAdmin");
        
    	
    	return maView;
    }
  //删除
    @RequestMapping("deleteAdmin")
    public ModelAndView deleteUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	System.out.print("id:"+id);
    	adminService.delete(id);
    	
    	int count=adminService.count();
    	Page page=new Page();
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
        List<Admin> adminListByPage=adminService.listByPage(map);
        
        maView.addObject("adminListByPage", adminListByPage);
        maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("admin");
    	
    	 
        HttpSession session=request.getSession();
        Admin adminLL=(Admin)session.getAttribute("user"); 
    	sysLogService.add(adminLL.getUsername(),"deleteAdmin","com.wuhan_data.controller.AdminController.deleteAdmin");
        
    	
    	return maView;
    }
    
    
    @RequestMapping("login")
    public ModelAndView adminLogin(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	//int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	String username=request.getParameter("username");
    	String password=request.getParameter("password");
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("username",username);
    	map.put("password",password);
    	if (adminService.adminLogin(map)==0)
    	{
    		maView.setViewName("login");
    		
    	}
    	else {
    		maView.setViewName("index");
    		HttpSession session=request.getSession();
    		Admin adminLL=adminService.getByName(username) ;
    		session.setAttribute("user", adminLL);
  			sysLogService.add(adminLL.getUsername(),"Login","com.wuhan_data.controller.UserController.login");
  			List<MenuList> menuList=menuService.getMenu(adminLL.getRole_list());
  			session.setAttribute("menuList",menuList);
		}
    	return maView;
    }
    @RequestMapping("adminLogout")
    public ModelAndView adminLogout(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	//int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	 HttpSession session = request.getSession(false);//防止创建Session  
         if(session == null){  
        	 maView.setViewName("login");
             
             return maView;  
         }  
           
         session.removeAttribute("user");  
         session.invalidate();
         maView.setViewName("login");
    	return maView;
    }

}
