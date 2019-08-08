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
	public ModelAndView roleInit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//获取参数
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleInit:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Page page=new Page();
        	int count=roleService.count();
        	System.out.println(count);
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
            List<Role> roleListByPage=roleService.listByPage(map);
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleInit:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
	@RequestMapping("roleListByPage")
	public ModelAndView  roleListByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView maView=new ModelAndView();
    	
    	//获取参数
		String currentPage="";
		try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleListByPage:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
		try {
			Page page=new Page();
	    	int count=roleService.count();
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
	        List<Role> roleListByPage=roleService.listByPage(map);
	        maView.addObject("roleListByPage", roleListByPage);
	        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("role");
	    	return maView;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleListByPage:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
	}
	@RequestMapping("roleSearchByName")
    public ModelAndView roleSearchByName(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
    		role_name = java.net.URLDecoder.decode(request.getParameter("role_name"),"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleSearchByName:获取数据"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    	try {
    		 Page page=new Page(); //分页类
             Map<String,Object> mapSearch = new HashMap<String, Object>();
             mapSearch.put("role_name", role_name);
             int count = roleService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
             map.put("role_name",role_name);
             List<Role> roleListByPage= roleService.search(map);//分页查询二极栏目
             mav.addObject("roleListByPage", roleListByPage);  
             mav.addObject("page", page);
             mav.addObject("controlURL", "roleSearchPage");//控制页码传递URL
             mav.setViewName("role");   
             return mav;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleSearchByName:数据库操作"+e.toString());
			mav.setViewName("login");
			return mav;
		}
    	  
    }
	@RequestMapping("roleSearchPage")
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
			System.out.println("roleSearchPage:获取数据"+e.toString());
			mav.setViewName("login");
			return mav;
		}
        //数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("role_name", role_name);
            int count = roleService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("role_name",role_name);
            List<Role> roleListByPage= roleService.search(map);//分页查询二极栏目
            mav.addObject("roleListByPage", roleListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "roleSearchPage");//控制页码传递URL
            mav.setViewName("role");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleSearchPage:数据库操作"+e.toString());
			mav.setViewName("login");
			return mav;
		}   
    }
	//add role
	@RequestMapping("addRole")
	public ModelAndView addRole(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	String addRoleCodeString="";
    	String addRoleNameString="";
    	String addRoleDescriptionString="";
    	String currentPage="";
    	try {
    		addRoleCodeString=request.getParameter("addRoleCode");
    		addRoleNameString=request.getParameter("addRoleName");
    		addRoleDescriptionString=request.getParameter("addRoleDescription");
    		currentPage=request.getParameter("currentPage");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addRole:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Role role=new Role();
        	role.setRole_code(addRoleCodeString);
        	role.setRole_name(addRoleNameString);
        	role.setRole_description(addRoleDescriptionString);
        	
        	roleService.add(role);
        	Page page=new Page();
        	int count=roleService.count();
        	Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数    
            Pattern pattern = Pattern.compile("[0-9]{1,9}");
            if(currentPage == null ||!pattern.matcher(currentPage).matches()) {
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
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addRole:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    }
	//delete role
	@RequestMapping("deleteRole")
    public ModelAndView deleteRole(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	int id=0;
    	String currentPage="";
    	try {
    		id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteRole:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		roleService.delete(id);
        	int count=roleService.count();
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
            List<Role> roleListByPage=roleService.listByPage(map);
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteRole:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    }
	//edit role
	@RequestMapping("editRole")
    public ModelAndView editRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	String currentPage="";
    	int editRoleID=0;
    	String editRoleCodeString="";
    	String editRoleNameString="";
    	String editRoleDescriptionString="";
    	try {
    		editRoleID=Integer.valueOf(request.getParameter("editRoleID"));
    		editRoleCodeString=request.getParameter("editRoleCode");
    		editRoleNameString=request.getParameter("editRoleName");
    		editRoleDescriptionString=request.getParameter("editRoleDescription");
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editRole:获取数据"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	//数据库操作
    	try {
    		Role role=new Role();
        	role.setId(editRoleID);
        	role.setRole_code(editRoleCodeString);
        	role.setRole_name(editRoleNameString);
        	role.setRole_description(editRoleDescriptionString);
        	roleService.update(role);
        	Page page=new Page();
        	int count=roleService.count();
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
            List<Role> roleListByPage=roleService.listByPage(map);
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editRole:数据库操作"+e.toString());
			maView.setViewName("login");
			return maView;
		}
    	
    }
    

}
