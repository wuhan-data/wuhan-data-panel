package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	@Autowired
	SysLogService sysLogService;
	private static String role_name="";//用于模糊查询的名字
	@RequestMapping("listRole")
	public ModelAndView listRole() {
		ModelAndView mav=new ModelAndView();
		List<Role> roleList=roleService.List();
		
		mav.addObject("roleList",roleList);
		
		mav.setViewName("listRole");
		return mav;
	}
	@RequestMapping("roleInit")
	public ModelAndView roleInit(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=roleService.count();
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
        List<Role> roleListByPage=roleService.listByPage(map);
        
        maView.addObject("roleListByPage", roleListByPage);
        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("role");
    	return maView;
    }
	@RequestMapping("roleListByPage")
	public ModelAndView  roleListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
		ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=roleService.count();
    	
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
        List<Role> roleListByPage=roleService.listByPage(map);
        
        maView.addObject("roleListByPage", roleListByPage);
        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("role");
    	return maView;
	}
	@RequestMapping("roleSearchByName")
    public ModelAndView roleSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	role_name = java.net.URLDecoder.decode(request.getParameter("role_name"),"UTF-8");
    	System.out.println("rolename"+role_name);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("role_name", role_name);
           int count = roleService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("role_name",role_name);
           List<Role> roleListByPage= roleService.search(map);//分页查询二极栏目
           mav.addObject("roleListByPage", roleListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "roleSearchPage");//控制页码传递URL
           mav.setViewName("role");   
           

	        HttpSession session=request.getSession();
	        Admin adminLL=(Admin)session.getAttribute("user");  
	    	sysLogService.add(adminLL.getUsername(),"roleSearchByName","com.wuhan_data.controller.RoleController.roleSearchByName");
	        
           
           return mav;
    }
	@RequestMapping("roleSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("role_name", role_name);
           int count = roleService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("role_name",role_name);
           List<Role> roleListByPage= roleService.search(map);//分页查询二极栏目
           mav.addObject("roleListByPage", roleListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "roleSearchPage");//控制页码传递URL
           mav.setViewName("role");           
           return mav;
    }
	//add role
	@RequestMapping("addRole")
	public ModelAndView addRole(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	Role role=new Role();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	role.setRole_code(request.getParameter("addRoleCode"));
    	role.setRole_name(request.getParameter("addRoleName"));
    	role.setRole_description(request.getParameter("addRoleDescription"));
    	roleService.add(role);
    	
    	Page page=new Page();
    	int count=roleService.count();
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
        List<Role> roleListByPage=roleService.listByPage(map);
        
        maView.addObject("roleListByPage", roleListByPage);
        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("role");
    	
    	HttpSession session=request.getSession();
    	Admin adminLL=(Admin)session.getAttribute("user"); 
	    sysLogService.add(adminLL.getUsername(),"addRole","com.wuhan_data.controller.RoleController.addRole");
	        
    	
    	return maView;
    }
	//delete role
	@RequestMapping("deleteRole")
    public ModelAndView deleteRole(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	System.out.print("id:"+id);
    	roleService.delete(id);
    	int count=roleService.count();
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
        map.put("page", page);
        List<Role> roleListByPage=roleService.listByPage(map);
        
        maView.addObject("roleListByPage", roleListByPage);
        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("role");
    	
    	HttpSession session=request.getSession();
    	Admin adminLL=(Admin)session.getAttribute("user");  
	    sysLogService.add(adminLL.getUsername(),"deteleRole","com.wuhan_data.controller.RoleController.deteleRole");
	      
    	
    	return maView;
    }
	//edit role
	@RequestMapping("editRole")
    public ModelAndView editRole(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	Role role=new Role();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	role.setId(Integer.valueOf(request.getParameter("editRoleID")));
    	role.setRole_code(request.getParameter("editRoleCode"));
    	role.setRole_name(request.getParameter("editRoleName"));
    	role.setRole_description(request.getParameter("editRoleDescription"));
    	System.out.println(role.toString());
    	roleService.update(role);
    	Page page=new Page();
    	int count=roleService.count();
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
        List<Role> roleListByPage=roleService.listByPage(map);
        
        maView.addObject("roleListByPage", roleListByPage);
        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("role");
    	
    	HttpSession session=request.getSession();
    	Admin adminLL=(Admin)session.getAttribute("user");  
	    sysLogService.add(adminLL.getUsername(),"editRole","com.wuhan_data.controller.RoleController.editRole");
	      
    	
    	return maView;
    }
    

}
