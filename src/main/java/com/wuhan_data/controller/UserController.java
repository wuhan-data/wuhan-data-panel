package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.tools.MenuList;
import com.wuhan_data.tools.Page;
import com.alibaba.fastjson.JSON;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.service.UserService;
import com.wuhan_data.service.DepartmentService;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.SysLogService;
@Controller
@RequestMapping("")
public class UserController {
	@Autowired
    UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	MenuService menuService;
	@Autowired
	SysLogService sysLogService;
	
	private static String tel="";//用于模糊查询的名字
    @RequestMapping("listUser")
    public ModelAndView listUser(){
        ModelAndView mav = new ModelAndView();
        List<User> cs= userService.list();
        mav.addObject("cs", cs);
        mav.setViewName("userManage");
        return mav;
    }
    @RequestMapping(value="test.do",produces="application/json;charset=utf-8")
    @ResponseBody
    public String test(HttpServletRequest request, 
            HttpServletResponse response) {
    	System.out.println("响应的测试接口test.do"+userService.get(32).toString());
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("data", userService.get(32));
    	return jsonObject.toString();
    }
    @RequestMapping(value="telIsExist",produces="application/json;charset=utf-8")
    @ResponseBody
    public String telIsExist(HttpServletRequest request, 
            HttpServletResponse response) {
    	JSONObject jsonObject = new JSONObject();
    	String tel="";
    	try {
			tel=URLDecoder.decode(request.getParameter("tel"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("相应的额接口为telIsExist");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
		}
    	try {
    		Map map=new HashMap();
    		map.put("tel", tel);
			List<User> users=userService.getByTel(map);
    		if (users.size()>0) {
				jsonObject.put("data", "exist");
			}
    		else {
				jsonObject.put("data", "notExist");
			}
		} catch (Exception e) {
			// TODO: handle exception
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
		}
    	return jsonObject.toString();
    }
    //指定角色id是否存在
    @RequestMapping(value="roleIdIsExistForD",produces="application/json;charset=utf-8")
    @ResponseBody
    public String roleIdIsExistForD(HttpServletRequest request, 
            HttpServletResponse response) {
    	JSONObject jsonObject = new JSONObject();
    	String roleId="";
    	try {
			roleId=URLDecoder.decode(request.getParameter("roleId"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("相应的额接口为roleIdIsExistForD");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
		}
    	try {
    		
    		if (userService.isExistRoleId(Integer.valueOf(roleId))==true) {
				jsonObject.put("data", "exist");
			}
    		else {
				jsonObject.put("data", "notExist");
			}
		} catch (Exception e) {
			// TODO: handle exception
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
		}
    	return jsonObject.toString();
    }
    //指定部门id是否存在
    @RequestMapping(value="departmentIdIsExistForD",produces="application/json;charset=utf-8")
    @ResponseBody
    public String departmentIdIsExistForD(HttpServletRequest request, 
            HttpServletResponse response) {
    	JSONObject jsonObject = new JSONObject();
    	String departmentId="";
    	try {
			departmentId=URLDecoder.decode(request.getParameter("departmentId"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("相应的额接口为departmentIdIsExistForD");
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
		}
    	try {
    		if (userService.isExistDepartmentId(Integer.valueOf(departmentId))==true) {
				jsonObject.put("data", "exist");
			}
    		else {
				jsonObject.put("data", "notExist");
			}
		} catch (Exception e) {
			// TODO: handle exception
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
		}
    	return jsonObject.toString();
    }
    
    
    
    
    @RequestMapping(value="selectByRealName2",produces="application/json;charset=utf-8")
    @ResponseBody
    public String selectByRealName2(HttpServletRequest request, 
            HttpServletResponse response){
    	JSONObject jsonObject = new JSONObject();
    	String realName="";
    	try {
			realName=URLDecoder.decode(request.getParameter("realName"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
		}
    	Map map=new HashMap();
    	map.put("real_name", realName);
    	List<User> users=userService.searchByRealname(map);
    	
    	List data=new ArrayList();
    	for(int i=0;i<users.size();i++) {
    		Map map1=new HashMap();
    		User user=users.get(i);
    		map1.put("realname", user.getReal_name());
    		map1.put("role", user.getRole_id());
    		map1.put("id", user.getId());
    		data.add(map1);
    	}

    	jsonObject.put("errCode", "0");
    	jsonObject.put("errMsg", "success");
    	jsonObject.put("data", data);
    	System.out.println(jsonObject.toString());
		return jsonObject.toString();
    }
    
    
    
    
    @RequestMapping(value="selectByRealName",produces="application/json;charset=utf-8")
    @ResponseBody
    public String selectByRealName(HttpServletRequest request, 
            HttpServletResponse response){
    	JSONObject jsonObject = new JSONObject();
    	String realName="";
    	try {
			realName=URLDecoder.decode(request.getParameter("realName"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			e.printStackTrace();
		}
    	Map map=new HashMap();
    	map.put("real_name", realName);
    	List<User> users=userService.searchByRealname(map);
    	String resultString="";
    	for (int i=0;i<users.size();i++)
    	{
    		User user=users.get(i);
    		resultString+=user.getReal_name()+"(角色："+user.getRole_id()+")|id="+user.getId()+"|;";
    	}
		jsonObject.put("data", resultString);
		jsonObject.put("msg","success");
		return jsonObject.toString();
    }
    
    @RequestMapping("userInit")
    public ModelAndView userInit(HttpServletRequest request, 
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
			System.out.println("userInit:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
        try {
        	Page page=new Page();
        	int count=userService.count();
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
            List<User> userListByPage=userService.listByPage(map);
            List<Role> roleList=roleService.List();
            maView.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            maView.addObject("departmentList",departmentList);
            maView.addObject("userListByPage", userListByPage);
            maView.addObject("controlURL", "userSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("user");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userInit:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
			return maView;
		}
    	
    }
    
    @RequestMapping("userSelectAnalysisListByPage") 
    public ModelAndView selectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView=new ModelAndView();
    	//参数获取
    	 String currentPage="";
         try {
         	currentPage=request.getParameter("currentPage");
 		} catch (Exception e) {
 			// TODO: handle exception
 			System.out.println("userSelectAnalysisListByPage:获取数据"+e.toString());
 			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
 			maView.setViewName("error");
 			return maView;
 		}
    	try {
    		Page page=new Page();
        	int count=userService.count();
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
            List<User> userListByPage=userService.listByPage(map);
            List<Role> roleList=roleService.List();
            maView.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            maView.addObject("departmentList",departmentList);
            maView.addObject("userListByPage", userListByPage);
            maView.addObject("controlURL", "userSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("user");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userSelectAnalysisListByPage:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
 			maView.setViewName("error");
 			return maView;
		}
    }
	 
    
    @RequestMapping("userSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	//参数获取
    	 String currentPage="";
    	try {
    		tel = java.net.URLDecoder.decode(request.getParameter("tel"),"UTF-8");
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userSearchByName:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
 			mav.setViewName("error");
 			return mav;
		}
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("tel", tel);
            int count = userService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
            map.put("tel",tel);
            List<User> userListByPage= userService.search(map);//分页查询二极栏目
            List<Role> roleList=roleService.List();
            mav.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            mav.addObject("departmentList",departmentList);
            mav.addObject("userListByPage", userListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "userSearchPage");//控制页码传递URL
            mav.setViewName("user");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userSearchByName:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
 			mav.setViewName("error");
 			return mav;
		}
    }
    @RequestMapping("userSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	String currentPage="";
    	try {
    		currentPage=request.getParameter("currentPage");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userSearchPage:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
 			mav.setViewName("error");
 			return mav;
		}
    	try {
    		Page page=new Page(); //分页类
            Map<String,Object> mapSearch = new HashMap<String, Object>();
            mapSearch.put("tel", tel);
            int count = userService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
            System.out.println("count:"+count);
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
            map.put("tel",tel);
            List<User> userListByPage= userService.search(map);//分页查询二极栏目
            List<Role> roleList=roleService.List();
            mav.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            mav.addObject("departmentList",departmentList);
            mav.addObject("userListByPage", userListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "userSearchPage");//控制页码传递URL
            mav.setViewName("user");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userSearchPage:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
 			mav.setViewName("error");
 			return mav;
		}
    }
    
    //添加user
    @RequestMapping("addUser")
    public ModelAndView addUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//获取数据
    	String roleListSelect="";
    	String departmentListSelect="";
    	String genderSelect="男";
    	String addUserTel="";
    	String addUserReal_name="";
    	String addUserRole_list="";
    	String addBirthday="";
    	String addCity="";
    	String addPassword = "";
    	try {
    		roleListSelect=StringUtils.join(request.getParameterValues("roleListSelect"),"|");
    		departmentListSelect=StringUtils.join(request.getParameterValues("departmentListSelect"),"|");
    		//departmentListSelect=request.getParameter("departmentListSelect");
    		genderSelect=request.getParameter("genderSelect");
    		addUserTel=request.getParameter("addUserTel");
    		addUserReal_name=request.getParameter("addUserReal_name");
    		addUserRole_list=request.getParameter("addUserRole_list");
    		addBirthday=request.getParameter("addBirthday");
    		addCity=request.getParameter("addCity");
			addPassword = request.getParameter("addPassWord");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addUser:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
 			maView.setViewName("error");
 			return maView;
		}
 
    	//数据库操作
    	try {
    		System.out.println("shenfen"+roleListSelect);
    		User user=new User();
        	user.setRole_id(roleListSelect);
        	user.setDepartment_id(departmentListSelect);
        	user.setStatus("0");
        	String genderString=genderSelect;
        	if (genderString.equals("男"))
        		user.setGender(1);//男1女0
        	else {
    			user.setGender(0);
    		}
        	user.setTel(addUserTel);
        	user.setReal_name(addUserReal_name);
        	user.setRole_list(addUserRole_list);
        	user.setRole_name("1");
        	user.setCreate_time(new Date());
        	user.setPassword(addPassword);
        	Date birthday=new Date();
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	birthday=formatter.parse(addBirthday);
        	System.out.println("birthday"+birthday);
        	user.setBirthday(birthday);
        	user.setCity(addCity);
        	userService.add(user);
        	Page page=new Page();
        	int count=userService.count();
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
            List<User> userListByPage=userService.listByPage(map);
            List<Role> roleList=roleService.List();
            maView.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            maView.addObject("departmentList",departmentList);
            maView.addObject("userListByPage", userListByPage);
            maView.addObject("controlURL", "userSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("user");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addUser:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
 			maView.setViewName("error");
 			return maView;
		}
    }
    
    
    //修改
    @RequestMapping("editUser")
    public ModelAndView editUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
    	//数据获取
    	int  editUserID=0;
    	String editroleListSelect="";
    	String editdepartmentListSelect="";
    	String editstatus="";
    	String editgenderSelect="男";
    	String editUserTel="";
    	String editUserReal_name="";
    	String editUserRole_list="";
    	String editBirthday="";
    	String editCity="";
    	String editPassword = "";
    	try {
    		editUserID=Integer.parseInt(request.getParameter("editUserID"));
        	editroleListSelect=StringUtils.join(request.getParameterValues("editroleListSelect"),"|");
        	editdepartmentListSelect=StringUtils.join(request.getParameterValues("editdepartmentListSelect"),"|");
        	//editdepartmentListSelect=request.getParameter("editdepartmentListSelect");
        	editstatus=request.getParameter("editstatus");
        	editgenderSelect=request.getParameter("editgenderSelect");
        	editUserTel=request.getParameter("editUserTel");
        	editUserReal_name=request.getParameter("editUserReal_name");
        	editUserRole_list=request.getParameter("editUserRole_list");
        	editBirthday=request.getParameter("editBirthday");
        	editCity=request.getParameter("editCity");
			editPassword = request.getParameter("editPassWord");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editUser:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
 			maView.setViewName("error");
 			return maView;
		}

    	try {
    		User user=new User();
        	user.setId(editUserID);
        	user.setRole_id(editroleListSelect);
        	user.setDepartment_id(editdepartmentListSelect);
        	user.setStatus(editstatus);
        	user.setPassword(editPassword);
        	String genderString=editgenderSelect;
        	if (genderString.equals("男"))
        		user.setGender(1);//男1女0
        	else {
    			user.setGender(0);
    		}
        	user.setTel(editUserTel);
        	user.setReal_name(editUserReal_name);
        	user.setRole_list(editUserRole_list);
        	user.setRole_name("1");
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	Date birthday=formatter.parse(editBirthday);
            user.setBirthday(birthday);
        	user.setCity(editCity);
        	userService.updata(user);
        	Page page=new Page();
        	int count=userService.count();
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
            List<User> userListByPage=userService.listByPage(map);
            List<Role> roleList=roleService.List();
            maView.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            maView.addObject("departmentList",departmentList);
            maView.addObject("userListByPage", userListByPage);
            maView.addObject("controlURL", "userSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("user");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editUser:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);

 			maView.setViewName("error");
 			return maView;
		}
    	
    }
    
    //删除
    @RequestMapping("deleteUser")
    public ModelAndView deleteUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	//参数获取
    	
    	String currentPage="";
    	int id=0;
    	try {
			currentPage=request.getParameter("currentPage");
			id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteUser:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
 			maView.setViewName("error");
 			return maView;
		}
    	try {
    		userService.delete(id);
        	Page page=new Page();
        	int count=userService.count();
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
            List<User> userListByPage=userService.listByPage(map);
            List<Role> roleList=roleService.List();
            maView.addObject("roleList", roleList);
            List<Department> departmentList=departmentService.list();
            maView.addObject("departmentList",departmentList);
            maView.addObject("userListByPage", userListByPage);
            maView.addObject("controlURL", "userSelectAnalysisListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("user");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteUser:数据库操作 "+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
 			maView.setViewName("error");
 			return maView;
		}
    }

    @RequestMapping("userManage")
    public ModelAndView userManage() {
    	ModelAndView mav=new ModelAndView();
        List<User> cs= userService.list();
        
        // 
        mav.addObject("cs", cs);
        // 
        mav.setViewName("user");
        return mav;
    	
    }
    
    
    
    
    
    
    
  //转向登录页面
  	@RequestMapping("tologin")
  	public String tologin(){
  		return "login";	
  	}
  	
  	 //转向注册页面
  	@RequestMapping("toregister")
  	public String toregister(){
  		return "register";	
  	}
  	
  	//登录验证
  	@RequestMapping("login1")
  	public String  login(@RequestParam("username") String username,
  			@RequestParam("password") String password,Model model,HttpServletRequest request, 
            HttpServletResponse response)throws IOException{
  		User user = new User();
  		String username1 = null;
  		try {
			username1 = new String(username.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "login";
		}
  		user.setUsername(username1);
  		user.setPassword(password);
  		if(userService.logincheck(user) != null){
  			model.addAttribute("error",username1);
  			//日志
  			HttpSession session=request.getSession();
  			User newUser=userService.getByName(user.getUsername());
  			session.setAttribute("user", newUser);
  			
  			//菜单生成
  			System.out.println("role_list="+newUser.getRole_list());
  			List<MenuList> menuList=menuService.getMenu(newUser.getRole_list());
  			session.setAttribute("menuList",menuList);
  			
  			
  			
  			return "index";
  		}
  	    else{
  	    	model.addAttribute("error","账号或密码错误");
  	    	return "login";
  	    }
  		}
 
  	@RequestMapping("register")
	public String regist(@RequestParam("re_username") String username,@RequestParam("re_password") String password,@RequestParam("re_confirmpassword") String confirm_password,Model model){
  		String username1 = null;
  		try {
			username1 = new String(username.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
  		ModelAndView mav = new ModelAndView();
		System.out.println("用户注册："+username1+" "+password+" "+confirm_password);
		User user = new User();
		if(password.equals(confirm_password))
		{
			user.setUsername(username1);
			user.setPassword(password);
			user.setGender(1);
			user.setReal_name(username1);
			user.setRole_list("查看");
			user.setRole_name("用户");
			user.setStatus("0");
			user.setTel("1365984567");
			Date data= new Date();
			user.setCreate_time(data);
			userService.regist(user);
			
			model.addAttribute("msg", "注册成功");
			System.out.println("注册成功");
			//注册成功后跳转success.jsp页面
//			mav.setViewName("login");
//			return mav;
			return "redirect:tologin";
		}
		else
		{
			System.out.println("注册失败");
			model.addAttribute("msg", "注册失败");
//			mav.addObject("error", "注册失败");
//			mav.setViewName("register");
//			return mav;
			return "login";
		}
		
	}

    
    

}
