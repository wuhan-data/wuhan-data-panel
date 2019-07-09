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
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.DepartmentService;
import com.wuhan_data.tools.Page;;

@Controller
@RequestMapping("")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	private static String departmentname="";//用于模糊查询的名字
	@RequestMapping("listDepartment")
	public ModelAndView listDepartment() {
		ModelAndView mav=new ModelAndView();
		List<Department> departmentList=departmentService.list();
		
		mav.addObject("departmentList",departmentList);
		
		mav.setViewName("listDepartment");
		return mav;
		
	}
	
	@RequestMapping("departmentInit")
	public ModelAndView departmentInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        
       
    	ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=departmentService.count();
    	
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
        List<Department> departmentListByPage=departmentService.listByPage(map);
        
        maView.addObject("departmentListByPage", departmentListByPage);
        maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("department");
    	return maView;
    }
	@RequestMapping("departmentSelectAnalysisListByPage")
	public ModelAndView  departmentSelectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
    	Page page=new Page();
    	
    	int count=departmentService.count();
    	
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
        List<Department> departmentListByPage=departmentService.listByPage(map);
        
        maView.addObject("departmentListByPage", departmentListByPage);
        maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("department");
    	return maView;
	}
	@RequestMapping("departmentSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	departmentname = java.net.URLDecoder.decode(request.getParameter("departmentname"),"UTF-8");
    	System.out.println("departmentname"+departmentname);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("departmentname", departmentname);
           int count = departmentService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("departmentname",departmentname);
           List<Department> departmentListByPage= departmentService.search(map);//分页查询二极栏目
           System.out.print(departmentListByPage);
           mav.addObject("departmentListByPage", departmentListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "departmentSearchPage");//控制页码传递URL
           mav.setViewName("department");           
           return mav;
    }
	@RequestMapping("departmentSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    	System.out.println("departmentname"+departmentname);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("departmentname", departmentname);
           int count = departmentService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("departmentname",departmentname);
           List<Department> departmentListByPage= departmentService.search(map);//分页查询二极栏目
           mav.addObject("departmentListByPage", departmentListByPage);  
           mav.addObject("page", page);
           mav.addObject("controlURL", "departmentSearchPage");//控制页码传递URL
           mav.setViewName("department");           
           return mav;
    }
	//add department
    @RequestMapping("addDepartment")
    public ModelAndView addDepartment(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
 //   	System.out.println("theme_name"+theme_name);
//    	String theme_name="%"+search+"%";
    	Department department=new Department();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	department.setDepartment_code(Integer.valueOf(request.getParameter("addDepartmentCode")));
    	department.setDepartment_name(request.getParameter("addDepartmentName"));
    	department.setDepartment_description(request.getParameter("addDepartmentDescription"));
    	departmentService.add(department);
    	
    	Page page=new Page();
    	int count=departmentService.count();
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
        List<Department> departmentListByPage=departmentService.listByPage(map);
        
        maView.addObject("departmentListByPage", departmentListByPage);
        maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("department");
    	return maView;
    }
  //修改
    @RequestMapping("editDepartment")
    public ModelAndView editDepartment(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	System.out.println("进入方法--------------");
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");   	
//    	String theme_name="%"+search+"%";
    	Department department=new Department();
    	department.setId(Integer.valueOf(request.getParameter("editDepartmentID")));
    	department.setDepartment_code(Integer.valueOf(request.getParameter("editDepartmentCode")));
    	department.setDepartment_name(request.getParameter("editDepartmentName"));
    	department.setDepartment_description(request.getParameter("editDepartmentDescription"));
    	System.out.print(department.getId()+department.getDepartment_code());
    	int a=departmentService.update(department);
    	System.out.println("a"+a);
    	Page page=new Page();
    	int count=departmentService.count();
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
        List<Department> departmentListByPage=departmentService.listByPage(map);    
        maView.addObject("departmentListByPage", departmentListByPage);
        maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("department");
    	return maView;
    }
  //删除
    @RequestMapping("deleteDepartment")
    public ModelAndView deleteUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	System.out.print("id:"+id);
    	departmentService.delete(id);
    	
    	int count=departmentService.count();
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
        List<Department> departmentListByPage=departmentService.listByPage(map);
        
        maView.addObject("departmentListByPage", departmentListByPage);
        maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
        maView.addObject("page", page); 
    	maView.setViewName("department");
    	return maView;
    }
    

}
