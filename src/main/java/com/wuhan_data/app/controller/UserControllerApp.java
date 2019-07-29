package com.wuhan_data.app.controller;

import java.io.File;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.context.request.RequestAttributes;  
import org.springframework.web.context.request.RequestContextHolder;  
import org.springframework.web.context.request.ServletRequestAttributes;  
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import net.sf.json.JSONObject;

import com.alibaba.druid.sql.parser.Token;
import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.app.service.UserServiceApp;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.UserService;
import com.wuhan_data.tools.ImageUtils;
import com.wuhan_data.tools.SendMessage;
import com.wuhan_data.tools.SessionApp;
import com.wuhan_data.tools.StringToMap;
import com.wuhan_data.tools.TokenUtil;



@Controller
@RequestMapping("")
public class UserControllerApp {
	@Autowired
	UserServiceApp userServiceApp;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	@RequestMapping(value = "loginTest", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String loginTest(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws Exception {
		Map mapReturn = new HashMap();
		mapReturn.put("errCode","0");
		mapReturn.put("errMsg", "登录成功");
//		String valueString=sessionSQLServiceApp.get("a6daa3840f2425799be009ea4c573713").getSess_value();
//		Map map=StringToMap.stringToMap(valueString);
//		mapReturn.put("data", map);
		String param = JSON.toJSONString(mapReturn);
		System.out.println("测试login接口返回:" + param);
		return param;
	}
	
	
	

	// 接口获取验证码
	@RequestMapping(value = "getVercodeApp2", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String getVercode2(HttpServletRequest request, HttpServletResponse response,@RequestBody String json) throws Exception {
		Map mapReturn = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		String tel = mapget.get("tel").toString();
		System.out.println("获取验证码接口：sendSMS:" + "tel=" + tel);
		if (sessionSQLServiceApp.isTimeOut(tel+"verCode", 60)==false) {
			mapReturn.put("errCode","-2");
			mapReturn.put("errMsg", "一分钟请勿重复申请验证码");
		} else {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tel", tel);
			HashMap<String, String> m = SendMessage.getMessageStatus(tel); // 应用发送短信接口  
			String result = m.get("result");// 获取到result值  
			if (result.trim().equals("1")) // 发送成功
			{
				String code = m.get("code");
			   // 将短信验证码放到session中保存  
				sessionSQLServiceApp.set(tel + "verCode", code);
				System.out.println("发送的验证码："+"code"+code);
				mapReturn.put("errCode","0");
				mapReturn.put("errMsg", "短信发送成功");
			} else {
				mapReturn.put("errCode","-2");
				mapReturn.put("errMsg", "短信发送失败");
			}
		}
		String param = JSON.toJSONString(mapReturn);
		System.out.println("获取验证码接口:" + param);
		return param;

	}

	// 接口获取验证码
	@RequestMapping(value = "getVercodeApp", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String getVercode(HttpServletRequest request, HttpServletResponse response,@RequestBody String json) throws Exception {
		Map data = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		//获取参数
		String tel ="";
		try {
			tel=mapget.get("tel").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-2", "请求参数获取异常", data);
		}		
		System.out.println("获取验证码接口：sendSMS:" + "tel=" + tel);
		
		//是否一分钟内重复申请
		boolean isTimeout=false;
		try {
			isTimeout=sessionSQLServiceApp.isTimeOut(tel+"verCode", 60);	
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-1", "数据库错误", data);
		}

		if (isTimeout==false) {
			return this.apiReturn("-2", "一分钟请勿重复申请验证码", data);
		}
		else {
			//短信发送服务
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tel", tel);
				HashMap<String, String> m = SendMessage.getMessageStatus(tel); // 应用发送短信接口  
				String result = m.get("result");// 获取到result值  
				if (result.trim().equals("1")) // 发送成功
				{
					String code = m.get("code");
				   // 将短信验证码放到session中保存  
					sessionSQLServiceApp.set(tel + "verCode", code);
					System.out.println("发送的验证码："+"code"+code);
					return this.apiReturn("0", "短信发送 成功", data);
				} else {
					return this.apiReturn("-2", "短信发送失败", data);
				}
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "短信发送异常", data);
			}
			
		}
	}

	// 接口登录
	@RequestMapping(value = "loginaa", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws Exception {
		Map data = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		String tel="";
		String verCode="";
		//参数获取
		try {
			tel = mapget.get("tel").toString();
			verCode = mapget.get("verCode").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-2", "请求参数获取异常", data);
		}
		
		System.out.println("登录接口:获取的参数为" + "tel" + tel + "verCode" + verCode);
		String sessioncode="";
		try {
			sessioncode = (String) sessionSQLServiceApp.get(tel+"verCode").getSess_value();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-1", "session获取异常", data);
		}
		// 对比缓存是否相同+
		if ((verCode).equals(sessioncode)) {
			//获取用户信息
			try {
				// 判断是否为新用户
				String errMsg="";
				System.out.println("laiji");
				if (userServiceApp.getByTel(tel) == null) {
					User user=new User();
					user.setTel(tel);
					//设置头像路径
					System.out.println("laiji22");
					String headString=ImageUtils.getURL(request);
					user.setHead(headString+"heads/default.jpg");
					System.out.println(user.toString());
					userServiceApp.add(user);
					System.out.println(user.toString());
					errMsg="新用户登录成功";	
				} else {
					errMsg="用户登录成功";
					// 将用户的信息加到session中，以token为key，对应的职位
				}
				// 将对应用户的信息加到data中
				User user = userServiceApp.getByTel(tel);
				// 生成token令牌
				String tokenString = TokenUtil.getToken(tel + new Date().toString());
				String idString = String.valueOf(user.getId());
				String telString = user.getTel();
				String realNameString = user.getReal_name();
				String genderString = "女";
				if (user.getGender() == 0) {
					genderString = "女";
				} else {
					genderString = "男";
				}
				String headString = user.getHead();
				Date birth = user.getBirthday();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String birthdayString = formatter.format(birth);
				String cityString = user.getCity();
				String descriptionString = user.getDescription();
				String deparmentString = user.getDepartment_id();// 这不是id，就是name懒得改了
				String roleNameString = user.getRole_id();
				data.put("token", tokenString);
				data.put("userId", idString);
				data.put("tel", telString);
				data.put("realName", realNameString);
				data.put("gender", genderString);
				data.put("head", headString);
				data.put("birthday", birthdayString);
				data.put("city", cityString);
				data.put("description", descriptionString);
				data.put("department", deparmentString);
				data.put("roleName", roleNameString);
				// 将用户的信息加到session中，以token为key，对应的职位
				sessionSQLServiceApp.set(tokenString, data.toString());
				return this.apiReturn("0", errMsg, data);
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "数据库获取异常", data);
			}
			// 没有设置保存多长时间会不会有问题
		} else {
			return this.apiReturn("-2", "手机号或者验证码不正确", data);
		}
	}

	
	  //接口获取用户个人信息
	  
	  @RequestMapping(value="getUserApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	  @ResponseBody public String getUser(HttpServletRequest request,HttpServletResponse response,@RequestBody String json)throws Exception 
	  { 
		Map data=new HashMap(); 
	  	JSONObject jsonObject =JSONObject.fromObject(json); 
	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
	  	//获取数据
	  	String tokenString="";
	  	try {
	  		 tokenString=mapget.get("token").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-2", "请求参数异常", data);
		}
	  	System.out.println("获取用户个人信息接口："+"token"+tokenString);
	  	
	  	//token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-1", "数据库异常", data);
		}
	  	
	  	if(tokenIsEmpty)
	  	{
	  		return this.apiReturn("-3", "token令牌错误", data);
	  	}
	  	else {
	  		//获取用户数据
	  		try {
	  			String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
		  		Map map=StringToMap.stringToMap(mapString);
		  		String tel=(String)map.get("tel");
		  		User user = userServiceApp.getByTel(tel);//通过电话号码获取用户
		  		String idString = String.valueOf(user.getId());
				String telString = user.getTel();
				String realNameString = user.getReal_name();
				String genderString = "女";
				if (user.getGender() == 0) {
					genderString = "女";
				} else {
					genderString = "男";
				}
				String headString = user.getHead();
				Date birth = user.getBirthday();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String birthdayString = formatter.format(birth);
				String cityString = user.getCity();
				String descriptionString = user.getDescription();
				String deparmentString = user.getDepartment_id();// 这不是id，就是name懒得改了
				String roleNameString = user.getRole_id();
				data.put("userId", idString);
				data.put("tel", telString);
				data.put("realName", realNameString);
				data.put("gender", genderString);
				data.put("head", headString);
				data.put("birthday", birthdayString);
				data.put("city", cityString);
				data.put("description", descriptionString);
				data.put("department", deparmentString);
				data.put("roleName", roleNameString);
				return this.apiReturn("0", "用户信息获取成功", data);			
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "数据库操作错误", data);
			}
	  		
	  	}  
	  }
	//接口更换头像 ,在UPImagesControllerAPP中
	  
	//接口编辑用户信息
	  @RequestMapping(value="editUserApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	  @ResponseBody public String editUserApp(HttpServletRequest request,HttpServletResponse response,@RequestBody String json)throws Exception 
	{
			Map data=new HashMap(); 
			JSONObject jsonObject =JSONObject.fromObject(json); 
			Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
			//请求参数获取
			String tokenString="";
			String realName="";
			String genderString="";
			String birthday="";
			String city="";
			String description="";
			try {
				tokenString=mapget.get("token").toString();
				realName = mapget.get("realName").toString();
				realName = URLDecoder.decode(realName, "utf-8");
				genderString = mapget.get("gender").toString();
				genderString = URLDecoder.decode(genderString, "utf-8");
				birthday = mapget.get("birthday").toString();
				city = mapget.get("city").toString();
				city = URLDecoder.decode(city, "utf-8");
				description = mapget.get("description").toString();
				description = URLDecoder.decode(description, "utf-8");
				System.out.println("用户信息编辑接口:"+"realName"+realName+"description"+description);
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-2", "请求参数错误", data);
			}
			//token令牌验证
		  	Boolean tokenIsEmpty=true;
		  	try {
				tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "数据库异常", data);
			}
			
			if(tokenIsEmpty)
		  	{
				return this.apiReturn("-3", "token令牌错误", data);
		  	}
			else {
				//用户信息设置
				try {
					int gender = 0;
					if (genderString.trim().equals("女")) {
						gender = 0;
					} else {
						gender = 1;
					}
					Date birth = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						birth = formatter.parse(birthday);
					String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
			  		Map map=StringToMap.stringToMap(mapString);
			  		String tel=(String)map.get("tel");
					User user = userServiceApp.getByTel(tel);
					user.setReal_name(realName);
					user.setGender(gender);
					user.setBirthday(birth);
					user.setCity(city);
					user.setDescription(description);
					System.out.println("user"+user.toString());
					if (userServiceApp.updata(user) != 0) {
						return this.apiReturn("0", "用户信息修改成功", data);
					} else {
						return this.apiReturn("-1", "用户信息修改失败", data);
					}
				} catch (Exception e) {
					// TODO: handle exception
					return this.apiReturn("-1", "数据库操作异常", data);
				}		
			}	  
	  }
	//更换手机号
	@RequestMapping(value="changeTelApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody public String changeTel(HttpServletRequest request,HttpServletResponse response,@RequestBody String json)throws Exception 
	{
		Map data=new HashMap(); 
		JSONObject jsonObject =JSONObject.fromObject(json); 
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
		//获取参数
		String tokenString="";
		String newTel="";
		String verCode="";
		try {
			tokenString=mapget.get("token").toString();
			newTel=mapget.get("newTel").toString();
			verCode=mapget.get("verCode").toString();
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-2", "请求参数异常", data);
		}
		//token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			return this.apiReturn("-1", "数据库异常", data);
		}
		
		if(tokenIsEmpty)
	  	{
			return this.apiReturn("-3", "token令牌错误", data);
	  	}
		else {
			//获取数据
			try {
				//新手机号是否已经被注册
				if(userServiceApp.getByTel(newTel)!=null)
				{   //已经注册了
					return this.apiReturn("-2", "改手机号已经被注册", data);
				}
				else {
					//验证码是否正确
					String sessioncode = (String) sessionSQLServiceApp.get(newTel+"verCode").getSess_value();
					if((verCode).equals(sessioncode))
					{
						//验证码正确
						String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
				  		Map map=StringToMap.stringToMap(mapString);
				  		int id=Integer.valueOf((String)map.get("userId"));//获得旧手机号
				  		User user=userServiceApp.get(id);
				  		user.setTel(newTel);
				  		userServiceApp.updata(user);
				  		flashSession(tokenString);
				  		return this.apiReturn("0", "手机号修改成功", data);
					}
					else {
						return this.apiReturn("-2", "验证码不正确", data);
					}		
				}
			} catch (Exception e) {
				// TODO: handle exception
				return this.apiReturn("-1", "数据获取异常", data);
			}		
		}  
	}
	  
	  
	//刷新sessionid中的值
	  public void flashSession(String token)
	  {
		  String mapString=sessionSQLServiceApp.get(token).getSess_value();
	  		Map map=StringToMap.stringToMap(mapString);
	  		int id=Integer.valueOf((String)map.get("userId"));
	  		User user = userServiceApp.get(id);//通过id获取用户
	  		String idString = String.valueOf(user.getId());
			String telString = user.getTel();
			String realNameString = user.getReal_name();
			String genderString = "女";
			if (user.getGender() == 0) {
				genderString = "女";
			} else {
				genderString = "男";
			}
			String headString = user.getHead();
			Date birth = user.getBirthday();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String birthdayString = formatter.format(birth);
			String cityString = user.getCity();
			String descriptionString = user.getDescription();
			String deparmentString = user.getDepartment_id();// 这不是id，就是name懒得改了
			String roleNameString = user.getRole_id();
			List list = new ArrayList();
			Map map1 = new HashMap();
			map1.put("userId", idString);
			map1.put("tel", telString);
			map1.put("realName", realNameString);
			map1.put("gender", genderString);
			map1.put("head", headString);
			map1.put("birthday", birthdayString);
			map1.put("city", cityString);
			map1.put("description", descriptionString);
			map1.put("department", deparmentString);
			map1.put("roleName", roleNameString);
			list.add(map1);
			sessionSQLServiceApp.set(token, map1.toString());
			System.out.println("flash"+map1.toString());
		  
	  }
	  public String apiReturn(String errCode, String errMsg, Map<String, Object> data) {
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("errCode", errCode);
			responseMap.put("errMsg", errMsg);
			responseMap.put("data", data);
			return JSON.toJSONString(responseMap);
		}
	  
//	  //获取项目的地址
//	  public String getURL(HttpServletRequest request)
//	  {
//		  String urlString=request.getScheme()+"://192.168.124.11"+":"+request.getLocalPort()+"/"+"wuhan_data1/";
//		  return urlString;
//	  }
//
//	// 接口 注册（注册）
//	@RequestMapping(value = "userRegisterlAPP", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
//	@ResponseBody
//	public String userRegisterlAPP(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
//			throws Exception {
//		Map mapReturn = new HashMap();
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//		String tel = mapget.get("tel").toString();
//		String code = mapget.get("verCode").toString();
//		String password = mapget.get("password").toString();
////		String tel="15926368971";
////		String code="521117";
////		String password="123456";
//		HttpSession session = request.getSession();// 设置session
//		String sessioncode = (String) session.get(tel + "code");
//		System.out.println("userRegisterlAPP:" + "code" + sessioncode);
//		if ((code).equals(sessioncode)) {// 比对缓存
//			// 注册
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("tel", tel);
//			map.put("password", password);
//			userServiceApp.register(map);
//			mapReturn.put("code", 1);
//			mapReturn.put("msg", "注册成功");
//			mapReturn.put("id", userServiceApp.getByTel(tel).getId());
//		} else {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "注册失败");
//			mapReturn.put("id", null);
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	// 接口 登录
//	@RequestMapping(value = "loginchekByTel", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
//	@ResponseBody
//	public String loginchekByTel(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
//			throws Exception {
//		Map mapReturn = new HashMap();
//		List data = new ArrayList();
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//		String tel = mapget.get("tel").toString();
//		String password = mapget.get("password").toString();
//		System.out.println("loginchekByTel=" + "tel:" + tel + "password" + password);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tel", tel);
//		map.put("password", password);
//		if (userServiceApp.logincheckByTel(map) == null) {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "用户名密码错误");
//			mapReturn.put("id", null);
//		} else {
//			User user = userServiceApp.logincheckByTel(map);
//			HttpSession session = request.getSession();
//			session.put("uid", user.getId());
//			mapReturn.put("code", 1);
//			mapReturn.put("msg", "用户名密码正确");
//			mapReturn.put("id", user.getId());
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	// 接口 忘记密码(获取验证码)
//	@RequestMapping(value = "sendSMSForgetPassword", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
//	@ResponseBody
//	public String sendSMSForgetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map mapReturn = new HashMap();
//		String tel = request.getParameter("tel");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tel", tel);
//		System.out.println("sendSMSForgetPassword=" + "tel:" + tel);
//		if (userServiceApp.telIsExist(map) != 0)// 号码被注册注册
//		{
//			HashMap<String, String> m = SendMessage.getMessageStatus(tel); // 应用发送短信接口  
//			String result = m.get("result");// 获取到result值  
//			// String result="1";
//			if (result.trim().equals("1")) {
//				String code = m.get("code");
//				// String code="123456";
//				System.out
//						.println(code + "============================================================================");
//				HttpSession session = request.getSession(); // 设置session  
//				session.put("ForgetPassword" + tel + "code", code); // 将短信验证码放到session中保存  
//				session.setMaxInactiveInterval(60 * 3); // 缓存设置3分钟
//				mapReturn.put("code", 1);
//				mapReturn.put("msg", "短信发送成功");
//			} else {
//				mapReturn.put("code", 0);
//				mapReturn.put("msg", "短信发送失败");
//			}
//		} else {
//			mapReturn.put("code", 2);
//			mapReturn.put("msg", "手机没有注册");
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//
//	}
//
//	// 接口 忘记密码（验证）
//
//	@RequestMapping(value = "checkSMSForgetPassword", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
//	@ResponseBody
//	public String checkSMSForgetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map mapReturn = new HashMap();
//		List data = new ArrayList();
//		String tel = request.getParameter("tel");
//		String code = request.getParameter("verCode");
//		// String tel="";
//		// String code="";
//		HttpSession session = request.getSession();// 设置session
//		String sessioncode = (String) session.get("ForgetPassword" + tel + "code");
//		System.out.println("checkSMSForgetPassword=" + "tel:" + tel + "code:" + code);
//		if (code.trim().equals(sessioncode.trim())) {
//			mapReturn.put("code", 1);
//			mapReturn.put("msg", "验证码正确");
//			mapReturn.put("id", userServiceApp.getByTel(tel).getId());
//
//		} else {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "验证码错误");
//			mapReturn.put("id", null);
//		}
//
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//
//	}
//
//	// 接口 修改密码
//	@RequestMapping(value = "changePassword", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
//	@ResponseBody
//	public String changePassword(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
//			throws Exception {
//		Map mapReturn = new HashMap();
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//		int id = Integer.valueOf(mapget.get("id").toString());
//		String password = mapget.get("password").toString();
//		System.out.println("changePassword=" + "id:" + id + "password:" + password);
////		String tel="15926368971";
////		String password="123456";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", id);
//		map.put("password", password);
//		if (userServiceApp.updatePasswordById(map) != 0) {// 比对缓存
//			mapReturn.put("code", 1);
//			mapReturn.put("msg", "密码修改成功");
//		} else {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "密码修改失败");
//
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	// 接口 我的主页
//	@RequestMapping(value = "homePage", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
//	@ResponseBody
//	public String homePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// int id=22;
//		int id = Integer.valueOf(request.getParameter("id"));
//		System.out.println("homePage 接口" + "id=" + id);
//		Map mapReturn = new HashMap();
//		User user = userServiceApp.get(id);
//		if (user != null) {
//			mapReturn.put("code", 1);
//			;
//			mapReturn.put("msg", "获取信息成功");
//			String head = user.getHead();
//			String realName = user.getReal_name();
//			String rank = user.getRole_id();
//			String tel = user.getTel();
//			List list = new ArrayList();
//			Map map1 = new HashMap();
//			map1.put("head", head);
//			map1.put("realName", realName);
//			map1.put("rank", rank);
//			map1.put("tel", tel);
//			list.add(map1);
//			mapReturn.put("data", list);
//		} else {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "获取信息失败,错误的id");
//			List list = new ArrayList();
//			mapReturn.put("data", list);
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	// 接口 个人信息
//	@RequestMapping(value = "personalInformation", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
//	@ResponseBody
//	public String personalInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map mapReturn = new HashMap();
//		int id = Integer.valueOf(request.getParameter("id"));
//		// int id =22;
//		System.out.println("personalInformation=" + "id:" + id);
//		User user = userServiceApp.get(id);
//		if (user != null) {
//			String head = user.getHead();
//			String realName = user.getReal_name();
//			String rank = user.getRole_id();
//			String tel = user.getTel();
//			int gender = user.getGender();
//			String sex = "";
//			if (gender == 0)
//				sex = "女";
//			else {
//				sex = "男";
//			}
//			Date birthday = user.getBirthday();
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//			String birth = formatter.format(birthday);
//			String city = user.getCity();
//			String description = user.getDescription();
//			List list = new ArrayList();
//			Map map1 = new HashMap();
//			map1.put("head", head);
//			map1.put("realName", realName);
//			map1.put("rank", rank);
//			map1.put("tel", tel);
//			map1.put("sex", sex);
//			map1.put("birth", birth);
//			map1.put("city", city);
//			map1.put("description", description);
//			list.add(map1);
//			mapReturn.put("code", 1);
//			;
//			mapReturn.put("msg", "获取信息成功");
//			mapReturn.put("data", list);
//		} else {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "获取信息失败");
//			List list = new ArrayList();
//			mapReturn.put("data", list);
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	// 更换头像 在UpImagesController中
//
//	// 接口 更换手机号（获取验证码)
//	@RequestMapping(value = "sendSMSChangeTel", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
//	@ResponseBody
//	public String sendSMSChangeTel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map mapReturn = new HashMap();
//		String tel = request.getParameter("tel");
//		// String tel="15926368971";
//		System.out.println("sendSMSChangeTel=" + "tel:" + tel);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tel", tel);
//		if (userServiceApp.telIsExist(map) == 0)// 号码没有注册
//		{
//			HashMap<String, String> m = SendMessage.getMessageStatus(tel); // 应用发送短信接口  
//			String result = m.get("result");// 获取到result值  
//			// String result="1";
//			if (result.trim().equals("1")) {
//				String code = m.get("code");
//				// String code="123456";
//				System.out
//						.println(code + "============================================================================");
//				HttpSession session = request.getSession(); // 设置session  
//				session.put("changeTel" + tel + "code", code); // 将短信验证码放到session中保存  
//				session.setMaxInactiveInterval(60 * 3); // 缓存设置3分钟
//				mapReturn.put("code", 1);
//				mapReturn.put("msg", "短信发送成功");
//			} else {
//				mapReturn.put("code", 0);
//				mapReturn.put("msg", "短信发送失败");
//			}
//		} else {
//			mapReturn.put("code", 2);
//			mapReturn.put("msg", "手机已经被注册");
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	// 接口 更换手机号（验证）
//	@RequestMapping(value = "changeTel", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
//	@ResponseBody
//	public String changeTel(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
//			throws Exception {
//		Map mapReturn = new HashMap();
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//		int id = Integer.valueOf(mapget.get("id").toString());
//		String tel = mapget.get("tel").toString();
//		String code = mapget.get("verCode").toString();
////	    int id=22;
////		String tel="15926368971";
////		String code="521117";
//
//		System.out.println("changeTel=" + "id" + id + "tel:" + tel + "code:" + code);
//		HttpSession session = request.getSession();// 设置session
//		String sessioncode = (String) session.get("changeTel" + tel + "code");
//		if ((code).equals(sessioncode)) {// 比对缓存
//			// 改tel
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("tel", tel);
//			map.put("id", id);
//			if (userServiceApp.updateTelById(map) != 0) {
//				mapReturn.put("code", 1);
//				mapReturn.put("msg", "tel修改成功");
//			} else {
//				mapReturn.put("code", 0);
//				mapReturn.put("msg", "tel修改失败");
//			}
//		} else {
//			mapReturn.put("code", 2);
//			mapReturn.put("msg", "验证码错误");
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//
//	}
//
//	// 接口 个人信息修改
//	@RequestMapping(value = "changeInfo", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
//	@ResponseBody
//	public String changeInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
//			throws Exception {
//		System.out.println("changeInfo=" + "json" + json);
//		Map mapReturn = new HashMap();
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//		int id = Integer.valueOf(mapget.get("id").toString());
//		String realName = mapget.get("realName").toString();
//		realName = URLDecoder.decode(realName, "utf-8");
//		System.out.println(realName);
//		String sex = mapget.get("sex").toString();
//		sex = URLDecoder.decode(sex, "utf-8");
//		String birthday = mapget.get("birth").toString();
//		String city = mapget.get("city").toString();
//		city = URLDecoder.decode(city, "utf-8");
//		String description = mapget.get("description").toString();
//		description = URLDecoder.decode(description, "utf-8");
//
//		int gender = 0;
//		if (sex.trim().equals("女")) {
//			gender = 0;
//		} else {
//			gender = 1;
//		}
//		Date birth = new Date();
//		try {
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			birth = formatter.parse(birthday);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
////		int  id=Integer.valueOf(request.getParameter("id"));
////		String realName=request.getParameter("realName");
////		String sex=request.getParameter("sex");
////		Date birth=new Date();
////		int gender=0;
////		if( sex.trim().equals("女"))
////			gender=0;
////		else {
////			gender=1;
////		}
////
////    	try {
////        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////        	Date birthday=formatter.parse(request.getParameter("birth"));
////        	birth=birthday;
////    		} catch (Exception e) {
////    			// TODO: handle exception
////    			e.printStackTrace();
////    		}
////    	String city=request.getParameter("city");
////    	String description=request.getParameter("description");
//		User user = userServiceApp.get(id);
//		user.setReal_name(realName);
//		user.setGender(gender);
//		user.setBirthday(birth);
//		user.setCity(city);
//		user.setDescription(description);
//		System.out.println("user=" + user.toString());
//		if (userServiceApp.updata(user) != 0) {
//			mapReturn.put("code", 1);
//			mapReturn.put("msg", "用户信息修改成功");
//		} else {
//			mapReturn.put("code", 0);
//			mapReturn.put("msg", "用户信息修改失败");
//		}
//		String param = JSON.toJSONString(mapReturn);
//		return param;
//	}

}