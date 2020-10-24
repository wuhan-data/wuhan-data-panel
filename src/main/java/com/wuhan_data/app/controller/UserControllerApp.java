package com.wuhan_data.app.controller;

import java.io.File;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
import com.wuhan_data.pojo.Role;
import com.wuhan_data.pojo.User;
import com.wuhan_data.service.DepartmentService;
import com.wuhan_data.service.RoleService;
import com.wuhan_data.service.SysLogService;
import com.wuhan_data.service.UserOpLogService;
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
	DepartmentService departmentService;
	@Autowired
	UserServiceApp userServiceApp;
	@Autowired
	RoleService roleService;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	@Autowired
	SysLogService sysLogService;
	@Autowired
	UserOpLogService userOpLogService;
	
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
			System.out.println("getVercodeApp"+e.toString());
			return this.apiReturn("-2", "请求参数获取异常", data);
		}		
		System.out.println("获取验证码接口：sendSMS:" + "tel=" + tel);
		
		//是否一分钟内重复申请
		boolean isTimeout=false;
		try {
			isTimeout=sessionSQLServiceApp.isTimeOut(tel+"verCode", 60);	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getVercodeApp"+e.toString());
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
				System.out.println("getVercodeApp"+e.toString());
				return this.apiReturn("-2", "短信发送异常", data);
			}
			
		}
	}

	/**
	 * 用户使用账号（手机号码）和密码进行登录
	 * @param request
	 * @param response
	 * @param json
	 * @return	登录errCode
	 * @throws Exception
	 */
	@RequestMapping(value = "loginaa", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws Exception {
		Map data = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		String telephoneNumber="";
		String password="";
		//参数获取
		try {
			telephoneNumber = mapget.get("tel").toString();
			password = mapget.get("password").toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getVercodeApp,错误类型："+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-3", "请求参数获取异常", data);
		}

		System.out.println("登录接口:获取的参数为" + "telephoneNumber:" + telephoneNumber + "password:" + password);

		// 对比缓存是否相同+
		if (true) {
			//获取用户信息
			try {
				// 判断是否为新用户
				String errMsg="";
				if(userServiceApp.getByTel(telephoneNumber) != null && userServiceApp.getByTel(telephoneNumber).getPassword().equals(password)){
					errMsg="用户登录成功";
					//0.1的概率删除过期session
					//生成随机数
					int max=100,min=1;
					int ran2 = (int) (Math.random()*(max-min)+min);
					if(ran2<10)
					{
						Date timeout=new Date();
						Calendar calendar=Calendar.getInstance();
						calendar.setTime(timeout);
						calendar.add(Calendar.DATE, -15);
						timeout=calendar.getTime();
						System.out.println("删除过期session条数："+sessionSQLServiceApp.deleteTimeoutToken(timeout));
					}

					// 将对应用户的信息加到data中
					User user = userServiceApp.getByTel(telephoneNumber);
					// 生成token令牌
					String tokenString = TokenUtil.getToken(telephoneNumber + new Date().toString());
					String idString = String.valueOf(user.getId());
					String telString = user.getTel();
					String passwordString = user.getPassword();
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
					deparmentString=departmentService.getNameList(deparmentString);
					roleNameString=roleService.getNameList(roleNameString);

					data.put("token", tokenString);
					data.put("userId", idString);
					data.put("tel", telString);
					data.put("password",passwordString);
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

					userOpLogService.addOp(Integer.valueOf(idString), "用户登录", request, request.getRequestURL().toString());
					return this.apiReturn("0", errMsg, data);
				}else {
					errMsg="手机号或密码错误！登录失败！";
					return this.apiReturn("-2", "手机号或者密码不正确", data);
				}

					// 将用户的信息加到session中，以token为key，对应的职位

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("getVercodeApp错误，错误类型"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库获取异常", e);
				return this.apiReturn("-1", "数据库获取异常", data);
			}
			// 没有设置保存多长时间会不会有问题
		} else {
			return this.apiReturn("-2", "手机号或者密码不正确", data);
		}
	}

	
	// 退出接口
		@RequestMapping(value = "loginout", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
		@ResponseBody
		public String loginout(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
				throws Exception {
			Map data=new HashMap(); 
		  	JSONObject jsonObject =JSONObject.fromObject(json); 
		  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
		  	//获取数据
		  	String tokenString="";
		  	try {
		  		 tokenString=mapget.get("token").toString();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("loginout错误，错误类型"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
				return this.apiReturn("-2", "请求参数异常",data);
			}
		  	System.out.println("退出登录："+"token"+tokenString);
		  	
		  	//token令牌验证
		  	Boolean tokenIsEmpty=true;
		  	try {
				tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("loginout错误，错误类型"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库获取数据异常", e);
				return this.apiReturn("-1", "数据库异常",data);
			}
		  	
		  	if(tokenIsEmpty)
		  	{
		  		return this.apiReturn("-3", "token令牌错误",data);
		  	}
		  	else {
		  		//获取用户数据
		  		try {
		  			sessionSQLServiceApp.delete(tokenString);
		  			//userOpLogService.addOp(Integer.valueOf(idString), "用户登录", request, request.getRequestURL().toString());
					return this.apiReturn("0", "退出登录成功",data);			
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("loginout"+e.toString());
					sysLogService.addUser(request, request.getRequestURL().toString(), "数据库操作异常", e);
					return this.apiReturn("-1", "数据库操作错误",data);
				}
		  		
		  	}  
			
		}
		
		 //接口获取用户权限
		  @RequestMapping(value="getAllPower",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
		  @ResponseBody public String getAllPower(HttpServletRequest request,HttpServletResponse response,@RequestBody String json)throws Exception 
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
				System.out.println("getUserApp"+e.toString());
				return this.apiReturn("-2", "请求参数异常", data);
			}
		  	System.out.println("获取用户权限接口："+"token"+tokenString);
		  	
		  	//token令牌验证
		  	Boolean tokenIsEmpty=true;
		  	try {
				tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("getUserApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
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
		  			System.out.println("String map"+mapString);
			  		Map map=StringToMap.stringToMap(mapString);
			  		String tel=(String)map.get("tel");
			  		User user = userServiceApp.getByTel(tel);//通过电话号码获取用户
			  		System.out.println("user"+user.toString());
			  		String roles=user.getRole_id();
			  		System.out.println("role"+roles);
			  		String[] roleList=roles.split("\\|");
			  		System.out.println("roleList"+roleList[0]+"nihao"+roleList[1]);
			  		List<String> power_1String=new ArrayList<String>();
			  		List<String> power_2String=new ArrayList<String>();
			  		List<String> power_3String=new ArrayList<String>();
			  		//遍历每一个角色的权限
			  		for(int i=0;i<roleList.length;i++)
			  		{
			  			String role_nameString=roleList[i];
			  			Role role=roleService.getByName(role_nameString);
			  			if(role==null)
			  				continue;
			  			power_1String.add(role.getRole_power_1());
			  			power_2String.add(role.getRole_power_2());
			  			power_3String.add(role.getRole_power_3());		
			  		}
			  		System.out.println("power_1String"+power_1String);
			  		Set <String> power_1=new HashSet<String>();
			  		for (int i=0;i<power_1String.size();i++)
			  		{
			  			String a=power_1String.get(i);
			  			String[] alist=a.split("\\|");
			  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
			  			power_1.addAll(set);	
			  		}
			  		System.out.println("power_1"+power_1.toString());
			  		Set <String> power_2=new HashSet<String>();
			  		for (int i=0;i<power_2String.size();i++)
			  		{
			  			String a=power_2String.get(i);
			  			String[] alist=a.split("\\|");
			  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
			  			power_2.addAll(set);	
			  		}
			  		Set <String> power_3=new HashSet<String>();
			  		for (int i=0;i<power_3String.size();i++)
			  		{
			  			String a=power_3String.get(i);
			  			String[] alist=a.split("\\|");
			  			Set<String> set=new HashSet<String>(Arrays.asList(alist));
			  			power_3.addAll(set);	
			  		}
			  		List<String> powerThemes=new ArrayList<String>(power_1);
			  		List<String> powerIndexSpecials=new ArrayList<String>(power_2);
			  		List<String> powerIndexManages=new ArrayList<String>(power_3);
			  		System.out.println("powerThemes"+powerThemes);
			  		data.put("powerThemes", powerThemes);
			  		data.put("powerIndexSpecials", powerIndexSpecials);
			  		data.put("powerIndexManages", powerIndexManages);
					return this.apiReturn("0", "用户权限获取成功", data);			
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("getUserApp"+e.toString());
					sysLogService.addUser(request, request.getRequestURL().toString(), "数据库操作异常", e);
					return this.apiReturn("-1", "数据库操作错误", data);
				}
		  		
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
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-2", "请求参数异常", data);
		}
	  	System.out.println("获取用户个人信息接口："+"token"+tokenString);
	  	
	  	//token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getUserApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
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
				deparmentString=departmentService.getNameList(deparmentString);
				roleNameString=roleService.getNameList(roleNameString);
				
				
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
				userOpLogService.addOp(Integer.valueOf(idString), "用户信息获取", request, request.getRequestURL().toString());
				return this.apiReturn("0", "用户信息获取成功", data);			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("getUserApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库操作异常", e);
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
				System.out.println("editUserApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
				return this.apiReturn("-2", "请求参数错误", data);
			}
			//token令牌验证
		  	Boolean tokenIsEmpty=true;
		  	try {
				tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("editUserApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
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
						userOpLogService.addOp(user.getId(), "用户信息修改", request, request.getRequestURL().toString());
						return this.apiReturn("0", "用户信息修改成功", data);
					} else {
						return this.apiReturn("-1", "用户信息修改失败", data);
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("editUserApp"+e.toString());
					sysLogService.addUser(request, request.getRequestURL().toString(), "数据库操作异常", e);
					return this.apiReturn("-1", "数据库操作异常", data);
				}		
			}	  
	  }

	/**
	 * 更改手机号码
	 * @param request
	 * @param response
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="changeTelApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody public String changeTel(HttpServletRequest request,HttpServletResponse response,@RequestBody String json)throws Exception 
	{
		Map data=new HashMap(); 
		JSONObject jsonObject =JSONObject.fromObject(json); 
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
		//获取参数
		String tokenString="";
		String newTel="";
		//String oldTel="";
		String password="";
		try {
			tokenString=mapget.get("token").toString();
			//oldTel = mapget.get("oldtel").toString();
			newTel=mapget.get("tel").toString();
			//password=mapget.get("password").toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("changeTelApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-2", "请求参数异常", data);
		}
		//token令牌验证
	  	Boolean tokenIsEmpty=true;
	  	try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("changeTelApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
			return this.apiReturn("-1", "数据库异常", data);
		}
		
		if(tokenIsEmpty)
	  	{
			return this.apiReturn("-3", "token令牌错误", data);
	  	}
		else {
			//获取数据
			try {
				String mapString = sessionSQLServiceApp.get(tokenString).getSess_value();
				Map map = StringToMap.stringToMap(mapString);
				int id = Integer.valueOf((String) map.get("userId"));//获得旧手机号
				User user = userServiceApp.get(id);
				//新手机号是否已经被注册
				if(userServiceApp.getByTel(newTel)!=null) {
					//已经注册了
					return this.apiReturn("-2", "该手机号已经被注册", data);
				} else {
					//手机号码未被注册
					user.setTel(newTel);
					userServiceApp.updata(user);
					flashSession(tokenString);
					userOpLogService.addOp(user.getId(), "用户修改手机号", request, request.getRequestURL().toString());
					return this.apiReturn("0", "手机号修改成功", data);
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("changeTelApp"+e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据获取异常", e);
				return this.apiReturn("-1", "数据获取异常", data);
			}		
		}  
	}

	/**
	 * 更改密码
	 * @param request
	 * @param response
	 * @param json
	 * @return errCode
	 * @throws Exception
	 */
	@RequestMapping(value = "changePassword", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody public String changePassword(HttpServletRequest request, HttpServletResponse response, @RequestBody String json) throws Exception {
		Map data = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		//获取参数
		String tokenString="";
		String oldPassword="";
		String newPassword="";
		String telephone="";
		try {
			tokenString=mapget.get("token").toString();
			oldPassword = mapget.get("oldpassword").toString();
			newPassword=mapget.get("newpassword").toString();
			telephone=mapget.get("tel").toString();
			System.out.println(tokenString);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("changePassword" + e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-2", "请求参数异常", data);
		}
		//token令牌验证
		Boolean tokenIsEmpty=true;
		try {
			tokenIsEmpty=(sessionSQLServiceApp.get(tokenString)==null);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("changeTelApp"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "数据库异常", e);
			return this.apiReturn("-1", "数据库异常", data);
		}

		if(tokenIsEmpty) {
			return this.apiReturn("-3", "token令牌错误", data);
		}else{
			//获取数据
			try {
				//手机号是否已经被注册
				if (!userServiceApp.getByTel(telephone).getPassword().equals(oldPassword)) {
					//没有注册

					return this.apiReturn("-4", "旧密码输入错误", data);
				} else {
					//重置密码
					User user = userServiceApp.getByTel(telephone);
					user.setPassword(newPassword);
					userServiceApp.updata(user);
					userOpLogService.addOp(user.getId(), "用户修改密码", request, request.getRequestURL().toString());
					return this.apiReturn("0", "密码修改成功", data);
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("changeTelApp" + e.toString());
				sysLogService.addUser(request, request.getRequestURL().toString(), "数据获取异常", e);
				return this.apiReturn("-1", "数据获取异常", data);
			}
		}

	}

	/**
	 * 新用户注册
	 * @param request
	 * @param response
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="userRegister",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody public String usergister(HttpServletRequest request,HttpServletResponse response,@RequestBody String json)throws Exception
	{
		Map data=new HashMap();
		JSONObject jsonObject =JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		//获取参数
		String telephone="";
		String password="";
		String realName="";
		try {
			telephone=mapget.get("tel").toString();
			password=mapget.get("password").toString();
			realName=mapget.get("name").toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("userRegister"+e.toString());
			sysLogService.addUser(request, request.getRequestURL().toString(), "请求参数异常", e);
			return this.apiReturn("-2", "请求参数异常", data);
		}
		//新手机号是否已经被注册
		if(userServiceApp.getByTel(telephone)!=null) {
			//已经注册了
			return this.apiReturn("-2", "该手机号已经被注册", data);
		} else {
			User newUser = new User();
			newUser.setTel(telephone);
			newUser.setPassword(password);
			newUser.setReal_name(realName);
			userServiceApp.add(newUser);
			return this.apiReturn("0", "用户注册成功", data);
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
			System.out.println(JSON.toJSONString(responseMap));
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
