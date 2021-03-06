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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.tools.MenuList;
import com.wuhan_data.tools.Page;
import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.pojo.SysLog;
import com.wuhan_data.service.UserService;
import com.wuhan_data.service.DepartmentService;
import com.wuhan_data.service.MenuService;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.SysLogService;;
@Controller
@RequestMapping("")
public class UserController {
	@Autowired
    UserService userService;
	@Autowired
	SysLogService sysLogService;
	@Autowired
	RoleService roleService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	MenuService menuService;
	
	private static String username="";//用于模糊查询的名字
    @RequestMapping("listUser")
    public ModelAndView listUser(){
        ModelAndView mav = new ModelAndView();
        List<User> cs= userService.list();
         
        // 
        mav.addObject("cs", cs);
        // 
        mav.setViewName("userManage");
        return mav;
    }
    
    
    
    @RequestMapping("userInit")
    public ModelAndView userInit(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
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
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
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
    }
    
    @RequestMapping("userSelectAnalysisListByPage") 
    public ModelAndView selectAnalysisListByPage(HttpServletRequest request, 
            HttpServletResponse response) throws UnsupportedEncodingException {
//    	request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        
    	ModelAndView maView=new ModelAndView();
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
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
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
    }
	 
    
    @RequestMapping("userSearchByName")
    public ModelAndView userSearchByName(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
    	username = java.net.URLDecoder.decode(request.getParameter("username"),"UTF-8");
    	System.out.println("username"+username);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("username", username);
           int count = userService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("username",username);
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
    	
    }
    @RequestMapping("userSearchPage")
    public ModelAndView searchPage(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView mav = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
    	System.out.println("username"+username);
//    	String theme_name="%"+search+"%";
    	   Page page=new Page(); //分页类
           Map<String,Object> mapSearch = new HashMap<String, Object>();
           mapSearch.put("username", username);
           int count = userService.searchCount(mapSearch);//每一个一级栏目下面二极栏目的数量
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
           map.put("username",username);
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
    }
    
    //添加user
    @RequestMapping("addUser")
    public ModelAndView addUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");
 //   	System.out.println("theme_name"+theme_name);
//    	String theme_name="%"+search+"%";
    	User user=new User();
		/* user.setId(Integer.valueOf(request.getParameter("addUserId"))); */
    	user.setUsername(request.getParameter("addUserName"));
    	user.setPassword(request.getParameter("addUserPassword"));
    	user.setRole_id(Integer.valueOf(request.getParameter("roleListSelect")));
    	user.setDepartment_id(Integer.valueOf(request.getParameter("departmentListSelect")));
    	user.setStatus("1");
    	user.setGender(0);
    	user.setTel("1");
    	user.setReal_name("1");
    	user.setRole_list("1");
    	user.setReal_name("1");
    	user.setRole_name("1");
    	user.setCreate_time(new Date());
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
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
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
    }
    
    
    //修改
    @RequestMapping("editUser")
    public ModelAndView editUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	System.out.println("进入方法--------------");
//    	request.setCharacterEncoding("UTF-8");    	
//        response.setCharacterEncoding("UTF-8");
    	ModelAndView maView = new ModelAndView();
//    	String theme_name = java.net.URLDecoder.decode(request.getParameter("theme"),"UTF-8");   	
//    	String theme_name="%"+search+"%";
    	User user=new User();
    	user.setId(Integer.parseInt(request.getParameter("editUserID")));
    	user.setPassword(request.getParameter("editUserPassword"));
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
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
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
    }
    
    //删除
    @RequestMapping("deleteUser")
    public ModelAndView deleteUser(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	ModelAndView maView = new ModelAndView();
    	int id = Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("id"),"UTF-8"));
    	System.out.print("id:"+id);
    	userService.delete(id);
    	
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
        System.out.println(page.getDbIndex());
        System.out.println(page.getDbNumber());
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
  	@RequestMapping("loginUser")
  	public String  login(@RequestParam("username") String username,
  			@RequestParam("password") String password,Model model){
  		User user = new User();
  		user.setUsername(username);
  		user.setPassword(password);
  		System.out.println("用户输入username:"+username);
  		System.out.println("用户输入password:"+password);
  		if(userService.logincheck(user) != null){
  			model.addAttribute("error",username);
  			//日志
  			SysLog  sysLog=new SysLog();
  			User newUser=userService.getByName(user.getUsername());
  			sysLog.setOperate_user_id(newUser.getId());
  			sysLog.setOperate("Login");
  			sysLog.setMethod("com.wuhan_data.controller.UserController.login");
  			sysLog.setCreate_time(new Date());
  			sysLogService.add(sysLog);
  			//菜单生成
  			List<MenuList> menuList=menuService.getMenu(newUser.getRole_name());
  			System.out.println(menuList);
  			
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
