package com.wuhan_data.app.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.wuhan_data.app.service.FeedbackServiceApp;
import com.wuhan_data.app.service.SessionSQLServiceApp;
import com.wuhan_data.app.service.UserServiceApp;
import com.wuhan_data.pojo.Feedback;
import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.ImageUtils;
import com.wuhan_data.tools.SessionApp;
import com.wuhan_data.tools.StringToMap;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("")
public class UpImagesControllerApp {
	@Autowired
	UserServiceApp userServiceApp;
	@Autowired
	FeedbackServiceApp feedbackServiceApp;
	@Autowired
	SessionSQLServiceApp sessionSQLServiceApp;
	/**
	 * 添加用户信息
	 * @param user，封装表单中除图片地址以外的其他数据（要求<input>中的name跟实体类中的属性一致）
	 * @param request，用来获取文件的存储位置等
	 * @param pictureFile，封装上传图片的信息如大小、文件名、扩展名等,（要求<input>中的name跟次命名一致）。
	 * @return
	 * 注意：图片提交input输入框的name属性值要与Controller中MultipartFile
	 * 接口所声明的形参名一致，不然需要用@RequestParam注解绑定
	 */
	
	@RequestMapping(value="uploadHead",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String uploadHead( HttpServletRequest request,  @RequestParam("file")MultipartFile file) {
		
		Map mapReturn=new HashMap();
//		JSONObject jsonObject =JSONObject.fromObject(json); 
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
//		String tokenString=mapget.get("token").toString();
	    String tokenString =request.getParameter("token");
	    System.out.println("图片上传接口："+"token"+tokenString);
	  	if(sessionSQLServiceApp.get(tokenString)==null)
	  	{
			mapReturn.put("errCode", "-3");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
	  	String param=JSON.toJSONString(mapReturn);
		return param;
	  	
	}
	
	
	
	//只允许传输单个图片
	//接口 头像上传
	@RequestMapping(value="setHeadApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String setHead( HttpServletRequest request, @RequestParam("file")MultipartFile [] files) {
		// 得到上传图片的地址
		Map mapReturn=new HashMap();
//		JSONObject jsonObject =JSONObject.fromObject(json); 
//		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
//		String tokenString=mapget.get("token").toString();
	    String tokenString =request.getParameter("token");
	    System.out.println("图片上传接口："+"token"+tokenString);
	  	if(sessionSQLServiceApp.get(tokenString)==null)
	  	{
			mapReturn.put("errCode", "-3");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
	  	else {
	  		 if (files.length!=1)
	 	    {
	 	    	System.out.print(files.length);
	 	    	mapReturn.put("errCode", "-2");
	 	    	mapReturn.put("errMsg","传输图片的数量错误");
	 	    	mapReturn.put("head", null);
	 	    }
	 	    else 
	 	    {
	 	    	String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
	 	    	Map map2=StringToMap.stringToMap(mapString);
		  		int id=Integer.valueOf((String)map2.get("userId"));
	 			//获取旧的头像的地址
	 	    	String oldHead=userServiceApp.get(id).getHead();
	 	    	System.out.println("oldHead"+oldHead);
	 	    	//上传新的图片的地址
	 	    	try {
	 	    		String imgPath = ImageUtils.uploadHead(request, files[0]);
	 	    		if(imgPath==null ||imgPath.equals(""))
	 	    		{
	 	    			mapReturn.put("errCode", "-1");
	 	    	    	mapReturn.put("errMsg","头像上传失败");
	 	    		}
	 	    		else {
	 					//上传成功，删除原来的图片
	 	    			if(oldHead!=null||oldHead!="")
	 	    			{
	 	    				String url = request.getSession().getServletContext().getRealPath("");
	 		    			String deletehead=oldHead;
	 	    				File oldFile = new File(deletehead); 
	 		    			if(oldFile.exists())
	 						{
	 							System.out.println("删除是否成功："+oldFile.delete()+"路径:"+deletehead);//直接删除
	 						}
	 	    			}
	 	    			Map map=new HashMap();
	 	    			map.put("id", id);
	 	    			map.put("head", imgPath);
	 	    			userServiceApp.setHeadById(map);
	 	    			//返回数据
	 	    			mapReturn.put("errCode", "0");
	 	    	    	mapReturn.put("errMsg","头像上传成功");
	 	    	    	List list = new ArrayList();
	 	    	    	Map map1 = new HashMap();
	 	    	    	map1.put("head", imgPath);
	 	    	    	list.add(map1);
	 	    	    	mapReturn.put("data", list);
	 	    	    	//刷新session中的内容
	 	    	    	flashSession(tokenString);
	 	    	    	
	 				}
	 			} catch (Exception e) {
	 				// TODO: handle exception
	 			}
	 		}
			
		}
		//int id=22;
	   
		String param=JSON.toJSONString(mapReturn);
		return param;
 
	}
	// 接口 问题反馈 多个图片
	//多个图片的上传
	// 如下代码只保留了主逻辑
	//处理提交的时候有多个图片
	@RequestMapping(value="uploadFeedback",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFeedback(HttpServletRequest request) throws IOException{
		
		Map mapReturn=new HashMap();
	    String tokenString =request.getParameter("token");
		//int uid=Integer.valueOf(request.getParameter("id"));
		String text=request.getParameter("text");
		String contact=request.getParameter("contact");
		
		System.out.println("反馈接口1"+"token"+tokenString+"text"+text+"contact"+contact);
		if(sessionSQLServiceApp.get(tokenString)==null)
	  	{
			mapReturn.put("errCode", "-3");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
		else {
			String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
		    Map map2=StringToMap.stringToMap(mapString);
	  		int uid=Integer.valueOf((String)map2.get("userId"));
		
		    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		    commonsMultipartResolver.setDefaultEncoding("utf-8");
		 
		    if (commonsMultipartResolver.isMultipart(request))
		    {
		        MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
		        Map<String, MultipartFile> map = mulReq.getFileMap();
		        String imgs="";
		        // key为前端的name属性，value为上传的对象（MultipartFile）
		        for (Map.Entry<String, MultipartFile> entry : map.entrySet()) 
		        {
		            // 自己的保存文件逻辑
		            String imgpath=ImageUtils.uploadFeedback(request, entry.getValue());
		            if(imgs.equals(""))
		            {
		            	imgs=imgs+imgpath;
		            }
		            else {
						imgs=imgs+";"+imgpath;
					}
		        }
		        if(imgs!=null || imgs!="")
		        {
		        	Feedback feedback=new Feedback();
		        	feedback.setUid(uid);
		        	feedback.setText(text);
		        	feedback.setImg(imgs);
		        	feedback.setContact(contact);
		        	Date date=new Date();
		        	feedback.setCreate_time(date);
		        	System.out.println("feedback"+feedback.toString());
		        	if(feedbackServiceApp.add(feedback)!=0)
		        	{
		        		mapReturn.put("code", "0");
						mapReturn.put("msg","上传反馈成功");
		        	}
		        	else {
		        		mapReturn.put("code", "-1");
						mapReturn.put("msg","上传反馈失败");
					}
		        }
		        else {
		        	mapReturn.put("code", "-1");
					mapReturn.put("msg","上传图片失败");
				} 
		    }
		    else 
		    {
				mapReturn.put("code", "-2");
				mapReturn.put("msg","未获取到图片数据");
			}
		}
	    String param=JSON.toJSONString(mapReturn);
		return param;
	}
	@RequestMapping(value="uploadFeedback1",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFeedback1(HttpServletRequest request,@RequestBody String json) throws IOException{
		Map mapReturn=new HashMap(); 
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
		String tokenString = mapget.get("token").toString();
		String textString =mapget.get("text").toString();
		String contactString=mapget.get("contact").toString();
		System.out.println("反馈接口1"+"token"+tokenString+"text"+textString+"contact"+contactString);
		if(sessionSQLServiceApp.get(tokenString)==null)
	  	{
			mapReturn.put("errCode", "-3");
			mapReturn.put("errMsg", "token令牌错误");
	  	}
		else {
			String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
		    Map map2=StringToMap.stringToMap(mapString);
	  		int uid=Integer.valueOf((String)map2.get("userId"));
			Feedback feedback=new Feedback();
        	feedback.setUid(uid);
        	feedback.setText(textString);
        	feedback.setImg("");
        	feedback.setContact(contactString);
        	Date date=new Date();
        	feedback.setCreate_time(date);
        	System.out.println("feedback"+feedback.toString());
        	if(feedbackServiceApp.add(feedback)!=0)
        	{
        		mapReturn.put("code", "0");
				mapReturn.put("msg","上传反馈成功");
        	}
        	else {
        		mapReturn.put("code", "-1");
				mapReturn.put("msg","上传反馈失败");
			}
		}
		 String param=JSON.toJSONString(mapReturn);
		 return param;
	}
	
//	@RequestMapping(value="uploadFeedback3",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
//	@ResponseBody
//	public String uploadFeedback3(HttpServletRequest request,@RequestBody String json) throws IOException{
//		Map mapReturn=new HashMap(); 
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		 commonsMultipartResolver.setDefaultEncoding("utf-8");
//		 if (commonsMultipartResolver.isMultipart(request))
//		 {
//			String tokenString =request.getParameter("token");
//			String text=request.getParameter("text");
//			String contact=request.getParameter("contact");
//			System.out.println("反馈接口3.2"+"token"+tokenString+"text"+text+"contact"+contact);
//			if(sessionSQLServiceApp.get(tokenString)==null)
//		  	{
//				mapReturn.put("errCode", "2");
//				mapReturn.put("errMsg", "token令牌错误");
//		  	}
//			else {
//				String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
//			    Map map2=StringToMap.stringToMap(mapString);
//		  		int uid=Integer.valueOf((String)map2.get("userId"));
//				MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
//		        Map<String, MultipartFile> map = mulReq.getFileMap();
//		        String imgs="";
//		        // key为前端的name属性，value为上传的对象（MultipartFile）
//		        for (Map.Entry<String, MultipartFile> entry : map.entrySet()) 
//		        {
//		            // 自己的保存文件逻辑
//		            String imgpath=ImageUtils.uploadFeedback(request, entry.getValue());
//		            if(imgs.equals(""))
//		            {
//		            	imgs=imgs+imgpath;
//		            }
//		            else {
//						imgs=imgs+";"+imgpath;
//					}
//		        }
//		        if(imgs!=null || imgs!="")
//		        {
//		        	Feedback feedback=new Feedback();
//		        	feedback.setUid(uid);
//		        	feedback.setText(text);
//		        	feedback.setImg(imgs);
//		        	feedback.setContact(contact);
//		        	Date date=new Date();
//		        	feedback.setCreate_time(date);
//		        	System.out.println("feedback"+feedback.toString());
//		        	if(feedbackServiceApp.add(feedback)!=0)
//		        	{
//		        		mapReturn.put("code", 0);
//						mapReturn.put("msg","上传反馈成功");
//		        	}
//		        	else {
//		        		mapReturn.put("code", 1);
//						mapReturn.put("msg","上传反馈失败");
//					}
//		        }
//		        else {
//		        	mapReturn.put("code", 3);
//					mapReturn.put("msg","上传图片失败");
//				} 
//				
//			}
//			 
//		 }
//		 //处理没有图片的情况
//		 else {
//			JSONObject jsonObject = JSONObject.fromObject(json);
//			Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
//			String tokenString = mapget.get("token").toString();
//			String textString =mapget.get("text").toString();
//			String contactString=mapget.get("contact").toString();
//			System.out.println("反馈接口3.1"+"token"+tokenString+"text"+textString+"contact"+contactString);
//			if(sessionSQLServiceApp.get(tokenString)==null)
//		  	{
//				mapReturn.put("errCode", "2");
//				mapReturn.put("errMsg", "token令牌错误");
//		  	}
//			else {
//				String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
//			    Map map2=StringToMap.stringToMap(mapString);
//		  		int uid=Integer.valueOf((String)map2.get("userId"));
//				Feedback feedback=new Feedback();
//	        	feedback.setUid(uid);
//	        	feedback.setText(textString);
//	        	feedback.setImg("");
//	        	feedback.setContact(contactString);
//	        	Date date=new Date();
//	        	feedback.setCreate_time(date);
//	        	System.out.println("feedback"+feedback.toString());
//	        	if(feedbackServiceApp.add(feedback)!=0)
//	        	{
//	        		mapReturn.put("code", 0);
//					mapReturn.put("msg","上传反馈成功");
//	        	}
//	        	else {
//	        		mapReturn.put("code", 1);
//					mapReturn.put("msg","上传反馈失败");
//				}
//				
//			}
//			
//		 }
//		 String param=JSON.toJSONString(mapReturn);
//		 return param;
//	}
//	
//	
	
	
	
//	@RequestMapping(value="uploadFeedback2",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
//	@ResponseBody
//	public String uploadFeedback2(HttpServletRequest request, @RequestParam("file")MultipartFile [] files) throws IOException{
//		
//		Map mapReturn=new HashMap();
//	    String tokenString =request.getParameter("token");
//	    // int uid=Integer.valueOf(request.getParameter("id"));
//	    //id通过token来获取
//		String textString=request.getParameter("text");
//		String contactString=request.getParameter("contact");
//	    System.out.println("图片上传接口："+"token"+tokenString+"text"+textString+"contact"+contactString);
//	  	if(sessionSQLServiceApp.get(tokenString)==null)
//	  	{
//			mapReturn.put("errCode", "2");
//			mapReturn.put("errMsg", "token令牌错误");
//	  	}
//	  	else {
//	  		String mapString=sessionSQLServiceApp.get(tokenString).getSess_value();
// 	    	Map map2=StringToMap.stringToMap(mapString);
//	  		int uid=Integer.valueOf((String)map2.get("userId"));
//	  		String feedImgString="";
//	  		System.out.println("几张图"+files.length);
//	  		for(int i=0;i<files.length;i++)
//	  		{
//	  			try {
//	 	    		String imgPath = ImageUtils.uploadFeedback(request, files[i]);
//	 	    		if(imgPath==null ||imgPath.equals(""))
//	 	    		{
//	 	    			mapReturn.put("errCode", "2");
//	 	    	    	mapReturn.put("errMsg","图片上传失败");
//	 	    		}
//	 	    		feedImgString=feedImgString+";"+imgPath;
//	 	    		System.out.println("feedImgString"+feedImgString);
//	  			}
//	  			catch (Exception e) {
//	 				// TODO: handle exception
//	 			}
//	  		}
//	  		Feedback feedback=new Feedback();
//        	feedback.setUid(uid);
//        	feedback.setText(textString);
//        	feedback.setImg(feedImgString);
//        	feedback.setContact(contactString);
//        	Date date=new Date();
//        	feedback.setCreate_time(date);
//        	System.out.println(feedback.toString());
//        	if(feedbackServiceApp.add(feedback)!=0)
//        	{
//        		mapReturn.put("code", 0);
//				mapReturn.put("msg","上传反馈成功");
//        	}
//        	else {
//        		mapReturn.put("code", 1);
//				mapReturn.put("msg","上传反馈失败");
//			}
//	  	}
//	    String param=JSON.toJSONString(mapReturn);
//	    System.out.println("反馈"+param);
//	 	return param;
//		
//	}
	
	
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
	
	
	
	
}
