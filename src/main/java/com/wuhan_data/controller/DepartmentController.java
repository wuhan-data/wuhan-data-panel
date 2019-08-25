package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.DepartmentService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.impl.SysLogServiceImpl;
import com.wuhan_data.tools.Page;;

@Controller
@RequestMapping("")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	private static String departmentname="";//用于模糊查询的名字
	
	 public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("errCode", errCode);
			responseMap.put("errMsg", errMsg);
			responseMap.put("data", data);
			System.out.println(JSON.toJSONString(responseMap));
			return JSON.toJSONString(responseMap);
		}
	
	@RequestMapping("listDepartment")
	public ModelAndView listDepartment() {
		ModelAndView mav=new ModelAndView();
		List<Department> departmentList=departmentService.list();
		mav.addObject("departmentList",departmentList);
		mav.setViewName("listDepartment");
		return mav;	
	}
	//code是否存在
	 @RequestMapping(value="departmentCodeIsExist",produces="application/json;charset=utf-8")
	 @ResponseBody
	 public String codeIsExist(HttpServletRequest request, 
	            HttpServletResponse response) {
		 	JSONObject jsonObject = new JSONObject();
	    	String code="";
	    	try {
				code=URLDecoder.decode(request.getParameter("roleCode"),"utf-8");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("codeIsExist参数获取异常"+e.getStackTrace());
			}
	    	try {
	    		Map map=new HashMap();
	    		map.put("department_code", code);
	    		List<Department> departments=departmentService.getByCode(map);
	    		if (departments.size()>0) {
					jsonObject.put("data", "exist");
				}
	    		else {
					jsonObject.put("data", "notExist");
				}
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		System.out.println("codeIsExist数据库操作异常"+e.getStackTrace());
	    	}
	    	return jsonObject.toString();
	    }
	//name是否存在
	 @RequestMapping(value="departmentNameIsExist",produces="application/json;charset=utf-8")
	 @ResponseBody
	 public String nameIsExist(HttpServletRequest request, 
	            HttpServletResponse response) {
		 	JSONObject jsonObject = new JSONObject();
	    	String name="";
	    	try {
				name=URLDecoder.decode(request.getParameter("roleName"),"utf-8");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("codeIsExist参数获取异常"+e.getStackTrace());
			}
	    	try {
	    		Map map=new HashMap();
	    		map.put("department_name", name);
	    		List<Department> departments=departmentService.getByName(map);
	    		if (departments.size()>0) {
					jsonObject.put("data", "exist");
				}
	    		else {
					jsonObject.put("data", "notExist");
				}
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		System.out.println("codeIsExist数据库操作异常"+e.getStackTrace());
	    	}
	    	return jsonObject.toString();
	    }
	
	@RequestMapping("departmentInit")
	public ModelAndView departmentInit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ModelAndView maView=new ModelAndView();
        //参数获取
        String currentPage="";
        try {
        	currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentInit:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}

        //数据库操作
        try {
        	Page page=new Page();
        	int count=departmentService.count();
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
            List<Department> departmentListByPage=departmentService.listByPage(map);
            maView.addObject("departmentListByPage", departmentListByPage);
            maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("department");
        	return maView;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentInit:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
    	
    }
	@RequestMapping("departmentSelectAnalysisListByPage")
	public ModelAndView  departmentSelectAnalysisListByPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView maView=new ModelAndView();
		//参数获取
		String currentPage="";
		try {
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentSelectAnalysisListByPage:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
		//数据库操作
		try {
			Page page=new Page();
	    	int count=departmentService.count();
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
	        List<Department> departmentListByPage=departmentService.listByPage(map);
	        maView.addObject("departmentListByPage", departmentListByPage);
	        maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("department");
	    	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentSelectAnalysisListByPage:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	
	}
	@RequestMapping("departmentSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	String currentPage="";
    	try {
    		departmentname = java.net.URLDecoder.decode(request.getParameter("departmentname"),"UTF-8");
        	currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentSearchByName:获取数据"+e.toString());
			mav.setViewName("error");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("departmentname", departmentname);
            int count = departmentService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("departmentname",departmentname);
            List<Department> departmentListByPage= departmentService.search(map);//分页查询二极栏目
            mav.addObject("departmentListByPage", departmentListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "departmentSearchPage");//控制页码传递URL
            mav.setViewName("department");     
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentSearchByName:数据库操作"+e.toString());
			mav.setViewName("error");
			return mav;
		}
    	   
    }
	@RequestMapping("departmentSearchPage")
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
			System.out.println("departmentSearchPage:参数获取"+e.toString());
			mav.setViewName("error");
			return mav;
		}
    	//数据库操作
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("departmentname", departmentname);
            int count = departmentService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("departmentname",departmentname);
            List<Department> departmentListByPage= departmentService.search(map);//分页查询二极栏目
            mav.addObject("departmentListByPage", departmentListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "departmentSearchPage");//控制页码传递URL
            mav.setViewName("department");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("departmentSearchPage:数据库操作"+e.toString());
			mav.setViewName("error");
			return mav;
		}
    	   
    }
	//add department
    @RequestMapping("addDepartment")
    public ModelAndView addDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        //参数获取
    	ModelAndView maView = new ModelAndView();
    	String departmentCode="";
    	String departmentName="";
    	String departmentDescription="";
    	String currentPage="";
    	try {
			departmentCode=request.getParameter("addDepartmentCode");
			departmentName=request.getParameter("addDepartmentName");
			departmentDescription=request.getParameter("addDepartmentDescription");
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addDepartment:获取参数"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		Department department=new Department();
        	department.setDepartment_code(departmentCode);
        	department.setDepartment_name(departmentName);
        	department.setDepartment_description(departmentDescription);
        	departmentService.add(department);
        	Page page=new Page();
        	int count=departmentService.count();
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
            List<Department> departmentListByPage=departmentService.listByPage(map);
            maView.addObject("departmentListByPage", departmentListByPage);
            maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("department");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addDepartment:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    }
  //修改
    @RequestMapping("editDepartment")
    public ModelAndView editDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("进入方法--------------");
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	int id=0;
    	String departmentCode="";
    	String departmentName="";
    	String departmentDescription="";
    	String currentPage="";
    	//获取参数
    	try {
    		id=Integer.valueOf(request.getParameter("editDepartmentID"));
			departmentCode=request.getParameter("editDepartmentCode");
			departmentName=request.getParameter("editDepartmentName");
			departmentDescription=request.getParameter("editDepartmentDescription");
			currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editDepartment:获取参数"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		Department department=new Department();
        	department.setId(id);
        	department.setDepartment_code(departmentCode);
        	department.setDepartment_name(departmentName);
        	department.setDepartment_description(departmentDescription);
        	departmentService.update(department);
        	Page page=new Page();
        	int count=departmentService.count();
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
            List<Department> departmentListByPage=departmentService.listByPage(map);    
            maView.addObject("departmentListByPage", departmentListByPage);
            maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("department");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editDepartment:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    }
  //删除
    @RequestMapping("deleteDepartment")
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
			System.out.println("deleteDepartment:参数获取"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
        	departmentService.delete(id);
        	int count=departmentService.count();
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
            List<Department> departmentListByPage=departmentService.listByPage(map);
            maView.addObject("departmentListByPage", departmentListByPage);
            maView.addObject("controlURL", "departmentSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("department");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteDepartment:数据库操作"+e.toString());
			maView.setViewName("error");
			return maView;
		}
    }
}
