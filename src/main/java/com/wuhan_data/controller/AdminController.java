package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.page.PageParams;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Menu;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.AdminService;
import com.wuhan_data.service.AdminService;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.UserService;
import com.wuhan_data.service.impl.SysLogServiceImpl;
import com.wuhan_data.tools.MenuList;
import com.wuhan_data.tools.Page;

import sun.print.PSPrinterJob.PluginPrinter;;

@Controller
@RequestMapping("")
public class AdminController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	AdminService adminService;
	@Autowired
	MenuService menuService;
	@Autowired
	SysLogService sysLogService;
	
	private static String adminname="";//用于模糊查询的名字
	@RequestMapping("listAdmin")
	public ModelAndView listAdmin() {
		ModelAndView mav=new ModelAndView();
		List<Admin> adminList=adminService.list();
		mav.addObject("adminList",adminList);
		mav.setViewName("listAdmin");
		return mav;
	}	
	
	@RequestMapping(value="nameIsExist",produces="application/json;charset=utf-8")
    @ResponseBody
    public String nameIsExist(HttpServletRequest request, 
            HttpServletResponse response) {
    	JSONObject jsonObject = new JSONObject();
    	String username="";
    	try {
			username=URLDecoder.decode(request.getParameter("username"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("相应的额接口为telIsExist");
			e.printStackTrace();
		}
    	try {
    		Admin admin= adminService.getByName(username);
    		if (admin!=null) {
				jsonObject.put("data", "exist");
			}
    		else {
				jsonObject.put("data", "notExist");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return jsonObject.toString();
    }
	
	
	
	
	@RequestMapping("adminInit")
	public ModelAndView adminInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//获取参数
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminInit,参数获取失败"+e.toString());
			maView.setViewName("error");
			return maView;
		} 
    	//读取数据库
    	try {
    		Page page=new Page();
        	int count=adminService.count();
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
            
        	
            
            List<MenuList> allMenuList=menuService.getAllMenu();
    		maView.addObject("allMenuList",allMenuList);
            
    		List<Admin> adminListByPage=adminService.listByPage(map);
            maView.addObject("adminListByPage", adminListByPage);
            maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("admin");
        	System.out.println(allMenuList);
        	return maView;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminInit：读取数据库错误"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
    }
	@RequestMapping("adminSelectAnalysisListByPage")
	public ModelAndView  adminSelectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
		//获取数据
		String currentPage="";
		try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminSelectAnalysisListByPage：参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
		
		//数据库操作
		try {
			Page page=new Page();
	    	int count=adminService.count();
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
	        List<MenuList> allMenuList=menuService.getAllMenu();
    		maView.addObject("allMenuList",allMenuList);
    		
	        List<Admin> adminListByPage=adminService.listByPage(map);
	        maView.addObject("adminListByPage", adminListByPage);
	        maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("admin");
	    	return maView;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminSelectAnalysisListByPage：数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
	}
	@RequestMapping("adminSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	String  currentPage="";
    	try {
    		adminname = java.net.URLDecoder.decode(request.getParameter("adminname"),"UTF-8");
        	currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminSearchByName:参数获取"+e.toString());
			mav.setViewName("error");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("adminname", adminname);
            int count = adminService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("adminname",adminname);
            List<MenuList> allMenuList=menuService.getAllMenu();
    		mav.addObject("allMenuList",allMenuList);
            List<Admin> adminListByPage= adminService.search(map);//分页查询二极栏目
            mav.addObject("adminListByPage", adminListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "adminSearchPage");//控制页码传递URL
            mav.setViewName("admin");     
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminSearchByName:数据库操作"+e.toString());
			mav.setViewName("error");
			return mav;
		}	   
    }
	@RequestMapping("adminSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    	//参数获取
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminSearchPage:参数获取"+e.toString());
			mav.setViewName("error");
			return mav;
		}
    	try {
    		 Page page=new Page(); //分页类
             Map<String,Object> mapSearch = new HashMap<String, Object>();
             mapSearch.put("adminname", adminname);
             int count = adminService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
             map.put("adminname",adminname);
             List<MenuList> allMenuList=menuService.getAllMenu();
     		 mav.addObject("allMenuList",allMenuList);
             List<Admin> adminListByPage= adminService.search(map);//分页查询二极栏目
             mav.addObject("adminListByPage", adminListByPage);  
             mav.addObject("page", page);
             mav.addObject("controlURL", "adminSearchPage");//控制页码传递URL
             mav.setViewName("admin");           
             return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("adminSearchPage:数据库操作"+e.toString());
			mav.setViewName("error");
			return mav;
		}  
    }
	//add admin
    @RequestMapping("addAdmin")
    public ModelAndView addAdmin(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	String usernameString="";
    	String passwordString="";
    	int status=0;
    	String role_listString="";
    	String currentPage="";
    	try {
    		usernameString=request.getParameter("addAdminUsername");
    		passwordString=request.getParameter("addAdminPassword");
    		status=Integer.valueOf(request.getParameter("addAdminStatus"));
//    		role_listString=request.getParameter("addAdminRole_list");
//    		String le[]=request.getParameterValues("addMenuLevelTwo");
//    		System.out.println(StringUtils.join(le));
    		role_listString=StringUtils.join(request.getParameterValues("addMenuLevelTwo"),",");
    		currentPage=request.getParameter("currentPage");	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addAdmin:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		Admin admin=new Admin();
        	admin.setUsername(usernameString);
        	admin.setPassword(passwordString);
        	admin.setStatus(status);
        	admin.setRole_list(role_listString);
        	admin.setCreate_date(new Date());
        	adminService.add(admin);
        	Page page=new Page();
        	int count=adminService.count();
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
            List<MenuList> allMenuList=menuService.getAllMenu();
    		maView.addObject("allMenuList",allMenuList);
            List<Admin> adminListByPage=adminService.listByPage(map);
            maView.addObject("adminListByPage", adminListByPage);
            maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("admin");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addAdmin:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
    }
  //修改
    @RequestMapping("editAdmin")
    public ModelAndView editAdmin(HttpServletRequest request,  HttpServletResponse response) throws Exception{
    	System.out.println("进入方法--------------");
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	
    	//参数获取
    	int id=0;
    	String usernameString="";
    	String passwordString="";
    	int status=0;
    	String role_listString="";
    	String currentPage="";
    	try {
    		id=Integer.valueOf(request.getParameter("editAdminID"));
    		usernameString=request.getParameter("editAdminUsername");
    		passwordString=request.getParameter("editAdminPassword");
    		status=Integer.valueOf(request.getParameter("editAdminStatus"));
    		//role_listString=request.getParameter("editAdminRole_list");
    		role_listString=StringUtils.join(request.getParameterValues("editMenuLevelTwo"),",");
    		
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editAdmin:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		//测试日志	
    		System.out.println(role_listString);
    		Admin admin=new Admin();
        	admin.setId(id);
        	admin.setUsername(usernameString);
        	admin.setPassword(passwordString);
        	admin.setStatus(status);
        	admin.setRole_list(role_listString);
        	adminService.update(admin);
        	Page page=new Page();
        	int count=adminService.count();
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
            List<MenuList> allMenuList=menuService.getAllMenu();
    		maView.addObject("allMenuList",allMenuList);
            List<Admin> adminListByPage=adminService.listByPage(map);    
            maView.addObject("adminListByPage", adminListByPage);
            maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("admin");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editAdmin:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
    }
  //删除
    @RequestMapping("deleteAdmin")
    public ModelAndView deleteUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	int id=0;
    	String currentPage="";
    	try {
        	id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
            currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteAdmin:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		if(id!=1) //id为1的管理员不能删除
    		{
	    		adminService.delete(id);
	        	int count=adminService.count();
	        	Page page=new Page();
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
	            List<MenuList> allMenuList=menuService.getAllMenu();
	    		maView.addObject("allMenuList",allMenuList);
	            List<Admin> adminListByPage=adminService.listByPage(map);
	            maView.addObject("adminListByPage", adminListByPage);
	            maView.addObject("controlURL", "adminSelectAnalysisListByPage");//控制页码传递URL
	            maView.addObject("page", page); 
	        	maView.setViewName("admin");
	        	return maView;
    		}
    		else {
    			maView.setViewName("error");
    			return maView;
    			
    		}
        	
    		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteAdmin:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    }
    
    
    @RequestMapping("login")
    public ModelAndView adminLogin(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	String username="";
    	String password="";
    	try {
    		username=request.getParameter("username");
        	password=request.getParameter("password");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("login:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
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
	  			List<MenuList> menuList=menuService.getMenu(adminLL.getRole_list());
	  			System.out.println(menuList);
	  			session.setAttribute("menuList",menuList);
	  			Map<String, List<String>> a=userService.getAllPower(45);
	  			List<String>bList=a.get("powerIndexManages");
	  			
	  			
			}
	    	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("login:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
    }
    @RequestMapping("adminLogout")
    public ModelAndView adminLogout(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
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
