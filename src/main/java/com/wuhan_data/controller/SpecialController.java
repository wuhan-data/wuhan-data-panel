package com.wuhan_data.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wuhan_data.pojo.AnalysisManage;
import com.wuhan_data.pojo.IndexPic;
import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.IndiCorrelative;
import com.wuhan_data.pojo.SpecialDetail;
import com.wuhan_data.service.IndiColumnService;
import com.wuhan_data.service.SpecialService;
import com.wuhan_data.tools.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class SpecialController {
	
	@Autowired
	SpecialService specialService;
	
	@RequestMapping("specialInit")
    public String specialInit(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession(true);
    	request.setCharacterEncoding("UTF-8");  	
        response.setCharacterEncoding("UTF-8");   
        Page page=new Page(); //分页类
        int count = specialService.total();//每一个一级栏目下面二极栏目的数量
        System.out.print("每一个一级栏目下面二极栏目的数量"+count);
        Map<String,Object> map = new HashMap<String, Object>(); //分页查询参数      
        String currentPage=request.getParameter("currentPage");
        System.out.println("currentPage:"+currentPage);
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
        List<IndexSpecial> specialByPage=specialService.getlist(map);

        JSONArray json = new JSONArray();
        for(IndexSpecial a : specialByPage){
            JSONObject jo = new JSONObject();
            jo.put("id", a.getTopic_weight());
            jo.put("name", a.getTitle());
            jo.put("num", a.getTopic_weight());
            json.add(jo);
        }        
        String jsondata = request.getParameter("jsondata");
        if(jsondata!=null) {
        	System.out.println("************************************");
        	System.out.println(jsondata);
        	System.out.println("************************************");
        	 JSONArray jsonArray = JSONArray.fromObject(jsondata);
             Object[] os = jsonArray .toArray();
             for(int i=0; i<os.length; i++) {
            	 IndexSpecial o = new IndexSpecial();
                 JSONObject jsonObj = JSONObject.fromObject(os[i]);
                 System.out.println("解析后："+jsonObj.get("name").toString()+":"+(jsonObj.getInt("num")));
                 o.setTitle(jsonObj.get("name").toString());
                 o.setTopic_weight(jsonObj.get("num").toString());
                 specialService.reOrderByTitle(o);             
             } 
             specialByPage=specialService.getlist(map);
        }else {
        	System.out.print("无法获取jsondata");
        }
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^:"+specialByPage.size());
        
        session.setAttribute("json", json);
        session.setAttribute("specialByPage", specialByPage);
        session.setAttribute("page", page);
//        mav.setViewName("specialManage");
      return "specialFrame";
//        return mav;
    }
	
	

	@RequestMapping(value="specialAdd",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
	 public String specialAdd(IndexSpecial indexSpecial,MultipartFile pic1,HttpServletRequest request, HttpServletResponse response) throws IOException {
System.out.println("zhaodaole");		
//       存到数据库的路径
       String sqlPath=null;
       
       String contextPath = request.getContextPath();
       String basePath = request.getScheme()+"://"+InetAddress.getLocalHost().getHostAddress()+":"+  
                     request.getServerPort()+contextPath+"/"; 
       
//       存到本地的路径
//       String localPath="/Users/in/uploads/";
       String localPath="C:\\wuhan_data_file\\head";     
//       存到本地的路径
//       String localPath="/Users/in/uploads/";
//       文件名
       String filename=null;
       System.out.println("pic1:"+pic1);
       if(!pic1.isEmpty()){  //判断获取到的图片是否为空 不为空 进入到if
           //生成uuid作为文件名的一部分
           String uuid = UUID.randomUUID().toString().replaceAll("-","");
           //获得文件类型
           String contentType=pic1.getContentType();
           //获得文件后缀
           String suffixName=contentType.substring(contentType.indexOf("/")+1);
           //拼接文件名
           filename=uuid+"."+suffixName;
           System.out.println(filename);
           //文件保存路径
           pic1.transferTo(new File(localPath+filename));
       }
       //数据库中保存的是图片的相对路径
       sqlPath = basePath+"uploads/"+filename;
       System.out.println(sqlPath);
       indexSpecial.setImage(sqlPath);
       specialService.add(indexSpecial); 
       return "redirect:specialInit";
   }
	
	


	@RequestMapping("specialDel")
    public String specialDel(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        System.out.print("进入del");
        int special_id=Integer.parseInt(request.getParameter("special_id"));
        specialService.delete(special_id);
        return "redirect:specialInit";
    }

	@RequestMapping("specialUpdateShow")
    public String specialUpdateShow(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
    	request.setCharacterEncoding("UTF-8");    	
        response.setCharacterEncoding("UTF-8");
        int special_id=Integer.parseInt(request.getParameter("special_id"));
        int is_show=Integer.parseInt(request.getParameter("is_show"));        
        IndexSpecial sd = new IndexSpecial();
        sd.setId(special_id);
        sd.setIs_show(is_show);
        specialService.updateShow(sd);   
        return "redirect:specialInit";
    }
	
	
	@RequestMapping(value="specialUpdate",produces = "text/plain;charset=utf-8",method = RequestMethod.POST)
	 public String specialUpdate(IndexSpecial indexSpecial,MultipartFile pic1,HttpServletRequest request, HttpServletResponse response) throws IOException {

	request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
		//      存到数据库的路径
     String sqlPath=null;
     String contextPath = request.getContextPath();
     String basePath = request.getScheme()+"://"+InetAddress.getLocalHost().getHostAddress()+":"+  
                   request.getServerPort()+contextPath+"/"; 
     
//     存到本地的路径
//     String localPath="/Users/in/uploads/";
     String localPath="C:\\wuhan_data_file\\head";    
//     存到本地的路径
//     String localPath="/Users/in/uploads/";
//     文件名
     String filename=null;
     if(!pic1.isEmpty()){  //判断获取到的图片是否为空 不为空 进入到if
         //生成uuid作为文件名的一部分
         String uuid = UUID.randomUUID().toString().replaceAll("-","");
         //获得文件类型
         String contentType=pic1.getContentType();
         //获得文件后缀
         String suffixName=contentType.substring(contentType.indexOf("/")+1);
         //拼接文件名
         filename=uuid+"."+suffixName;
         System.out.println(filename);
         //文件保存路径
         pic1.transferTo(new File(localPath+filename));
         sqlPath = basePath+"uploads/"+filename;
         
         System.out.println(sqlPath);
         int id=Integer.parseInt(request.getParameter("id"));
         indexSpecial.setId(id);
         indexSpecial.setImage(sqlPath);
         specialService.update(indexSpecial);

     }else {
   	  int id=Integer.parseInt(request.getParameter("id"));
   	 indexSpecial.setId(id);
   	 specialService.updateTitle(indexSpecial);  
 	  
     }
     //数据库中保存的是图片的相对路径
     System.out.print("到这里了");      
     return "redirect:specialInit";
 }

	
}
