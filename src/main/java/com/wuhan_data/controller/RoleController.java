package com.wuhan_data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.And;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.Admin;
import com.wuhan_data.pojo.Department;
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.UserService;
import com.wuhan_data.tools.Page;

@Controller
@RequestMapping("")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
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
	//code是否存在
	 @RequestMapping(value="roleCodeIsExist",produces="application/json;charset=utf-8")
	 @ResponseBody
	 public String codeIsExist(HttpServletRequest request, 
	            HttpServletResponse response) {
		 	JSONObject jsonObject = new JSONObject();
	    	String code="";
	    	try {
				code=URLDecoder.decode(request.getParameter("roleCode"),"utf-8");
				System.out.println("code"+code);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("codeIsExist参数获取异常"+e.getStackTrace());
				sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			}
	    	try {
	    		Map map=new HashMap();
	    		map.put("role_code", code);
	    		List<Role> roles=roleService.getByCode(map);
	    		System.out.println("nihao"+roles.size());
	    		if (roles.size()>0) {
					jsonObject.put("data", "exist");
				}
	    		else {
					jsonObject.put("data", "notExist");
				}
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		System.out.println("codeIsExist数据库操作异常"+e.getStackTrace());
	    		sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
	    	}
	    	return jsonObject.toString();
	    }
	//name是否存在
	 @RequestMapping(value="roleNameIsExist",produces="application/json;charset=utf-8")
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
				sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			}
	    	try {
	    		Role role=roleService.getByName(name);
	    		if (role!=null) {
					jsonObject.put("data", "exist");
				}
	    		else {
					jsonObject.put("data", "notExist");
				}
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		System.out.println("codeIsExist数据库操作异常"+e.getStackTrace());
	    		sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
	    	}
	    	return jsonObject.toString();
	    }
	 @RequestMapping(value="editRoleNameIsExist",produces="application/json;charset=utf-8")
	 @ResponseBody
	 public String editRoleNameIsExist(HttpServletRequest request, 
	            HttpServletResponse response) {
		 	JSONObject jsonObject = new JSONObject();
	    	String name="";
	    	int id=0;
	    	try {
				name=URLDecoder.decode(request.getParameter("roleName"),"utf-8");
				id=Integer.valueOf(URLDecoder.decode(request.getParameter("roleID"),"utf-8"));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("codeIsExist参数获取异常"+e.getStackTrace());
				sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			}
	    	try {
	    		
	    		Role role=roleService.getByName(name);
	    		
	    		if (role!=null) {
	    			if(role.getId()!=id)
	    				jsonObject.put("data", "exist");
	    			else {
	    				jsonObject.put("data", "notExist");
					}
					
				}
	    		else {
					jsonObject.put("data", "notExist");
				}
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		System.out.println("codeIsExist数据库操作异常"+e.getStackTrace());
	    		sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
	    	}
	    	return jsonObject.toString();
	    }
	 //将id转为name
	 @RequestMapping(value="roleNameList",produces="application/json;charset=utf-8")
	 @ResponseBody
	 public String roleNameList(HttpServletRequest request, 
	            HttpServletResponse response) {
		 JSONObject jsonObject = new JSONObject();
	    	String roleIdList="";
	    	try {
				roleIdList=URLDecoder.decode(request.getParameter("roleIdList"),"utf-8");
				System.out.println("roleIdList"+roleIdList);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("roleNameList参数获取异常"+e.getStackTrace());
				sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			}
	    	try {
	    		String[] ids=roleIdList.split("\\|");
	    		String[] names=new String[ids.length];
	    		for(int i=0;i<ids.length;i++)
	    		{
	    			Role role=roleService.get(Integer.valueOf(ids[i]));
	    			names[i]=role.getRole_name();
	    		}
	    		String result=StringUtils.join(names,"|");
	    		jsonObject.put("data",result);
	    	} catch (Exception e) {
	    		// TODO: handle exception
	    		System.out.println("codeIsExist数据库操作异常"+e.getStackTrace());
	    		sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
	    	}
	    	return jsonObject.toString();
		 
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
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
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
            
            maView.addObject("power_1",roleService.getThemeLists());
            maView.addObject("power_2",roleService.getIndexSpecials());
            maView.addObject("power_3",roleService.getIndexManages());
            maView.addObject("power_4",roleService.getIndexManages2());
            
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleInit:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
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
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
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
	        maView.addObject("power_1",roleService.getThemeLists());
	        maView.addObject("power_2",roleService.getIndexSpecials());
            maView.addObject("power_3",roleService.getIndexManages());
            maView.addObject("power_4",roleService.getIndexManages2());
            
	        maView.addObject("roleListByPage", roleListByPage);
	        maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
	        maView.addObject("page", page); 
	    	maView.setViewName("role");
	    	return maView;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleListByPage:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
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
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			mav.setViewName("error");
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
             mav.addObject("power_1",roleService.getThemeLists());
             mav.addObject("power_2",roleService.getIndexSpecials());
             mav.addObject("power_3",roleService.getIndexManages());
             mav.addObject("power_4",roleService.getIndexManages2());
             
             mav.addObject("roleListByPage", roleListByPage);  
             mav.addObject("page", page);
             mav.addObject("controlURL", "roleSearchPage");//控制页码传递URL
             mav.setViewName("role");   
             return mav;	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleSearchByName:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			mav.setViewName("error");
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
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			mav.setViewName("error");
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
            mav.addObject("power_1",roleService.getThemeLists());
            mav.addObject("power_2",roleService.getIndexSpecials());
            mav.addObject("power_3",roleService.getIndexManages());
            mav.addObject("power_4",roleService.getIndexManages2());
            mav.addObject("roleListByPage", roleListByPage);  
            mav.addObject("page", page);
            mav.addObject("controlURL", "roleSearchPage");//控制页码传递URL
            mav.setViewName("role");           
            return mav;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("roleSearchPage:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			mav.setViewName("error");
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
    	String addRoleCodeString="0000";
    	String addRoleNameString="";
    	String addRoleDescriptionString="";
    	String currentPage="";
    	String power_1="";
    	String power_2="";
    	String power_3="";
    	String power_4="";
    	try {
//    		addRoleCodeString=request.getParameter("addRoleCode");
    		addRoleNameString=request.getParameter("addRoleName");
    		addRoleDescriptionString=request.getParameter("addRoleDescription");
    		currentPage=request.getParameter("currentPage");
    		power_1=StringUtils.join(request.getParameterValues("addPower_1"),"|");
    		power_2=StringUtils.join(request.getParameterValues("addPower_2"),"|");
    		power_3=StringUtils.join(request.getParameterValues("addPower_3"),"|");
    		power_3=StringUtils.join(request.getParameterValues("addPower_3"),"|");
//    		power_4=StringUtils.join(request.getParameterValues("addPower_4"),"|");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addRole:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		power_4=power_3;
    		Role role=new Role();
        	role.setRole_code(addRoleCodeString);
        	role.setRole_name(addRoleNameString);
        	role.setRole_description(addRoleDescriptionString);
        	role.setRole_power_1(power_1);
        	role.setRole_power_2(power_2);
        	role.setRole_power_3(power_3);
        	role.setRole_power_4(power_4);
        	
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
            maView.addObject("power_1",roleService.getThemeLists());
            maView.addObject("power_2",roleService.getIndexSpecials());
            maView.addObject("power_3",roleService.getIndexManages());
            maView.addObject("power_4",roleService.getIndexManages2());
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("addRole:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
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
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		//只有用户中没有这个角色才可以删除
    		if(userService.isExistRoleId(id)==false)
    		{
    			roleService.delete(id);
    		}
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
            maView.addObject("power_1",roleService.getThemeLists());
            maView.addObject("power_2",roleService.getIndexSpecials());
            maView.addObject("power_3",roleService.getIndexManages());
            maView.addObject("power_4",roleService.getIndexManages2());
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteRole:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
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
    	String editRoleCodeString="0000";
    	String editRoleNameString="";
    	String editRoleDescriptionString="";
    	String power_1="";
    	String power_2="";
    	String power_3="";
    	String power_4="";
    	try {
    		editRoleID=Integer.valueOf(request.getParameter("editRoleID"));
//    		editRoleCodeString=request.getParameter("editRoleCode");
    		editRoleNameString=request.getParameter("editRoleName");
    		editRoleDescriptionString=request.getParameter("editRoleDescription");
    		currentPage=request.getParameter("currentPage");
    		power_1=StringUtils.join(request.getParameterValues("editPower_1"),"|");
    		power_2=StringUtils.join(request.getParameterValues("editPower_2"),"|");
    		power_3=StringUtils.join(request.getParameterValues("editPower_3"),"|");
//    		power_4=StringUtils.join(request.getParameterValues("editPower_4"),"|");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editRole:获取数据"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "请求参数异常", e);
			maView.setViewName("error");
			return maView;
		}
    	//数据库操作
    	try {
    		power_4=power_3;
    		
    		Role role=new Role();
        	role.setId(editRoleID);
        	role.setRole_code(editRoleCodeString);
        	role.setRole_name(editRoleNameString);
        	role.setRole_description(editRoleDescriptionString);
        	role.setRole_power_1(power_1);
        	role.setRole_power_2(power_2);
        	role.setRole_power_3(power_3);
        	role.setRole_power_4(power_4);
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
            maView.addObject("power_1",roleService.getThemeLists());
            maView.addObject("power_2",roleService.getIndexSpecials());
            maView.addObject("power_3",roleService.getIndexManages());
            maView.addObject("power_4",roleService.getIndexManages2());
            maView.addObject("roleListByPage", roleListByPage);
            maView.addObject("controlURL", "roleListByPage");//控制页码传递URL
            maView.addObject("page", page); 
        	maView.setViewName("role");
        	return maView;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("editRole:数据库操作"+e.toString());
			sysLogService.addAdmin(request, request.getRequestURL().toString(), "数据库操作异常", e);
			maView.setViewName("error");
			return maView;
		}
    	
    }
    

}
