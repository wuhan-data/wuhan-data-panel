package com.wuhan_data.app.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.wuhan_data.app.service.UserServiceApp;
import com.wuhan_data.pojo.Feedback;
import com.wuhan_data.pojo.User;
import com.wuhan_data.tools.ImageUtils;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("")
public class UpImagesControllerApp {
	@Autowired
	UserServiceApp userServiceApp;
	@Autowired
	FeedbackServiceApp feedbackServiceApp;
	/**
	 * 添加用户信息
	 * @param user，封装表单中除图片地址以外的其他数据（要求<input>中的name跟实体类中的属性一致）
	 * @param request，用来获取文件的存储位置等
	 * @param pictureFile，封装上传图片的信息如大小、文件名、扩展名等,（要求<input>中的name跟次命名一致）。
	 * @return
	 * 注意：图片提交input输入框的name属性值要与Controller中MultipartFile
	 * 接口所声明的形参名一致，不然需要用@RequestParam注解绑定
	 */
	
	//只允许传输单个图片
	
	
	//接口 头像上传
	@RequestMapping(value="UpImagesHead",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String UpImagesHead( HttpServletRequest request, @RequestParam("file")MultipartFile [] files) {
		// 得到上传图片的地址
		Map mapReturn=new HashMap();
	    int id =Integer.valueOf(request.getParameter("id"));
		//int id=22;
	    if (files.length!=1)
	    {
	    	System.out.print(files.length);
	    	mapReturn.put("code", 2);
	    	mapReturn.put("msg","传输图片的数量错误");
	    	mapReturn.put("head", null);
	    }
	    else 
	    {
			//获取旧的头像的地址
	    	String oldHead=userServiceApp.get(id).getHead();
	    	//上传新的图片的地址
	    	try {
	    		String imgPath = ImageUtils.uploadHead(request, files[0]);
	    		if(imgPath==null ||imgPath.equals(""))
	    		{
	    			mapReturn.put("code", 0);
	    	    	mapReturn.put("msg","头像上传失败");
	    	    	mapReturn.put("head", null);
	    		}
	    		else {
					//上传成功，删除原来的图片
	    			if(oldHead!=null||oldHead!="")
	    			{
	    				String url = request.getSession().getServletContext().getRealPath("");
		    			String deletehead=url+"/"+oldHead;
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
	    			mapReturn.put("code", 1);
	    	    	mapReturn.put("msg","头像上传成功");
	    	    	mapReturn.put("head", imgPath);	
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		String param=JSON.toJSONString(mapReturn);
		return param;
 
	}
	
	// 接口 问题反馈 多个图片
	//多个图片的上传
	// 如下代码只保留了主逻辑
	@RequestMapping(value="uploadFeedback",produces="text/plain;charset=utf-8",method=RequestMethod.GET)
	@ResponseBody
	public String uploadFile(HttpServletRequest request) throws IOException{
		
		
		int uid=Integer.valueOf(request.getParameter("id"));
		String text=request.getParameter("text");
		String contact=request.getParameter("contact");
		Map mapReturn=new HashMap();
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
	        	if(feedbackServiceApp.add(feedback)!=0)
	        	{
	        		mapReturn.put("code", 1);
					mapReturn.put("msg","上传反馈成功");
	        	}
	        	else {
	        		mapReturn.put("code", 0);
					mapReturn.put("msg","上传反馈失败");
				}
	        }
	        else {
	        	mapReturn.put("code", 3);
				mapReturn.put("msg","上传图片失败");
			}
	        
	    }
	    else 
	    {
			mapReturn.put("code", 2);
			mapReturn.put("msg","未获取到图片数据");
		}
	    String param=JSON.toJSONString(mapReturn);
		return param;
	}
	
	
	@RequestMapping(value="ppp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public void uploadFile(HttpServletRequest request, @RequestParam("file")MultipartFile [] files){
	    int id=Integer.valueOf(request.getParameter("id"));
	    System.out.print(id);
	    // 这样就可以收到文件了，files.length == 1.
	    System.out.println(files.length);
	    MultipartFile file=files[0];
	    try {
			ImageUtils.uploadHead(request, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	    // 后续操作省略
	}
	
	
	
	
}
