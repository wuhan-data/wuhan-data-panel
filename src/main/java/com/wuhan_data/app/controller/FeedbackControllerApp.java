//package com.wuhan_data.app.controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.alibaba.fastjson.JSON;
//import com.wuhan_data.app.service.FeedbackServiceApp;
//import com.wuhan_data.pojo.Collect;
//import com.wuhan_data.pojo.Feedback;
//
//import net.sf.json.JSONObject;
//
//@Controller
//@RequestMapping("")
//public class FeedbackControllerApp {
//	@Autowired
//	FeedbackServiceApp feedbackServiceApp;
//	//问题反馈接口
//	@RequestMapping(value="setFeedbackApp",produces="text/plain;charset=utf-8",method=RequestMethod.POST)
//	@ResponseBody
//	public String setFeedback(HttpServletRequest request, 
//			@RequestBody String json,@RequestParam("img")MultipartFile multipartFile) throws Exception
//	{
//		Map mapReturn=new HashMap();
//		
//		
//		JSONObject jsonObject =JSONObject.fromObject(json); 
//	  	Map<String, Object> mapget = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class); 
//	  	String tokenString=mapget.get("token").toString();
//		String textString=mapget.get("text").toString();
//		
//	  	HttpSession session = request.getSession();
//	  	if(session.getAttribute(tokenString)==null)
//	  	{
//			mapReturn.put("errCode", "2");
//			mapReturn.put("errMsg", "token令牌错误");
//	  	}
//	  	else {
//	  	//先上传图片
//			String realpath="";
//			//获取文件名
//			String name="";
//			if(multipartFile!=null)
//			{
//				long size= multipartFile.getSize();
//				name=multipartFile.getOriginalFilename();//直接返回文件的名字
//				String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
//				String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//文件保存进来，我给他重新命名，数据库保存有原本的名字，所以输出的时候再把他附上原本的名字就行了。
//				String filepath=request.getServletContext().getRealPath("/")+"feedbacks\\";//获取项目路径到webapp
//				File file=new File(filepath);
//				if(!file.exists())
//				{//目录不存在就创建
//					file.mkdirs();
//				}
//				try {
//					multipartFile.transferTo(new File(file+"\\"+fileName+"."+subffix));
//				} catch (IllegalStateException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}//保存文件
//				realpath=file+"\\"+fileName+"."+subffix;
//		
//		//		int uid=Integer.valueOf(request.getParameter("uid"));		
//		//		String type=request.getParameter("type");
//		//		int index_id=Integer.valueOf(request.getParameter("index_id"));
//				int uid=0;
//				String title ="title";
//				//String text="text";
//				String img=realpath;
//				String contact="contact";
//				String state="state";
//				Date create_time=new Date();
//				Feedback feedback=new Feedback();
//				feedback.setUid(uid);
//				feedback.setTitle(title);
//				feedback.setText(textString);
//				feedback.setImg(img);
//				feedback.setContact(contact);
//				feedback.setCreate_time(create_time);
//				feedback.setState(state);
//				int code=0;
//				if (feedbackServiceApp.add(feedback)!=0)
//				{
//					mapReturn.put("errCode","0");
//					mapReturn.put("errMsg","反馈成功");
//				}
//				else {
//					mapReturn.put("errCode","1");
//					mapReturn.put("errMsg","反馈失败");
//					
//				}
//			}
//			else {
//				mapReturn.put("code","3");
//				mapReturn.put("msg","未接受到图片数据");
//			}
//			
//		}
//
//		String param=JSON.toJSONString(mapReturn);
//		System.out.println("问题反馈接口："+param);
//		return param;
//	}
//	@RequestMapping(value="getFeedback",produces="text/plain;charset=utf-8")
//	@ResponseBody
//	public String getFeedback(HttpServletRequest request, 
//            HttpServletResponse response) throws Exception
//	{
//		Map mapReturn=new HashMap();
//		//int uid=Integer.valueOf(request.getParameter("uid"));
//		int uid=0;
//		List<Feedback> feedbacks=feedbackServiceApp.getByUid(uid);
//	//	List<Collect> collects=collectServiceApp.getByUid(uid);
//		
//		mapReturn.put("code","0");
//		mapReturn.put("msg","收藏成功");
//		mapReturn.put("data",feedbacks);
//		
//		String param=JSON.toJSONString(mapReturn);
//		return param;
//	}
//
//	
//
//}
