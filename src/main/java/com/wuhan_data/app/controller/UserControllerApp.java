package com.wuhan_data.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.httpclient.HttpException;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.UserServiceApp;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.UserService;
import com.wuhan_data.tools.SendMessage;

import jdk.nashorn.internal.ir.ReturnNode;

@Controller
@RequestMapping("")
public class UserControllerApp {
	@Autowired
	UserServiceApp userServiceApp;
	
//	@Autowired
//	UserService userService;
	
	@RequestMapping(value="ttt",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String ttt(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json)throws Exception
	{
		Map mapReturn=new HashMap();
		System.out.println("nihao");
		System.out.println(json);
		JSONObject jsonObject = JSONObject.fromObject(json);
	    Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	   // int id=Integer.valueOf(mapget.get("id").toString());
	    String head=mapget.get("head").toString();
	    System.out.println(head);
//	    String telString=map.get("tel").toString();
//	    String password=map.get("password").toString();
//	    System.out.println("tel="+telString+"password="+password);
		mapReturn.put("code",1);
		mapReturn.put("msg", "登录成功");
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	//接口 注册（获取验证码）
	@RequestMapping(value="sendSMS",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String sendSMS(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		String tel=request.getParameter("tel");
		//String tel="15172462980";;
	    System.out.println("sendSMS:"+"tel="+tel);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("tel",tel);
		if (userServiceApp.telIsExist(map)==0)//号码没有注册
		{
			HashMap<String, String> m = SendMessage.getMessageStatus(tel); //应用发送短信接口  
			String result = m.get("result");//获取到result值  
			//String result="1";
			if (result.trim().equals("1")) 
			{
				String code = m.get("code");
				//String code="123456";
				System.out.println(code+"============================================================================");
				HttpSession session = request.getSession(); //设置session  
				session.setAttribute(tel+"code", code); //将短信验证码放到session中保存  
				session.setMaxInactiveInterval(60 * 3); //缓存设置3分钟
				mapReturn.put("code", 1);
				mapReturn.put("msg","短信发送成功");		
			}
			else{
				mapReturn.put("code", 0);
				mapReturn.put("msg","短信发送失败");
			}
				
		}
		else {
			mapReturn.put("code", 2);
			mapReturn.put("msg","手机已经被注册");
		}
		String param=JSON.toJSONString(mapReturn);
		return param;

	}
	//接口 注册（注册）
	@RequestMapping(value="userRegisterlAPP",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String userRegisterlAPP(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map mapReturn=new HashMap();	
		JSONObject jsonObject = JSONObject.fromObject(json);
	    Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	    String tel=mapget.get("tel").toString();
	    String code=mapget.get("verCode").toString();
	    String password=mapget.get("password").toString();		
//		String tel="15926368971";
//		String code="521117";
//		String password="123456";
		HttpSession session = request.getSession();//设置session
		String sessioncode =(String) session.getAttribute(tel+"code");
		System.out.println("userRegisterlAPP:"+"code"+sessioncode);
		if((code).equals(sessioncode)){//比对缓存
		//注册
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("tel",tel);
			map.put("password",password);
			userServiceApp.register(map);
			mapReturn.put("code",1);
			mapReturn.put("msg","注册成功");
			mapReturn.put("id",userServiceApp.getByTel(tel).getId());
		}
		else{
			mapReturn.put("code",0);
			mapReturn.put("msg","注册失败");
			mapReturn.put("id", null);	
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	//接口 登录
	@RequestMapping(value="loginchekByTel",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String loginchekByTel(HttpServletRequest request, 
            HttpServletResponse response ,@RequestBody String json) throws Exception
	{
		Map mapReturn=new HashMap();
		List data=new ArrayList();
		JSONObject jsonObject = JSONObject.fromObject(json);
	    Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	    String tel=mapget.get("tel").toString();
	    String password=mapget.get("password").toString();
		System.out.println("loginchekByTel="+"tel:"+tel+"password"+password);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("tel", tel);
		map.put("password",password);
		if (userServiceApp.logincheckByTel(map)==null)
		{
			mapReturn.put("code", 0);
			mapReturn.put("msg","用户名密码错误");
			mapReturn.put("id",null);
		}
		else {
			User user=userServiceApp.logincheckByTel(map);
			HttpSession session=request.getSession();
			session.setAttribute("uid", user.getId());
			mapReturn.put("code",1);
			mapReturn.put("msg","用户名密码正确");
			mapReturn.put("id",user.getId());
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	//接口 忘记密码(获取验证码)
	@RequestMapping(value="sendSMSForgetPassword",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String sendSMSForgetPassword(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		String tel=request.getParameter("tel");
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("tel",tel);
		System.out.println("sendSMSForgetPassword="+"tel:"+tel);
		if (userServiceApp.telIsExist(map)!=0)//号码被注册注册
		{
			HashMap<String, String> m = SendMessage.getMessageStatus(tel); //应用发送短信接口  
			String result = m.get("result");//获取到result值  
			//String result="1";
			if (result.trim().equals("1")) 
			{
				String code = m.get("code");
				//String code="123456";
				System.out.println(code+"============================================================================");
				HttpSession session = request.getSession(); //设置session  
				session.setAttribute("ForgetPassword"+tel+"code", code); //将短信验证码放到session中保存  
				session.setMaxInactiveInterval(60 * 3); //缓存设置3分钟
				mapReturn.put("code", 1);
				mapReturn.put("msg","短信发送成功");
			}
			else{
				mapReturn.put("code", 0);
				mapReturn.put("msg","短信发送失败");
			}
		}
		else {
			mapReturn.put("code", 2);
			mapReturn.put("msg","手机没有注册");
		}
		String param=JSON.toJSONString(mapReturn);
		return param;

	}
	
	
	//接口 忘记密码（验证）
	
	@RequestMapping(value="checkSMSForgetPassword",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String checkSMSForgetPassword(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		List data=new ArrayList();
		String tel=request.getParameter("tel");
		String code=request.getParameter("verCode");
		//String tel="";
		//String code="";
		HttpSession session = request.getSession();//设置session
		String sessioncode =(String) session.getAttribute("ForgetPassword"+tel+"code");
		System.out.println("checkSMSForgetPassword="+"tel:"+tel+"code:"+code);
		if (code.trim().equals(sessioncode.trim()))
		{
			mapReturn.put("code", 1);
			mapReturn.put("msg","验证码正确");
			mapReturn.put("id",userServiceApp.getByTel(tel).getId());
		}
		else
		{
			mapReturn.put("code", 0);
			mapReturn.put("msg","验证码错误");
			mapReturn.put("id",null);
		}
		
		String param=JSON.toJSONString(mapReturn);
		return param;

	}
	//接口 修改密码
	@RequestMapping(value="changePassword",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String changePassword(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map mapReturn=new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
	    Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	    int id =Integer.valueOf(mapget.get("id").toString());
	    String password=mapget.get("password").toString();
	    System.out.println("changePassword="+"id:"+id+"password:"+password);
//		String tel="15926368971";
//		String password="123456";
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("id",id);
		map.put("password",password);
		if(userServiceApp.updatePasswordById(map)!=0){//比对缓存
			mapReturn.put("code",1);
			mapReturn.put("msg","密码修改成功");
		}
		else{
			mapReturn.put("code",0);
			mapReturn.put("msg","密码修改失败");
			
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	// 接口 我的主页
	@RequestMapping(value="homePage",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String homePage(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		//int id=22;
	    int id=Integer.valueOf(request.getParameter("id"));
	    System.out.println("homePage 接口"+"id="+id);
		Map mapReturn=new HashMap();
		User user=userServiceApp.get(id);
		if (user!=null)
		{
			mapReturn.put("code",1);;
			mapReturn.put("msg", "获取信息成功");
			String head=user.getHead();
			String realName=user.getReal_name();
			String rank=user.getRole_id();
			String tel=user.getTel();
			List list=new ArrayList();
			Map map1=new HashMap();
			map1.put("head",head);
			map1.put("realName",realName);
			map1.put("rank", rank);
			map1.put("tel", tel);
			list.add(map1);
			mapReturn.put("data", list);			
		}
		else {
			mapReturn.put("code",0);
			mapReturn.put("msg","获取信息失败,错误的id");
			List list=new ArrayList();
			mapReturn.put("data", list);
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	//接口 个人信息
	@RequestMapping(value="personalInformation",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String personalInformation(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		int id=Integer.valueOf(request.getParameter("id"));
		//int id =22;
		System.out.println("personalInformation="+"id:"+id);
		User user=userServiceApp.get(id);
		if (user!=null)
		{
			String head=user.getHead();
			String realName=user.getReal_name();
			String rank=user.getRole_id();
			String tel=user.getTel();
			int gender=user.getGender();
			String sex="";
			if (gender==0)
				sex="女";
			else {
				sex="男";
			}
			Date birthday=user.getBirthday();
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd"); 
			String birth=formatter.format(birthday);
			String city=user.getCity();
			String description=user.getDescription();
			List list=new ArrayList();
			Map map1=new HashMap();
			map1.put("head", head);
			map1.put("realName", realName);
			map1.put("rank", rank);
			map1.put("tel",tel);
			map1.put("sex", sex);
			map1.put("birth",birth);
			map1.put("city", city);
			map1.put("description", description);
			list.add(map1);
			mapReturn.put("code",1);;
			mapReturn.put("msg", "获取信息成功");
			mapReturn.put("data",list);
		}
		else {
			mapReturn.put("code",0);
			mapReturn.put("msg","获取信息失败");
			List list=new ArrayList();
			mapReturn.put("data",list);
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	//更换头像  在UpImagesController中
	
	
	//接口 更换手机号（获取验证码)
	@RequestMapping(value="sendSMSChangeTel",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String sendSMSChangeTel(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
	{
		Map mapReturn=new HashMap();
		String tel=request.getParameter("tel");
		//String tel="15926368971";
		System.out.println("sendSMSChangeTel="+"tel:"+tel);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("tel",tel);
		if (userServiceApp.telIsExist(map)==0)//号码没有注册
		{
			HashMap<String, String> m = SendMessage.getMessageStatus(tel); //应用发送短信接口  
			String result = m.get("result");//获取到result值  
			//String result="1";
			if (result.trim().equals("1")) 
			{
				String code = m.get("code");
				//String code="123456";
				System.out.println(code+"============================================================================");
				HttpSession session = request.getSession(); //设置session  
				session.setAttribute("changeTel"+tel+"code", code); //将短信验证码放到session中保存  
				session.setMaxInactiveInterval(60*3); //缓存设置3分钟
				mapReturn.put("code", 1);
				mapReturn.put("msg","短信发送成功");	
			}
			else{
				mapReturn.put("code", 0);
				mapReturn.put("msg","短信发送失败");
			}	
		}
		else {
			mapReturn.put("code", 2);
			mapReturn.put("msg","手机已经被注册");
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	
	// 接口 更换手机号（验证）
	@RequestMapping(value="changeTel",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String changeTel(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		Map mapReturn=new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
	    Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	    int id =Integer.valueOf(mapget.get("id").toString());
	    String tel=mapget.get("tel").toString();
	    String code=mapget.get("verCode").toString();
//	    int id=22;
//		String tel="15926368971";
//		String code="521117";
		
		System.out.println("changeTel="+"id"+id+"tel:"+tel+"code:"+code);
		HttpSession session = request.getSession();//设置session
		String sessioncode =(String) session.getAttribute("changeTel"+tel+"code");
		if((code).equals(sessioncode)){//比对缓存
		//改tel
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("tel",tel);
			map.put("id",id);
			if(userServiceApp.updateTelById(map)!=0)
			{
				mapReturn.put("code",1);
				mapReturn.put("msg","tel修改成功");
			}
			else {
				mapReturn.put("code",0);
				mapReturn.put("msg","tel修改失败");
			}
		}
		else{
			mapReturn.put("code",2);
			mapReturn.put("msg","验证码错误");
		}
		String param=JSON.toJSONString(mapReturn);
		return param;

	
	}
	
	//接口 个人信息修改
	@RequestMapping(value="changeInfo",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String changeInfo(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody String json) throws Exception
	{
		System.out.println("changeInfo="+"json"+json);
		Map mapReturn=new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
	    Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
	    int id =Integer.valueOf(mapget.get("id").toString());
	    String realName=mapget.get("realName").toString();
	    realName = URLDecoder.decode(realName, "utf-8");
	    System.out.println(realName);
	    String sex=mapget.get("sex").toString();
	    sex=URLDecoder.decode(sex,"utf-8");
	    String birthday=mapget.get("birth").toString();
	    String city=mapget.get("city").toString();
	    city=URLDecoder.decode(city,"utf-8");
	    String description=mapget.get("description").toString();
	    description=URLDecoder.decode(description,"utf-8");
	    
	    int gender=0;
	    if (sex.trim().equals("女"))
	    {
	    	gender=0;
	    }
	    else {
			gender=1;
		}
	    Date birth=new Date();
	    try {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	birth=formatter.parse(birthday);
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}	
//		int  id=Integer.valueOf(request.getParameter("id"));
//		String realName=request.getParameter("realName");
//		String sex=request.getParameter("sex");
//		Date birth=new Date();
//		int gender=0;
//		if( sex.trim().equals("女"))
//			gender=0;
//		else {
//			gender=1;
//		}
//
//    	try {
//        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        	Date birthday=formatter.parse(request.getParameter("birth"));
//        	birth=birthday;
//    		} catch (Exception e) {
//    			// TODO: handle exception
//    			e.printStackTrace();
//    		}
//    	String city=request.getParameter("city");
//    	String description=request.getParameter("description");
    	User user=userServiceApp.get(id);
    	user.setReal_name(realName);
    	user.setGender(gender);
    	user.setBirthday(birth);
    	user.setCity(city);
    	user.setDescription(description);
    	System.out.println("user="+user.toString());
    	if (userServiceApp.updata(user)!=0)
    	{
    		mapReturn.put("code",1);
			mapReturn.put("msg","用户信息修改成功");
    	}
    	else {
    		mapReturn.put("code",0);
			mapReturn.put("msg","用户信息修改失败");
		}
    	String param=JSON.toJSONString(mapReturn);
		return param;
	}

}
