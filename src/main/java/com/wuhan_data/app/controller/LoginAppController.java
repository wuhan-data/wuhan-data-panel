package com.wuhan_data.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.LoginAppService;
import com.wuhan_data.pojo.Admin;
import com.wuhan_data.tools.MenuList;
@Controller
@RequestMapping("")
public class LoginAppController {
	
	@Autowired
	LoginAppService loginAppService;
	
	  @RequestMapping("loginApp")
	  @ResponseBody
	    public String appLogin(HttpServletRequest request, 
	            HttpServletResponse response) throws IOException{
	    	
	    
	    	//需要从app端获得
//	    	String username=request.getParameter("username");
//	    	String password=request.getParameter("password");
	    	String username="18671687178";//需要从app端获得
	    	String password="111111";
	    	
	    	String pwDB= loginAppService.getPwByTel(username);
	    	System.out.println(pwDB);
	    	boolean l=false;
	    	if (password.equals(pwDB))
	    	{
	    		//maView.setViewName("login");
	    		//System.out.println("登录成功！");
	    		HttpSession session=request.getSession();
	    		session.setAttribute("username",username);
	    		l=true;
	    		
	    	}
	    	else {
	    		l=false;
	    		
	    		System.out.println("登录失败！");
			}
	    	Map map=new HashMap();
			map.put("l", l);
			String  param= JSON.toJSONString(map);
			return param;
	    }
	
	
	

}
